package com.vcc.aprender.ui.alphabet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vcc.aprender.domain.alphabet.AlphabetContent
import com.vcc.aprender.domain.model.LearningTopic
import com.vcc.aprender.ui.audio.SpeechControls
import com.vcc.aprender.ui.components.GameAnswerButton
import com.vcc.aprender.ui.components.GameCompletionBanner
import com.vcc.aprender.ui.components.GameFeedback
import com.vcc.aprender.ui.components.GameNavigation
import com.vcc.aprender.ui.components.GameProgress
import com.vcc.aprender.ui.components.GameQuestionTransition
import com.vcc.aprender.ui.components.TopicIllustration

@Composable
fun AlphabetScreen(
    state: AlphabetUiState,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onAnswer: (Char) -> Unit,
    onBack: () -> Unit
) {
    val lastLetter = AlphabetContent.cards.lastIndex
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier.widthIn(max = 720.dp).fillMaxWidth()
                .verticalScroll(rememberScrollState()).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(onClick = onBack) { Text("Voltar às atividades") }
            Text("Descobre o alfabeto", style = MaterialTheme.typography.headlineMedium)
            GameProgress(state.index + 1, AlphabetContent.cards.size, label = "Letra")
            GameQuestionTransition(target = state.index, modifier = Modifier.fillMaxWidth()) { visibleIndex ->
                val card = AlphabetContent.cards[visibleIndex]
                val options = AlphabetContent.optionsFor(visibleIndex)
                val isCurrent = visibleIndex == state.index
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("${card.letter}  ${card.letter.lowercaseChar()}", style = MaterialTheme.typography.displayLarge)
                    TopicIllustration(LearningTopic.LETTERS)
                    Text(card.word, style = MaterialTheme.typography.headlineMedium)
                    SpeechControls("Letra ${card.letter}. ${card.word}. Qual é a primeira letra de ${card.word}?")
                    Text("Qual é a primeira letra de ${card.word}?", textAlign = TextAlign.Center)
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        options.forEach { option ->
                            GameAnswerButton(
                                text = option.toString(),
                                onClick = { onAnswer(option) },
                                enabled = isCurrent && state.selected == null,
                                selected = isCurrent && state.selected == option,
                                correct = isCurrent && state.selected != null && option == card.letter
                            )
                        }
                    }
                    if (isCurrent) {
                        GameFeedback(
                            message = if (state.selected == null) null
                                else if (state.correct == true) "Muito bem! Acertaste!"
                                else "Boa tentativa! A resposta é ${card.letter}.",
                            positive = state.correct == true
                        )
                        if (state.selected != null && visibleIndex == lastLetter) {
                            GameCompletionBanner(
                                title = "Parabéns! Chegaste ao fim do alfabeto!",
                                message = "Podes recomeçar e treinar as letras novamente."
                            )
                        }
                    }
                }
            }
            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            GameNavigation(
                onPrevious = onPrevious, onNext = onNext,
                nextLabel = if (state.index == lastLetter) "Recomeçar" else "Próxima letra"
            )
        }
    }
}
