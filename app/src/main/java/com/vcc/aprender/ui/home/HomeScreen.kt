package com.vcc.aprender.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import com.vcc.aprender.domain.model.LearningTopic

@Composable
fun HomeScreen(state: HomeUiState, onRetry: () -> Unit, onTopicClick: (LearningTopic) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text("VCC Aprender", style = MaterialTheme.typography.headlineLarge)
            Text("Aprender, brincar e descobrir!", style = MaterialTheme.typography.bodyLarge)
        }
        when (state) {
            HomeUiState.Loading -> item { CircularProgressIndicator() }
            is HomeUiState.Error -> item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(state.message)
                    Button(onClick = onRetry) { Text("Tentar novamente") }
                }
            }
            is HomeUiState.Success -> items(state.topics, key = { it.name }) { topic ->
                ElevatedCard(
                    onClick = { onTopicClick(topic) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 88.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(topic.title, style = MaterialTheme.typography.titleMedium)
                        Text(topic.description, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}
