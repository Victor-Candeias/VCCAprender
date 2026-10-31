package com.vcc.aprender.ui.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vcc.aprender.data.progress.ProgressRepository
import com.vcc.aprender.domain.activities.ActivityContent
import com.vcc.aprender.domain.activities.LearningQuestion
import com.vcc.aprender.domain.model.LearningTopic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ActivityUiState(
    val topic: LearningTopic,
    val index: Int = 0,
    val answer: String? = null,
    val correct: Boolean? = null,
    val error: String? = null
) {
    val questions: List<LearningQuestion> get() = ActivityContent.questions(topic)
    val question: LearningQuestion get() = questions[index]
}

class ActivityViewModel(
    private val topic: LearningTopic,
    private val profileId: Long,
    private val progressRepository: ProgressRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ActivityUiState(topic))
    val state = _state.asStateFlow()

    fun answer(value: String) {
        val current = _state.value
        if (current.answer != null) return
        val correct = value == current.question.correctAnswer
        _state.value = current.copy(answer = value, correct = correct)
        viewModelScope.launch {
            runCatching {
                progressRepository.recordAnswer(profileId, topic, correct)
                if (correct) progressRepository.completeActivity(profileId, topic)
            }.onFailure { _state.value = _state.value.copy(error = "Não foi possível guardar o progresso.") }
        }
    }

    fun next() {
        val current = _state.value
        _state.value = ActivityUiState(topic, (current.index + 1) % current.questions.size)
    }

    fun previous() {
        val current = _state.value
        _state.value = ActivityUiState(topic, (current.index + current.questions.size - 1) % current.questions.size)
    }
}

class ActivityViewModelFactory(
    private val topic: LearningTopic,
    private val profileId: Long,
    private val progressRepository: ProgressRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(ActivityViewModel::class.java))
        return ActivityViewModel(topic, profileId, progressRepository) as T
    }
}
