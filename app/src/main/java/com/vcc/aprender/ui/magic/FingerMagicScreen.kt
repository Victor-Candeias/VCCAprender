package com.vcc.aprender.ui.magic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.vcc.aprender.domain.magic.MagicQuestion
import com.vcc.aprender.domain.magic.MagicCategory
import com.vcc.aprender.ui.audio.SpeechControls
import com.vcc.aprender.ui.components.BrandArtwork
import com.vcc.aprender.ui.components.BrandSymbol
import com.vcc.aprender.ui.components.GameCelebration
import com.vcc.aprender.ui.components.GameProgress
import com.vcc.aprender.ui.theme.AprenderColors

@Composable
fun FingerMagicScreen(
    state: FingerMagicUiState,
    onPress: (Int) -> Unit,
    onRelease: (Int) -> Unit,
    onAccessibleReveal: (Int) -> Unit,
    onCancelTouch: () -> Unit,
    onNext: () -> Unit,
    onRestart: () -> Unit,
    onBack: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val cancelTouch by rememberUpdatedState(onCancelTouch)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) cancelTouch()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            cancelTouch()
        }
    }
    val session = state.session
    Surface(color = AprenderColors.Cream, modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.background(AprenderColors.Blue)
                .background(AprenderColors.Cream),
            contentAlignment = Alignment.TopCenter
        ) {
            val scroll = rememberScrollState()
            LaunchedEffect(session.index, session.completed) { scroll.scrollTo(0) }
            Column(
                modifier = Modifier.widthIn(max = 820.dp).fillMaxSize()
                    .verticalScroll(scroll).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(onClick = onBack, modifier = Modifier.align(Alignment.Start).heightIn(min = 48.dp)) {
                    Text("Voltar ao início")
                }
                Text("Magia na ponta dos dedos", style = MaterialTheme.typography.headlineMedium,
                    color = AprenderColors.Navy, modifier = Modifier.semantics { heading() })
                if (session.completed) {
                    BrandArtwork(BrandSymbol.Trophy, modifier = Modifier.size(100.dp), description = null)
                    GameCelebration()
                    Text("Parabéns! Descobriste os ${session.questions.size} sorrisos!",
                        style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center,
                        modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite })
                    Button(onClick = onRestart, modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)) {
                        Text("Jogar novamente")
                    }
                } else {
                    val question = session.question
                    GameProgress(session.index + 1, session.questions.size, "Pergunta")
                    if (question.category != MagicCategory.CLASSIC) {
                        Text(
                            question.category.title,
                            color = AprenderColors.Navy,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.semantics { heading() }
                        )
                    }
                    Text(question.prompt, style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().semantics { heading() })
                    Text("Mantém o dedo num quadrado durante 1 segundo para descobrir a carinha.",
                        textAlign = TextAlign.Center)
                    // Keep the three images and the three squares in matching columns.
                    // Separate rows ensure different label heights never misalign the squares.
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        question.options.forEachIndexed { index, picture ->
                            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                MagicArtwork(picture, Modifier.fillMaxWidth().widthIn(max = 150.dp)
                                    .background(Color.White, RoundedCornerShape(20.dp)).padding(6.dp))
                                Text("${'A' + index} · ${picture.label}", textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        question.options.forEachIndexed { index, picture ->
                            val visible = index in session.visibleWrong || (session.solved && index == question.correctIndex)
                            val progress = if (visible) 1f else if (state.heldOption == index) state.revealProgress else 0f
                            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text("${'A' + index}", style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.clearAndSetSemantics { })
                                MagicSquare(
                                    question = question, index = index, progress = progress, revealed = visible,
                                    happy = index == question.correctIndex,
                                    enabled = !session.solved && !visible,
                                    onPress = { onPress(index) }, onRelease = { onRelease(index) },
                                    onAccessibleReveal = { onAccessibleReveal(index) }
                                )
                            }
                        }
                    }
                    val feedback = when {
                        session.solved -> "Encontraste o sorriso! ${question.explanation}"
                        session.visibleWrong.isNotEmpty() -> "Uma carinha triste. Experimenta outro quadrado!"
                        else -> null
                    }
                    if (feedback != null) {
                        Text(feedback, style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                                .background(if (session.solved) AprenderColors.Mint else AprenderColors.Sunshine, RoundedCornerShape(16.dp))
                                .padding(16.dp).semantics { liveRegion = LiveRegionMode.Polite })
                    }
                    if (session.solved) {
                        Button(onClick = onNext, modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)) {
                            Text(if (session.index == session.questions.lastIndex) "Concluir" else "Seguinte")
                        }
                    }
                    key(question.id) {
                        SpeechControls(
                            textToRead = question.prompt + ". " + question.options.mapIndexed { i, option ->
                                "${'A' + i}: ${option.label}"
                            }.joinToString(". "),
                            feedbackText = feedback
                        )
                    }
                }
                state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        }
    }
}

@Composable
private fun MagicSquare(
    question: MagicQuestion,
    index: Int,
    progress: Float,
    revealed: Boolean,
    happy: Boolean,
    enabled: Boolean,
    onPress: () -> Unit,
    onRelease: () -> Unit,
    onAccessibleReveal: () -> Unit
) {
    val press by rememberUpdatedState(onPress)
    val release by rememberUpdatedState(onRelease)
    val canPress by rememberUpdatedState(enabled)
    var focused by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(16.dp)
    val status = when {
        revealed && happy -> "Sorriso. Resposta correta"
        revealed -> "Cara triste. Tenta outra resposta"
        progress > 0f -> "A revelar"
        else -> "Por revelar"
    }
    Box(
        modifier = Modifier.fillMaxWidth().widthIn(max = 150.dp).aspectRatio(1f)
            .testTag("magic-square-$index")
            .border(if (focused) 4.dp else 2.dp, if (focused) AprenderColors.Blue else AprenderColors.Navy, shape)
            .clip(shape).background(Color.White)
            .pointerInput(question.id) {
                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    if (!canPress) return@awaitEachGesture
                    press()
                    try {
                        var pressed = true
                        while (pressed) {
                            val event = awaitPointerEvent(PointerEventPass.Final)
                            pressed = event.changes.any { it.id == down.id && it.pressed }
                        }
                    } finally {
                        release()
                    }
                }
            }
            .onFocusChanged {
                if (focused && !it.isFocused) release()
                focused = it.isFocused
            }
            .onKeyEvent { event ->
                if (event.key in listOf(Key.Enter, Key.NumPadEnter, Key.Spacebar, Key.DirectionCenter)) {
                    if (enabled && event.type == KeyEventType.KeyDown) press()
                    if (event.type == KeyEventType.KeyUp) release()
                    true
                } else false
            }
            .semantics(mergeDescendants = true) {
                role = Role.Button
                contentDescription = "Quadrado ${'A' + index}, ${question.options[index].label}"
                stateDescription = status
                if (enabled) onClick(label = "Revelar carinha em 1 segundo") { onAccessibleReveal(); true }
                else disabled()
            }.focusable(),
        contentAlignment = Alignment.Center
    ) {
        // No answer is exposed to accessibility until its square is fully revealed.
        MagicFace(happy, Modifier.fillMaxSize().padding(4.dp))
        Box(Modifier.fillMaxSize().background(AprenderColors.Ink.copy(alpha = 1f - progress)))
    }
}
