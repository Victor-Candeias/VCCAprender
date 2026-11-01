package com.vcc.aprender.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vcc.aprender.ui.theme.AprenderColors
import com.vcc.aprender.ui.accessibility.AnswerAccessibility

@Composable
fun GameProgress(current: Int, total: Int, label: String = "Exercício") {
    val fraction = if (total > 0) (current.toFloat() / total).coerceIn(0f, 1f) else 0f
    val motion = LocalGameAnimationsEnabled.current
    val animated by animateFloatAsState(
        targetValue = fraction,
        animationSpec = if (motion) tween(durationMillis = 280) else snap(),
        label = "Progresso do jogo"
    )
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        Text("$label $current de $total", modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite }, style = MaterialTheme.typography.titleMedium)
        LinearProgressIndicator(
            progress = { animated },
            modifier = Modifier.fillMaxWidth().clearAndSetSemantics { },
            color = AprenderColors.Blue,
            trackColor = AprenderColors.Sky
        )
    }
}

@Composable
fun GameAnswerButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
    selected: Boolean = false,
    correct: Boolean = false,
    prefix: String? = null
) {
    val motion = LocalGameAnimationsEnabled.current
    val tint = when {
        correct -> AprenderColors.Mint
        selected -> AprenderColors.Sunshine
        else -> Color.White
    }
    val background by animateColorAsState(
        targetValue = tint,
        animationSpec = if (motion) tween(durationMillis = 200) else snap(),
        label = "Cor da resposta"
    )
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.025f else 1f,
        animationSpec = if (motion) tween(durationMillis = 170) else snap(),
        label = "Seleção da resposta"
    )
    var focused by remember { mutableStateOf(false) }
    val outline = when {
        focused -> AprenderColors.Blue
        selected -> AprenderColors.Navy
        correct -> AprenderColors.Blue
        else -> Color.Transparent
    }
    val status = AnswerAccessibility.state(selected, correct)
    Card(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth().heightIn(min = 64.dp)
            .onFocusChanged { focused = it.hasFocus }
            .border(width = if (focused) 3.dp else if (selected || correct) 2.dp else 0.dp,
                color = outline, shape = RoundedCornerShape(20.dp))
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .semantics {
                contentDescription = listOfNotNull(prefix, text).joinToString(" ")
                if (status != null) stateDescription = status
            },
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(background).padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (prefix != null) Text(prefix, style = MaterialTheme.typography.titleMedium)
            Text(text, modifier = Modifier.weight(1f, fill = false),
                style = MaterialTheme.typography.titleMedium)
            // Shape and label accompany colour; do not rely on colour alone.
            if (selected || correct) Text(
                text = if (correct) "✓" else "•",
                style = MaterialTheme.typography.titleMedium,
                color = AprenderColors.Ink,
                modifier = Modifier.clearAndSetSemantics { }
            )
        }
    }
}

@Composable
fun GameFeedback(message: String?, positive: Boolean) {
    val motion = LocalGameAnimationsEnabled.current
    AnimatedVisibility(
        visible = message != null,
        enter = if (motion) fadeIn(tween(180)) + expandVertically(tween(220)) else EnterTransition.None,
        exit = if (motion) fadeOut(tween(120)) + shrinkVertically(tween(160)) else ExitTransition.None
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().semantics { liveRegion = LiveRegionMode.Polite },
            shape = RoundedCornerShape(22.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
                    .background(if (positive) AprenderColors.Mint else AprenderColors.Sunshine)
                    .padding(16.dp)
            ) {
                BrandArtwork(
                    if (positive) BrandSymbol.Success else BrandSymbol.Encourage,
                    description = null
                )
                if (positive) GameCelebration()
                Text(
                    text = if (positive) "Resposta correta" else "Boa tentativa",
                    style = MaterialTheme.typography.titleMedium,
                    color = AprenderColors.Ink
                )
                Text(
                    text = message ?: "",
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = AprenderColors.Ink
                )
            }
        }
    }
}

@Composable
fun GameNavigation(onPrevious: (() -> Unit)?, onNext: () -> Unit, nextLabel: String = "Próximo") {
    // Stacking avoids clipped labels at 200% font size and on narrow windows.
    val enlargeText = LocalDensity.current.fontScale >= 1.5f
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val stacked = maxWidth < 410.dp || enlargeText
        if (stacked) {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (onPrevious != null) {
                    OutlinedButton(onClick = onPrevious, modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp)) {
                        Text("Anterior")
                    }
                }
                Button(onClick = onNext, modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp)) {
                    Text(nextLabel)
                }
            }
        } else {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                if (onPrevious != null) {
                    OutlinedButton(onClick = onPrevious, modifier = Modifier.weight(1f).heightIn(min = 52.dp)) {
                        Text("Anterior")
                    }
                }
                Button(onClick = onNext, modifier = Modifier.weight(1f).heightIn(min = 52.dp)) {
                    Text(nextLabel)
                }
            }
        }
    }
}
