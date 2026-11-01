package com.vcc.aprender.ui.profiles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vcc.aprender.data.profile.ChildProfile
import com.vcc.aprender.data.profile.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {
    val profiles: StateFlow<List<ChildProfile>> = repository.profiles.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val selectedId: StateFlow<Long?> = repository.selectedId.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), null
    )
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun clearError() { _error.value = null }

    fun save(id: Long, name: String, age: Int, avatar: String) = viewModelScope.launch {
        runCatching { repository.save(id, name, age, avatar) }
            .onFailure { _error.value = it.message ?: "Erro ao guardar o perfil." }
    }

    fun select(id: Long) = viewModelScope.launch {
        runCatching { repository.select(id) }
            .onFailure { _error.value = it.message ?: "Erro ao selecionar o perfil." }
    }

    fun deleteAll() = viewModelScope.launch {
        runCatching { repository.deleteAll() }
            .onFailure { _error.value = it.message ?: "Erro ao eliminar os dados." }
    }

    fun delete(profile: ChildProfile) = viewModelScope.launch {
        runCatching { repository.delete(profile) }
            .onFailure { _error.value = it.message ?: "Erro ao eliminar o perfil." }
    }
}

class ProfileViewModelFactory(private val repository: ProfileRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(ProfileViewModel::class.java))
        return ProfileViewModel(repository) as T
    }
}
