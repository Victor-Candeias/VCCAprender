package com.vcc.aprender.ui.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vcc.aprender.data.progress.LearningProgress
import com.vcc.aprender.domain.model.LearningTopic
import com.vcc.aprender.domain.progress.ProgressSummaryCalculator
import java.text.DateFormat
import java.util.Date

@Composable
fun ProgressScreen(progress: List<LearningProgress>, onBack: () -> Unit) {
    val summary = ProgressSummaryCalculator.calculate(progress)
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Evolução da criança", style = MaterialTheme.typography.headlineMedium)
            Button(onClick = onBack) { Text("Voltar ao início") }
        }
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Resumo geral", style = MaterialTheme.typography.titleLarge)
                    Text("Temas praticados: ${summary.activeTopics}")
                    Text("Atividades concluídas: ${summary.completedActivities}")
                    Text("Respostas certas: ${summary.correctAnswers} de ${summary.totalAnswers}")
                    Text("Taxa de acerto: ${summary.accuracyPercent}%")
                    LinearProgressIndicator(
                        progress = { summary.accuracyPercent / 100f },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        if (summary.topics.isEmpty()) {
            item { Text("Ainda não existem resultados. Experimenta uma atividade para começar!") }
        } else {
            item { Text("Resultados por tema", style = MaterialTheme.typography.titleLarge) }
            items(summary.topics, key = { it.topicId }) { topic ->
                val name = LearningTopic.entries.firstOrNull { it.name == topic.topicId }?.title ?: topic.topicId
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(name, style = MaterialTheme.typography.titleMedium)
                        Text("Respostas certas: ${topic.correctAnswers} de ${topic.totalAnswers}")
                        Text("Taxa de acerto: ${topic.accuracyPercent}%")
                        LinearProgressIndicator(
                            progress = { topic.accuracyPercent / 100f },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text("Atividades concluídas: ${topic.completedActivities}")
                        if (topic.lastActivityAt > 0) {
                            Text("Última atividade: ${DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.forLanguageTag("pt-PT")).format(Date(topic.lastActivityAt))}")
                        }
                    }
                }
            }
        }
    }
}
