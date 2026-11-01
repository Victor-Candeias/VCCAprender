package com.vcc.aprender.ui.editor

import android.content.Context
import android.net.Uri
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    fun exportTo(context: Context, uri: Uri) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    val json = repository.exportJson()
                    context.contentResolver.openOutputStream(uri, "wt")?.use { stream ->
                        stream.write(json.toByteArray(Charsets.UTF_8))
                    } ?: error("Não foi possível abrir o ficheiro.")
                }
            }.onSuccess { _message.value = "Exportação JSON concluída." }
                .onFailure { _message.value = it.message ?: "Falha ao exportar." }
        }
    }

    fun importFrom(context: Context, uri: Uri) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    val json = context.contentResolver.openInputStream(uri)?.use { stream ->
                        val output = java.io.ByteArrayOutputStream()
                        val buffer = ByteArray(8192)
                        while (true) {
                            val read = stream.read(buffer)
                            if (read < 0) break
                            output.write(buffer, 0, read)
                            require(output.size() <= com.vcc.aprender.data.cards.CardJsonCodec.MAX_BYTES) { "Ficheiro demasiado grande." }
                        }
                        output.toString("UTF-8")
                    } ?: error("Não foi possível ler o ficheiro.")
                    repository.importJson(json)
                }
            }.onSuccess { count -> _message.value = "$count atividades importadas." }
                .onFailure { _message.value = it.message ?: "JSON inválido." }
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
