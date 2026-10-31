package com.vcc.aprender.ui.activities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vcc.aprender.ui.audio.SpeechControls

@Composable
fun ActivityScreen(
    state: ActivityUiState,
    onAnswer: (String) -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val question = state.question
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(onClick = onBack) { Text("Voltar às atividades") }
        Text(state.topic.title, style = MaterialTheme.typography.headlineMedium)
        Text("Exercício ${state.index + 1} de ${state.questions.size}")
        Text(question.illustration, style = MaterialTheme.typography.displayLarge, textAlign = TextAlign.Center)
        Text(question.prompt, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        SpeechControls(question.prompt + ". " + question.options.joinToString(". "))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            question.options.forEach { option ->
                Button(onClick = { onAnswer(option) }, enabled = state.answer == null) {
                    Text(option)
                }
            }
        }
        if (state.answer != null) {
            Text(if (state.correct == true) "Muito bem! ⭐" else "Boa tentativa! A resposta é ${question.correctAnswer}.")
        }
        state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = onPrevious) { Text("Anterior") }
            Button(onClick = onNext) { Text("Próximo") }
        }
    }
}
