package com.vcc.aprender.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vcc.aprender.domain.usecase.GetLearningTopicsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val getLearningTopics: GetLearningTopicsUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init { refresh() }

    fun refresh() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            _uiState.value = try {
                HomeUiState.Success(getLearningTopics())
            } catch (error: Exception) {
                HomeUiState.Error("Não foi possível carregar as atividades.")
            }
        }
    }
}
