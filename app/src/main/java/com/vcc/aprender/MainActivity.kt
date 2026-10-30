package com.vcc.aprender

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vcc.aprender.di.AppContainer
import com.vcc.aprender.ui.home.HomeScreen
import com.vcc.aprender.ui.home.HomeViewModel
import com.vcc.aprender.ui.home.HomeViewModelFactory

class MainActivity : ComponentActivity() {
    private val container by lazy { AppContainer() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { VccAprenderApp(container) }
    }
}

@Composable
fun VccAprenderApp(container: AppContainer) {
    val model: HomeViewModel = viewModel(factory = HomeViewModelFactory(container.getLearningTopics))
    val state by model.uiState.collectAsStateWithLifecycle()
    MaterialTheme {
        HomeScreen(state = state, onRetry = model::refresh)
    }
}
