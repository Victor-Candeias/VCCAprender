package com.vcc.aprender.ui.alphabet

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
fun AlphabetScreen(
    state: AlphabetUiState,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onAnswer: (Char) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(onClick = onBack) { Text("Voltar às atividades") }
        Text("Descobre o alfabeto", style = MaterialTheme.typography.headlineMedium)
        Text("Letra ${state.index + 1} de 26")
        Text("${state.card.letter}  ${state.card.letter.lowercaseChar()}", style = MaterialTheme.typography.displayLarge)
        Text(state.card.emoji, style = MaterialTheme.typography.displayLarge)
        Text(state.card.word, style = MaterialTheme.typography.headlineMedium)
        SpeechControls("Letra ${state.card.letter}. ${state.card.word}. Qual é a primeira letra de ${state.card.word}?")
        Text("Qual é a primeira letra de ${state.card.word}?", textAlign = TextAlign.Center)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            state.options.forEach { option ->
                Button(onClick = { onAnswer(option) }, enabled = state.selected == null) {
                    Text(option.toString())
                }
            }
        }
        if (state.selected != null) {
            Text(if (state.correct == true) "Muito bem! Acertaste! ⭐" else "Boa tentativa! A resposta é ${state.card.letter}.")
        }
        if (state.error != null) Text(state.error, color = MaterialTheme.colorScheme.error)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = onPrevious) { Text("Anterior") }
            Button(onClick = onNext) { Text("Próxima letra") }
        }
    }
}
