package com.vcc.aprender.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vcc.aprender.ui.theme.AprenderColors
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/** Short, deterministic particle celebration; decorative Canvas is hidden from TalkBack. */
@Composable
fun GameCelebration(modifier: Modifier = Modifier) {
    val animate = LocalGameAnimationsEnabled.current
    val progress = remember { Animatable(if (animate) 0f else 1f) }
    LaunchedEffect(animate) {
        if (animate) {
            progress.snapTo(0f)
            progress.animateTo(1f, tween(durationMillis = 700))
        } else {
            progress.snapTo(1f)
        }
    }
    Canvas(
        modifier = modifier.fillMaxWidth().height(76.dp).clearAndSetSemantics { }
    ) {
        val colors = listOf(AprenderColors.Blue, Color(0xFFFFB938), Color(0xFF3E9B70), Color(0xFFEA7255))
        val t = progress.value
        val mid = center
        repeat(14) { i ->
            val angle = (i * 2.0 * PI / 14.0) - PI / 2.0
            val reach = size.minDimension * (0.2f + 0.38f * t)
            val x = mid.x + cos(angle).toFloat() * reach
            val y = mid.y + sin(angle).toFloat() * reach * 0.65f
            val color = colors[i % colors.size]
            if (i % 3 == 0) {
                val radius = size.minDimension * 0.056f
                val path = Path().apply {
                    repeat(10) { point ->
                        val a = point * PI / 5.0 - PI / 2.0
                        val r = if (point % 2 == 0) radius else radius * 0.47f
                        val px = x + cos(a).toFloat() * r
                        val py = y + sin(a).toFloat() * r
                        if (point == 0) moveTo(px, py) else lineTo(px, py)
                    }
                    close()
                }
                drawPath(path, color)
            } else {
                drawCircle(color, radius = size.minDimension * 0.032f, center = Offset(x, y))
            }
        }
    }
}

/** Completion remains readable and functional even when all animations are disabled. */
@Composable
fun GameCompletionBanner(
    title: String = "Parabéns! Concluíste a atividade!",
    message: String = "Boa aventura! Podes voltar a jogar e continuar a aprender."
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        BrandArtwork(BrandSymbol.Trophy, description = "Atividade concluída")
        GameCelebration()
        Text(title, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        Text(message, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
    }
}
