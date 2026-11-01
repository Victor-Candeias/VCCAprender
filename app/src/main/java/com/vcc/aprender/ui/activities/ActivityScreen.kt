package com.vcc.aprender.ui.activities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.vcc.aprender.ui.components.GameProgress
import com.vcc.aprender.ui.components.GameAnswerButton
import com.vcc.aprender.ui.components.GameFeedback
import com.vcc.aprender.ui.components.GameNavigation
import com.vcc.aprender.ui.components.TopicIllustration
import androidx.compose.foundation.layout.fillMaxWidth

@Composable
fun ActivityScreen(
    state: ActivityUiState,
    onAnswer: (String) -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val question = state.question
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
    Column(
        modifier = Modifier.widthIn(max = 720.dp).fillMaxWidth().verticalScroll(rememberScrollState()).padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(onClick = onBack) { Text("Voltar às atividades") }
        Text(state.topic.title, style = MaterialTheme.typography.headlineMedium)
        GameProgress(state.index + 1, state.questions.size)
        TopicIllustration(state.topic)
        Text(question.prompt, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        SpeechControls(question.prompt + ". " + question.options.joinToString(". "))
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            question.options.forEach { option ->
                GameAnswerButton(
                    text = option,
                    onClick = { onAnswer(option) },
                    enabled = state.answer == null,
                    selected = state.answer == option,
                    correct = state.answer != null && option == question.correctAnswer
                )
            }
        }
        GameFeedback(
            message = if (state.answer == null) null else if (state.correct == true) "Muito bem! Acertaste!" else "Boa tentativa! A resposta é ${question.correctAnswer}.",
            positive = state.correct == true
        )
        state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        GameNavigation(onPrevious = onPrevious, onNext = onNext)
    }
    }
}
