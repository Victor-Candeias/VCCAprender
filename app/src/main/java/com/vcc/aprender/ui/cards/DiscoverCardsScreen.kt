package com.vcc.aprender.ui.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vcc.aprender.ui.audio.SpeechControls
import com.vcc.aprender.domain.cards.AnswerFeedbackFactory
import com.vcc.aprender.ui.components.GameProgress
import com.vcc.aprender.ui.components.GameAnswerButton
import com.vcc.aprender.ui.components.GameFeedback

@Composable
fun DiscoverCardsScreen(
    state: DiscoverCardsUiState,
    onChoose: (String) -> Unit,
    onReveal: (Int) -> Unit,
    onNext: () -> Unit,
    onRestart: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(onClick = onBack) { Text("Voltar ao início") }
        Text("Descobre a Resposta", style = MaterialTheme.typography.headlineMedium)
        if (state.loading) {
            Text("A carregar cartões…")
            return@Column
        }
        if (state.error != null && state.total == 0) {
            Text(state.error)
            return@Column
        }
        if (state.finished) {
            Text("Parabéns! Terminaste os cartões! 🎉", style = MaterialTheme.typography.titleLarge)
            Button(onClick = onRestart) { Text("Jogar novamente") }
            return@Column
        }
        GameProgress(state.index + 1, state.total, label = "Cartão")
        SpeechControls(state.card.question + ". " + state.card.options.mapIndexed { index, option -> "${'A' + index}: ${option.label}" }.joinToString(". "))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                state.card.illustration?.let { illustration ->
                    Text("Toca nos quadrados para descobrir a imagem")
                    RevealTilesGrid(
                        illustration = illustration,
                        tiles = state.tiles,
                        enabled = state.answer == null,
                        onReveal = onReveal
                    )
                    Text("Quadrados por revelar: ${state.tiles.remaining}")
                }
                Text(state.card.question, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
            }
        }
        state.card.options.forEachIndexed { index, option ->
            val letter = ('A' + index)
            GameAnswerButton(
                text = listOfNotNull(option.illustration, option.label).joinToString("  "),
                prefix = "$letter)",
                onClick = { onChoose(option.id) },
                enabled = state.answer == null,
                selected = state.answer?.selectedOptionId == option.id,
                correct = state.answer != null && state.card.correctOptionId == option.id
            )
        }
        state.answer?.let { answer ->
            AnswerFeedbackPanel(AnswerFeedbackFactory.from(state.card, answer))
            Button(onClick = onNext) { Text(if (state.index == state.total - 1) "Terminar" else "Próximo cartão") }
        }
        state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
    }
}
