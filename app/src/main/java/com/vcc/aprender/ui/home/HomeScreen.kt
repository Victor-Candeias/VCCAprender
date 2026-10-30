package com.vcc.aprender.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(state: HomeUiState, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("VCC Aprender", style = MaterialTheme.typography.headlineLarge)
        Text("Aprender, brincar e descobrir!")
        when (state) {
            HomeUiState.Loading -> CircularProgressIndicator()
            is HomeUiState.Error -> {
                Text(state.message)
                Button(onClick = onRetry) { Text("Tentar novamente") }
            }
            is HomeUiState.Success -> state.topics.forEach { topic ->
                Text(topic.title, style = MaterialTheme.typography.titleMedium)
                Text(topic.description)
            }
        }
    }
}
