package com.vcc.aprender.ui.home

import com.vcc.aprender.domain.model.LearningTopic

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val topics: List<LearningTopic>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
