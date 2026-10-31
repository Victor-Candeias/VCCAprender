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
        if (state.finished) {
            Text("Parabéns! Terminaste os cartões! 🎉", style = MaterialTheme.typography.titleLarge)
            Button(onClick = onRestart) { Text("Jogar novamente") }
            return@Column
        }
        Text("Cartão ${state.index + 1} de ${state.total}")
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
            Button(
                modifier = Modifier.fillMaxWidth().semantics {
                    contentDescription = "Opção $letter: ${option.label}"
                },
                onClick = { onChoose(option.id) },
                enabled = state.answer == null
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("$letter)")
                    option.illustration?.let { Text(it) }
                    Text(option.label)
                }
            }
        }
        state.answer?.let { answer ->
            AnswerFeedbackPanel(AnswerFeedbackFactory.from(state.card, answer))
            Button(onClick = onNext) { Text(if (state.index == state.total - 1) "Terminar" else "Próximo cartão") }
        }
        state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
    }
}
