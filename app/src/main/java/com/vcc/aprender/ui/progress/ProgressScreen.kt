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
import com.vcc.aprender.data.progress.AnswerHistory
import com.vcc.aprender.data.progress.LearningProgress
import com.vcc.aprender.domain.progress.CompetencyReportCalculator
import com.vcc.aprender.domain.model.LearningTopic
import com.vcc.aprender.domain.progress.ProgressSummaryCalculator
import java.text.DateFormat
import java.util.Date

@Composable
fun ProgressScreen(progress: List<LearningProgress>, history: List<AnswerHistory> = emptyList(), onBack: () -> Unit) {
    val report = CompetencyReportCalculator.calculate(history)
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
        item {
            Text("Relatório por competência", style = MaterialTheme.typography.titleLarge)
            if (report.competencies.isEmpty()) {
                Text("O histórico detalhado começa a ser registado nesta versão.")
            }
        }
        items(report.competencies, key = { "competency-${it.topicId}" }) { item ->
            val name = LearningTopic.entries.firstOrNull { it.name == item.topicId }?.title ?: item.topicId
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(name, style = MaterialTheme.typography.titleMedium)
                    Text("Respostas certas: ${item.correct} de ${item.total}")
                    Text("Taxa de acerto: ${item.accuracy}%")
                    LinearProgressIndicator(progress = { item.accuracy / 100f }, modifier = Modifier.fillMaxWidth())
                }
            }
        }
        item { Text("Histórico diário", style = MaterialTheme.typography.titleLarge) }
        if (report.dailyHistory.isEmpty()) {
            item { Text("Ainda não existem respostas registadas no histórico.") }
        }
        items(report.dailyHistory, key = { "day-${it.dayStartUtc}" }) { day ->
            val date = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.forLanguageTag("pt-PT"))
                .format(Date(day.dayStartUtc))
            Text("$date: ${day.correct} respostas certas em ${day.total}")
        }
        item { Text("Últimas respostas", style = MaterialTheme.typography.titleLarge) }
        items(report.recentAnswers, key = { "answer-${it.id}" }) { answer ->
            val name = LearningTopic.entries.firstOrNull { it.name == answer.topicId }?.title ?: answer.topicId
            Text("$name · ${if (answer.correct) "Certa" else "Incorreta"} · " +
                DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, java.util.Locale.forLanguageTag("pt-PT"))
                    .format(Date(answer.answeredAt)))
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
