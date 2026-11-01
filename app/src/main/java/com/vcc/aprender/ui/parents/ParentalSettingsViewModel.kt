package com.vcc.aprender.ui.parents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vcc.aprender.data.settings.ParentalSettings
import com.vcc.aprender.data.settings.ParentalSettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ParentalSettingsViewModel(private val repository: ParentalSettingsRepository) : ViewModel() {
    val settings = repository.settings.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), ParentalSettings()
    )

    fun setAnimationsEnabled(enabled: Boolean) {
        viewModelScope.launch { repository.setAnimationsEnabled(enabled) }
    }

    fun setAudioEnabled(enabled: Boolean) {
        viewModelScope.launch { repository.setAudioEnabled(enabled) }
    }

    fun setSpeechRate(rate: Float) {
        viewModelScope.launch { repository.setSpeechRate(rate) }
    }
}

class ParentalSettingsViewModelFactory(
    private val repository: ParentalSettingsRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(ParentalSettingsViewModel::class.java))
        return ParentalSettingsViewModel(repository) as T
    }
}
