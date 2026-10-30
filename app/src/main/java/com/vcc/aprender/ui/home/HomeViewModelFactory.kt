package com.vcc.aprender.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vcc.aprender.domain.usecase.GetLearningTopicsUseCase

class HomeViewModelFactory(private val useCase: GetLearningTopicsUseCase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(HomeViewModel::class.java))
        return HomeViewModel(useCase) as T
    }
}
