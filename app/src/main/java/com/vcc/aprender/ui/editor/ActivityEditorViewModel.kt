package com.vcc.aprender.ui.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vcc.aprender.data.cards.CardDraft
import com.vcc.aprender.data.cards.CustomCardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ActivityEditorViewModel(private val repository: CustomCardRepository) : ViewModel() {
    val cards = repository.cards.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun save(draft: CardDraft, id: Long = 0) {
        viewModelScope.launch {
            runCatching { repository.save(draft, id) }
                .onSuccess { _message.value = "Atividade guardada." }
                .onFailure { _message.value = it.message ?: "Não foi possível guardar." }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            runCatching { repository.delete(id) }
                .onSuccess { _message.value = "Atividade eliminada." }
                .onFailure { _message.value = "Não foi possível eliminar." }
        }
    }

    fun clearMessage() { _message.value = null }
}

class ActivityEditorViewModelFactory(private val repository: CustomCardRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(ActivityEditorViewModel::class.java))
        return ActivityEditorViewModel(repository) as T
    }
}
