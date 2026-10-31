package com.vcc.aprender.ui.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vcc.aprender.data.progress.LearningProgress
import com.vcc.aprender.domain.model.LearningTopic

@Composable
fun ProgressScreen(progress: List<LearningProgress>, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("O meu progresso")
        Button(onClick = onBack) { Text("Voltar") }
        if (progress.isEmpty()) Text("Ainda não existem atividades concluídas.")
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(progress, key = { it.id }) { item ->
                val topic = LearningTopic.entries.firstOrNull { it.name == item.topicId }
                Text(topic?.title ?: item.topicId)
                Text("Atividades concluídas: ${item.completedActivities}")
                Text("Respostas certas: ${item.correctAnswers} / ${item.totalAnswers}")
            }
        }
    }
}
