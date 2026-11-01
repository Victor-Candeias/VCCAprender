package com.vcc.aprender.ui.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vcc.aprender.data.progress.AnswerHistory
import com.vcc.aprender.data.progress.LearningProgress
import com.vcc.aprender.domain.model.LearningTopic
import com.vcc.aprender.domain.progress.CompetencyReportCalculator
import com.vcc.aprender.domain.progress.ProgressSummaryCalculator
import com.vcc.aprender.ui.components.FamilyHeader
import com.vcc.aprender.ui.components.FamilyProgress
import com.vcc.aprender.ui.components.FamilySection
import com.vcc.aprender.ui.theme.AprenderColors
import java.text.DateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProgressScreen(
    progress: List<LearningProgress>,
    history: List<AnswerHistory> = emptyList(),
    onBack: () -> Unit
) {
    val report = CompetencyReportCalculator.calculate(history)
    val summary = ProgressSummaryCalculator.calculate(progress)
    val portuguese = Locale.forLanguageTag("pt-PT")
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            modifier = Modifier.widthIn(max = 1100.dp).fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    FamilyHeader("📊", "Evolução da criança",
                        "Descobre tudo o que já aprendeste!", AprenderColors.Lavender)
                    Button(onClick = onBack, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) {
                        Text("Voltar ao início")
                    }
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                FamilySection("Resumo geral", "🏆", AprenderColors.Sunshine) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Temas praticados: ${summary.activeTopics}")
                        Text("Atividades concluídas: ${summary.completedActivities}")
                        Text("Respostas certas: ${summary.correctAnswers} de ${summary.totalAnswers}")
                        Text("Taxa de acerto: ${summary.accuracyPercent}%")
                        FamilyProgress(summary.accuracyPercent, "Taxa de acerto")
                    }
                }
            }
            if (summary.topics.isEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text("Ainda não existem resultados. Experimenta uma atividade para começar!")
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Relatório por competência", style = MaterialTheme.typography.titleLarge)
                    if (report.competencies.isEmpty()) {
                        Text("O histórico detalhado começa a ser registado nesta versão.")
                    }
                }
            }
            items(report.competencies, key = { "competency-${it.topicId}" }) { item ->
                val name = LearningTopic.entries.firstOrNull { it.name == item.topicId }?.title ?: item.topicId
                FamilySection(name, "🎯", AprenderColors.Mint) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Respostas certas: ${item.correct} de ${item.total}")
                        Text("Taxa de acerto: ${item.accuracy}%")
                        FamilyProgress(item.accuracy, "Taxa de acerto")
                    }
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text("Histórico diário", style = MaterialTheme.typography.titleLarge)
            }
            if (report.dailyHistory.isEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text("Ainda não existem respostas registadas no histórico.")
                }
            }
            items(report.dailyHistory, key = { "day-${it.dayStartUtc}" }) { day ->
                val date = DateFormat.getDateInstance(DateFormat.MEDIUM, portuguese)
                    .format(Date(day.dayStartUtc))
                FamilySection(date, "📅", AprenderColors.Sky) {
                    Text("${day.correct} respostas certas em ${day.total}")
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text("Últimas respostas", style = MaterialTheme.typography.titleLarge)
            }
            items(report.recentAnswers, key = { "answer-${it.id}" }) { answer ->
                val name = LearningTopic.entries.firstOrNull { it.name == answer.topicId }?.title
                    ?: answer.topicId
                FamilySection(
                    name,
                    if (answer.correct) "⭐" else "💪",
                    if (answer.correct) AprenderColors.Mint else AprenderColors.Coral
                ) {
                    Text("${if (answer.correct) "Certa" else "Incorreta"} · " +
                        DateFormat.getDateTimeInstance(
                            DateFormat.SHORT, DateFormat.SHORT, portuguese
                        ).format(Date(answer.answeredAt)))
                }
            }
            if (summary.topics.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text("Resultados por tema", style = MaterialTheme.typography.titleLarge)
                }
                items(summary.topics, key = { "topic-${it.topicId}" }) { topic ->
                    val name = LearningTopic.entries.firstOrNull { it.name == topic.topicId }?.title
                        ?: topic.topicId
                    FamilySection(name, "📚", AprenderColors.Sky) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("Respostas certas: ${topic.correctAnswers} de ${topic.totalAnswers}")
                            Text("Taxa de acerto: ${topic.accuracyPercent}%")
                            FamilyProgress(topic.accuracyPercent, "Taxa de acerto")
                            Text("Atividades concluídas: ${topic.completedActivities}")
                            if (topic.lastActivityAt > 0) {
                                Text("Última atividade: " +
                                    DateFormat.getDateInstance(DateFormat.MEDIUM, portuguese)
                                        .format(Date(topic.lastActivityAt)))
                            }
                        }
                    }
                }
            }
        }
    }
}
