package com.vcc.aprender.ui.magic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vcc.aprender.data.progress.ProgressRepository
import com.vcc.aprender.domain.magic.FingerMagicContent
import com.vcc.aprender.domain.magic.FingerMagicDeck
import com.vcc.aprender.domain.magic.FingerMagicSession
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class FingerMagicUiState(
    val session: FingerMagicSession = FingerMagicSession(),
    val heldOption: Int? = null,
    val revealProgress: Float = 0f,
    val error: String? = null
)

class FingerMagicViewModel(
    private val profileId: Long,
    private val progressRepository: ProgressRepository,
    private val nanoTime: () -> Long = System::nanoTime,
    private val random: Random = Random.Default
) : ViewModel() {
    private val mutableState = MutableStateFlow(
        FingerMagicUiState(session = FingerMagicDeck.newSession(random))
    )
    val state = mutableState.asStateFlow()
    private var holdJob: Job? = null
    private var holdStartedNanos: Long? = null
    private var correctRevealJob: Job? = null
    private var accessibleHold = false

    fun press(option: Int, accessible: Boolean = false) {
        val current = mutableState.value
        if (current.heldOption != null || !current.session.canReveal(option)) return
        accessibleHold = accessible
        val start = nanoTime()
        holdStartedNanos = start
        mutableState.value = current.copy(heldOption = option, revealProgress = 0f)
        holdJob = viewModelScope.launch {
            // Use elapsed time, independent of the system's animation duration scale.
            while (true) {
                val elapsed = (nanoTime() - start) / 1_000_000L
                val progress = (elapsed.toFloat() / FingerMagicContent.REVEAL_MILLIS).coerceIn(0f, 1f)
                mutableState.value = mutableState.value.copy(revealProgress = progress)
                if (progress >= 1f) break
                delay(16)
            }
            finishReveal(option)
            if (accessible) release(option)
        }
    }

    private fun finishReveal(option: Int) {
        val current = mutableState.value
        if (current.heldOption != option) return
        val before = current.session
        val after = before.reveal(option)
        if (after == before) return
        mutableState.value = current.copy(session = after, revealProgress = 1f)
        if (!after.solved) {
            correctRevealJob?.cancel()
            val questionId = after.question.id
            correctRevealJob = viewModelScope.launch {
                delay(FingerMagicContent.WRONG_VISIBLE_MILLIS)
                val latest = mutableState.value
                if (latest.session.question.id == questionId && latest.session.answered) {
                    mutableState.value = latest.copy(session = latest.session.revealCorrectAnswer())
                }
            }
        }
        if (option !in before.attempted) {
            viewModelScope.launch {
                runCatching {
                    progressRepository.recordAnswer(profileId, before.question.topic, after.solved)
                    if (after.solved) progressRepository.completeActivity(profileId, before.question.topic)
                }.onFailure {
                    mutableState.value = mutableState.value.copy(error = "Não foi possível guardar o progresso.")
                }
            }
        }
    }

    fun release(option: Int) {
        if (mutableState.value.heldOption != option) return
        // Releasing may be delivered before the next 16 ms timer tick. Commit a
        // completed hold from elapsed time instead of losing its revealed result.
        val elapsedMillis = holdStartedNanos?.let { (nanoTime() - it) / 1_000_000L } ?: 0L
        if (elapsedMillis >= FingerMagicContent.REVEAL_MILLIS) finishReveal(option)
        holdJob?.cancel()
        holdJob = null
        holdStartedNanos = null
        accessibleHold = false
        val current = mutableState.value
        mutableState.value = current.copy(heldOption = null, revealProgress = 0f)

    }

    /** Touch cancellation, backgrounding and disposal must not reveal an unattended image. */
    fun cancelTouch() {
        mutableState.value.heldOption?.let(::release)
    }

    fun releaseTouch(option: Int) {
        if (!accessibleHold) release(option)
    }

    fun next() {
        val current = mutableState.value
        val next = current.session.next()
        if (next == current.session) return
        clearTimers()
        mutableState.value = FingerMagicUiState(session = next, error = current.error)
    }

    fun restart() {
        clearTimers()
        mutableState.value = FingerMagicUiState(session = FingerMagicDeck.newSession(random))
    }

    private fun clearTimers() {
        holdJob?.cancel()
        holdJob = null
        holdStartedNanos = null
        correctRevealJob?.cancel()
        correctRevealJob = null
        accessibleHold = false
    }
}

class FingerMagicViewModelFactory(
    private val profileId: Long,
    private val progressRepository: ProgressRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(FingerMagicViewModel::class.java))
        return FingerMagicViewModel(profileId, progressRepository) as T
    }
}
