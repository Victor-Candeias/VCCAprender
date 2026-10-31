package com.vcc.aprender.ui.alphabet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vcc.aprender.data.progress.ProgressRepository
import com.vcc.aprender.domain.alphabet.AlphabetContent
import com.vcc.aprender.domain.alphabet.LetterCard
import com.vcc.aprender.domain.model.LearningTopic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AlphabetUiState(
    val index: Int = 0,
    val selected: Char? = null,
    val correct: Boolean? = null,
    val error: String? = null
) {
    val card: LetterCard get() = AlphabetContent.cards[index]
    val options: List<Char> get() = AlphabetContent.optionsFor(index)
}

class AlphabetViewModel(
    private val progress: ProgressRepository,
    private val profileId: Long
) : ViewModel() {
    private val _state = MutableStateFlow(AlphabetUiState())
    val state = _state.asStateFlow()

    fun next() {
        _state.value = AlphabetUiState(index = (_state.value.index + 1) % AlphabetContent.cards.size)
    }

    fun previous() {
        _state.value = AlphabetUiState(index = (_state.value.index + AlphabetContent.cards.size - 1) % AlphabetContent.cards.size)
    }

    fun answer(letter: Char) {
        val current = _state.value
        if (current.selected != null) return
        val correct = letter == current.card.letter
        _state.value = current.copy(selected = letter, correct = correct)
        viewModelScope.launch {
            runCatching {
                progress.recordAnswer(profileId, LearningTopic.LETTERS, correct)
                if (correct) progress.completeActivity(profileId, LearningTopic.LETTERS)
            }.onFailure { _state.value = _state.value.copy(error = "Não foi possível guardar o progresso.") }
        }
    }
}

class AlphabetViewModelFactory(
    private val progress: ProgressRepository,
    private val profileId: Long
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(AlphabetViewModel::class.java))
        return AlphabetViewModel(progress, profileId) as T
    }
}
