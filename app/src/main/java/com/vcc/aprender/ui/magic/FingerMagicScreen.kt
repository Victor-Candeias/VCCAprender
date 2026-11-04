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
                    Text("Mantém o dedo sobre a imagem durante 1 segundo para escolher a resposta. Só tens uma tentativa.",
                        textAlign = TextAlign.Center)
                    val wrongAnswer = session.answered && !session.solved
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        question.options.forEachIndexed { index, picture ->
                            // After an incorrect attempt only the actual correct image remains.
                            if (!wrongAnswer || index == question.correctIndex) {
                                val showHappySmile = session.solved && index == question.correctIndex
                                val progress = if (state.heldOption == index && !session.answered)
                                    state.revealProgress else 0f
                                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    MagicAnswerImage(
                                        question = question,
                                        index = index,
                                        progress = progress,
                                        showHappySmile = showHappySmile,
                                        correctOnly = wrongAnswer,
                                        enabled = !session.answered,
                                        onPress = { onPress(index) },
                                        onRelease = { onRelease(index) },
                                        onAccessibleReveal = { onAccessibleReveal(index) }
                                    )
                                    Text("${'A' + index} · ${picture.label}", textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleMedium)
                                }
                            }
                        }
                    }
                    val correctExplanation =
                        "A resposta correta é ${question.options[question.correctIndex].label}. ${question.explanation}"
                    val feedback = when {
                        session.solved -> "Muito bem! ${question.explanation}"
                        wrongAnswer -> "Resposta errada! $correctExplanation"
                        else -> null
                    }
                    if (wrongAnswer) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .background(AprenderColors.Sunshine, RoundedCornerShape(16.dp))
                                .padding(16.dp)
                                .semantics { liveRegion = LiveRegionMode.Polite },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "Resposta errada!",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.testTag("magic-wrong-feedback")
                            )
                            Text(
                                correctExplanation,
                                color = AprenderColors.Navy,
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else if (feedback != null) {
                        Text(feedback, style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                                .background(AprenderColors.Mint, RoundedCornerShape(16.dp))
                                .padding(16.dp).semantics { liveRegion = LiveRegionMode.Polite })
                    }
                    if (session.canAdvance) {
                        Button(onClick = onNext, modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)) {
                            Text(if (session.index == session.questions.lastIndex) "Concluir" else "Pergunta seguinte")
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
private fun MagicAnswerImage(
    question: MagicQuestion,
    index: Int,
    progress: Float,
    showHappySmile: Boolean,
    correctOnly: Boolean,
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
        showHappySmile -> "Sorriso. Resposta correta"
        correctOnly -> "Resposta correta"
        progress > 0f -> "A revelar"
        else -> "Por revelar"
    }
    Box(
        modifier = Modifier.fillMaxWidth().widthIn(max = 150.dp).aspectRatio(1f)
            .testTag("magic-image-$index")
            .border(if (focused) 4.dp else 2.dp,
                if (focused) AprenderColors.Blue
                else if (showHappySmile || correctOnly) AprenderColors.Mint
                else AprenderColors.Navy, shape)
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
                    if (canPress && event.type == KeyEventType.KeyDown) press()
                    if (event.type == KeyEventType.KeyUp) release()
                    true
                } else false
            }
            .semantics(mergeDescendants = true) {
                role = Role.Button
                contentDescription = "Imagem ${'A' + index}, ${question.options[index].label}"
                stateDescription = status
                if (enabled) onClick(label = "Selecionar imagem") { onAccessibleReveal(); true }
                else disabled()
            }.focusable(),
        contentAlignment = Alignment.Center
    ) {
        MagicArtwork(question.options[index], Modifier.fillMaxSize().padding(6.dp))
        if (progress > 0f && !showHappySmile) {
            Box(
                Modifier.fillMaxSize()
                    .background(AprenderColors.Ink.copy(alpha = progress * 0.30f))
            )
        }
        if (showHappySmile) {
            Box(
                Modifier.fillMaxSize()
                    .background(Color.White.copy(alpha = 0.72f)),
                contentAlignment = Alignment.Center
            ) {
                MagicFace(true, Modifier.fillMaxSize(0.70f))
            }
        }
    }
}
