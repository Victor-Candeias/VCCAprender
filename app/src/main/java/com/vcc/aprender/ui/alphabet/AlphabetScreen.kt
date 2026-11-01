package com.vcc.aprender.ui.alphabet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.vcc.aprender.ui.components.GameProgress
import com.vcc.aprender.ui.components.GameAnswerButton
import com.vcc.aprender.ui.components.GameFeedback
import com.vcc.aprender.ui.components.GameNavigation
import com.vcc.aprender.ui.components.TopicIllustration
import com.vcc.aprender.domain.model.LearningTopic
import androidx.compose.foundation.layout.fillMaxWidth

@Composable
fun AlphabetScreen(
    state: AlphabetUiState,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onAnswer: (Char) -> Unit,
    onBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
    Column(
        modifier = Modifier.widthIn(max = 720.dp).fillMaxWidth().verticalScroll(rememberScrollState()).padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(onClick = onBack) { Text("Voltar às atividades") }
        Text("Descobre o alfabeto", style = MaterialTheme.typography.headlineMedium)
        GameProgress(state.index + 1, 26, label = "Letra")
        Text("${state.card.letter}  ${state.card.letter.lowercaseChar()}", style = MaterialTheme.typography.displayLarge)
        TopicIllustration(LearningTopic.LETTERS)
        Text(state.card.word, style = MaterialTheme.typography.headlineMedium)
        SpeechControls("Letra ${state.card.letter}. ${state.card.word}. Qual é a primeira letra de ${state.card.word}?")
        Text("Qual é a primeira letra de ${state.card.word}?", textAlign = TextAlign.Center)
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            state.options.forEach { option ->
                GameAnswerButton(
                    text = option.toString(),
                    onClick = { onAnswer(option) },
                    enabled = state.selected == null,
                    selected = state.selected == option,
                    correct = state.selected != null && option == state.card.letter
                )
            }
        }
        GameFeedback(
            message = if (state.selected == null) null else if (state.correct == true) "Muito bem! Acertaste!" else "Boa tentativa! A resposta é ${state.card.letter}.",
            positive = state.correct == true
        )
        if (state.error != null) Text(state.error, color = MaterialTheme.colorScheme.error)
        GameNavigation(onPrevious = onPrevious, onNext = onNext, nextLabel = "Próxima letra")
    }
    }
}
