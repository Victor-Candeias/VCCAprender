package com.vcc.aprender.ui.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.vcc.aprender.ui.components.AdaptiveTwoPaneScreen
import com.vcc.aprender.ui.components.LearningArtwork
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
        if (state.loading || state.error != null && state.total == 0 || state.finished) {
            AdaptiveTwoPaneScreen(
                primary = {
                    OutlinedButton(onClick = onBack) { Text("Voltar ao início") }
                    Text("Descobre a Resposta", style = MaterialTheme.typography.headlineMedium)
                    if (state.loading) Text("A carregar cartões…")
                    else if (state.error != null && state.total == 0) Text(state.error)
                    else GameCompletionBanner(
                        title = "Parabéns! Terminaste os cartões!",
                        message = "Concluíste este baralho. Podes jogar novamente!"
                    )
                },
                secondary = {
                    if (state.finished) {
                        Button(onClick = onRestart, modifier = Modifier.fillMaxWidth()) {
                            Text("Jogar novamente")
                        }
                    }
                }
            )
        } else {
            // The card is the transition key; an outgoing card cannot submit a new answer.
            GameQuestionTransition(target = state.card, modifier = Modifier.fillMaxSize()) { visibleCard ->
                val current = visibleCard.id == state.card.id
                AdaptiveTwoPaneScreen(
                    primary = {
                        OutlinedButton(onClick = onBack) { Text("Voltar ao início") }
                        Text("Descobre a Resposta", style = MaterialTheme.typography.headlineMedium)
                        GameProgress(state.index + 1, state.total, label = "Cartão")
                        SpeechControls(
                            textToRead = visibleCard.question + ". " + visibleCard.options.mapIndexed { index, option ->
                                "${'A' + index}: ${option.label}"
                            }.joinToString(". "),
                            feedbackText = if (current) state.answer?.let { answer ->
                                AnswerFeedbackFactory.from(visibleCard, answer).let { "${it.title} ${it.message}" }
                            } else null
                        )
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                visibleCard.illustration?.let { illustration ->
                                    if (current) {
                                        Text("Toca nos quadrados para descobrir a imagem")
                                        RevealTilesGrid(
                                            illustration = illustration,
                                            tiles = state.tiles,
                                            enabled = state.answer == null,
                                            onReveal = onReveal
                                        )
                                        Text("Quadrados por revelar: ${state.tiles.remaining}")
                                    } else {
                                        LearningArtwork(illustration)
                                    }
                                }
                                Text(
                                    visibleCard.question,
                                    style = MaterialTheme.typography.titleLarge,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    },
                    secondary = {
                        Text("Escolhe uma resposta", style = MaterialTheme.typography.titleMedium)
                        visibleCard.options.forEachIndexed { index, option ->
                            val letter = ('A' + index)
                            GameAnswerButton(
                                text = option.label,
                                illustration = option.illustration,
                                prefix = "$letter)",
                                onClick = { onChoose(option.id) },
                                enabled = current && state.answer == null,
                                selected = current && state.answer?.selectedOptionId == option.id,
                                correct = current && state.answer != null &&
                                    visibleCard.correctOptionId == option.id
                            )
                        }
                        if (current) {
                            state.answer?.let { answer ->
                                AnswerFeedbackPanel(AnswerFeedbackFactory.from(visibleCard, answer))
                                Button(onClick = onNext, modifier = Modifier.fillMaxWidth()) {
                                    Text(if (state.index == state.total - 1) "Terminar" else "Próximo cartão")
                                }
                            }
                            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                        }
                    }
                )
            }
        }
    }
}
