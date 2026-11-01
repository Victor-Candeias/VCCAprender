package com.vcc.aprender.ui.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vcc.aprender.domain.cards.AnswerFeedbackFactory
import com.vcc.aprender.ui.audio.SpeechControls
import com.vcc.aprender.ui.components.GameAnswerButton
import com.vcc.aprender.ui.components.GameCompletionBanner
import com.vcc.aprender.ui.components.GameProgress
import com.vcc.aprender.ui.components.GameQuestionTransition

@Composable
fun DiscoverCardsScreen(
    state: DiscoverCardsUiState,
    onChoose: (String) -> Unit,
    onReveal: (Int) -> Unit,
    onNext: () -> Unit,
    onRestart: () -> Unit,
    onBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier.widthIn(max = 720.dp).fillMaxWidth()
                .verticalScroll(rememberScrollState()).padding(20.dp),
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
                GameCompletionBanner(
                    title = "Parabéns! Terminaste os cartões!",
                    message = "Concluíste este baralho. Podes jogar novamente!"
                )
                Button(onClick = onRestart) { Text("Jogar novamente") }
                return@Column
            }

            GameProgress(state.index + 1, state.total, label = "Cartão")
            // Use the card value as transition target so the outgoing card keeps its own text.
            GameQuestionTransition(target = state.card, modifier = Modifier.fillMaxWidth()) { visibleCard ->
                val isCurrent = visibleCard.id == state.card.id
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SpeechControls(
                        visibleCard.question + ". " + visibleCard.options.mapIndexed { index, option ->
                            "${'A' + index}: ${option.label}"
                        }.joinToString(". ")
                    )
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            visibleCard.illustration?.let { illustration ->
                                if (isCurrent) {
                                    Text("Toca nos quadrados para descobrir a imagem")
                                    RevealTilesGrid(
                                        illustration = illustration,
                                        tiles = state.tiles,
                                        enabled = state.answer == null,
                                        onReveal = onReveal
                                    )
                                    Text("Quadrados por revelar: ${state.tiles.remaining}")
                                } else {
                                    Text(illustration, style = MaterialTheme.typography.displayLarge)
                                }
                            }
                            Text(visibleCard.question, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
                        }
                    }
                    visibleCard.options.forEachIndexed { index, option ->
                        val letter = ('A' + index)
                        GameAnswerButton(
                            text = listOfNotNull(option.illustration, option.label).joinToString("  "),
                            prefix = "$letter)",
                            onClick = { onChoose(option.id) },
                            enabled = isCurrent && state.answer == null,
                            selected = isCurrent && state.answer?.selectedOptionId == option.id,
                            correct = isCurrent && state.answer != null && visibleCard.correctOptionId == option.id
                        )
                    }
                    if (isCurrent) {
                        state.answer?.let { answer ->
                            AnswerFeedbackPanel(AnswerFeedbackFactory.from(visibleCard, answer))
                            Button(onClick = onNext) {
                                Text(if (state.index == state.total - 1) "Terminar" else "Próximo cartão")
                            }
                        }
                    }
                }
            }
            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        }
    }
}
