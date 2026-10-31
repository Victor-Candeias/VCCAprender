package com.vcc.aprender.ui.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vcc.aprender.data.progress.LearningProgress
import com.vcc.aprender.data.progress.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class ProgressViewModel(private val repository: ProgressRepository) : ViewModel() {
    private val profileId = MutableStateFlow<Long?>(null)
    val progress = profileId.flatMapLatest { id ->
        if (id == null) flowOf(emptyList()) else repository.observe(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<LearningProgress>())

    fun selectProfile(id: Long?) { profileId.value = id }
}

class ProgressViewModelFactory(private val repository: ProgressRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(ProgressViewModel::class.java))
        return ProgressViewModel(repository) as T
    }
}
