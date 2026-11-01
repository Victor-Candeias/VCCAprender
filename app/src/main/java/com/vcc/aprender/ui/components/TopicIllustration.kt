package com.vcc.aprender.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.vcc.aprender.domain.model.LearningTopic
import com.vcc.aprender.ui.theme.AprenderColors

/** Original, offline vector artwork drawn with Compose Canvas (no emoji font dependency). */
@Composable
fun TopicIllustration(topic: LearningTopic, modifier: Modifier = Modifier) {
    val background = when (topic) {
        LearningTopic.LETTERS -> AprenderColors.Sky
        LearningTopic.NUMBERS -> AprenderColors.Sunshine
        LearningTopic.COLORS -> AprenderColors.Coral
        LearningTopic.SHAPES -> AprenderColors.Lavender
        LearningTopic.ANIMALS -> AprenderColors.Mint
        LearningTopic.SOUNDS -> AprenderColors.Sunshine
        LearningTopic.WORDS -> AprenderColors.Lavender
    }
    Canvas(
        modifier = modifier.size(76.dp).background(background, CircleShape)
            .semantics { contentDescription = "Ilustração de ${topic.title}" }
    ) {
        val unit = size.minDimension / 100f
        fun dot(x: Float, y: Float, radius: Float, color: Color) =
            drawCircle(color, radius * unit, Offset(x * unit, y * unit))
        fun stroke(x: Float, y: Float, endX: Float, endY: Float, color: Color, width: Float = 7f) =
            drawLine(color, Offset(x * unit, y * unit), Offset(endX * unit, endY * unit), width * unit)
        fun tile(x: Float, y: Float, w: Float, h: Float, color: Color) =
            drawRoundRect(color, Offset(x * unit, y * unit), Size(w * unit, h * unit),
                androidx.compose.ui.geometry.CornerRadius(8 * unit))
        when (topic) {
            LearningTopic.LETTERS -> {
                stroke(26f, 76f, 45f, 22f, AprenderColors.Blue, 9f)
                stroke(45f, 22f, 64f, 76f, AprenderColors.Blue, 9f)
                stroke(33f, 58f, 57f, 58f, AprenderColors.Blue, 8f)
                dot(73f, 29f, 7f, Color(0xFFFF9C6B))
            }
            LearningTopic.NUMBERS -> {
                tile(23f, 20f, 54f, 60f, Color.White)
                stroke(44f, 36f, 53f, 29f, AprenderColors.Blue)
                stroke(53f, 29f, 53f, 66f, AprenderColors.Blue)
                stroke(41f, 67f, 65f, 67f, AprenderColors.Blue)
            }
            LearningTopic.COLORS -> {
                dot(39f, 40f, 19f, Color(0xFFEC655E))
                dot(63f, 40f, 19f, Color(0xFF3E91DF))
                dot(50f, 62f, 19f, Color(0xFFFFC44D))
                dot(50f, 47f, 9f, Color.White)
            }
            LearningTopic.SHAPES -> {
                dot(33f, 36f, 17f, AprenderColors.Blue)
                tile(49f, 43f, 32f, 32f, Color(0xFFED7A56))
                val triangle = Path().apply {
                    moveTo(23f * unit, 79f * unit)
                    lineTo(45f * unit, 79f * unit)
                    lineTo(34f * unit, 58f * unit)
                    close()
                }
                drawPath(triangle, Color(0xFF3E9B70))
            }
            LearningTopic.ANIMALS -> {
                dot(50f, 57f, 29f, Color(0xFFB77B50))
                dot(29f, 32f, 14f, Color(0xFFB77B50))
                dot(71f, 32f, 14f, Color(0xFFB77B50))
                dot(40f, 52f, 4f, AprenderColors.Ink)
                dot(60f, 52f, 4f, AprenderColors.Ink)
                dot(50f, 65f, 7f, Color(0xFF51362E))
            }
            LearningTopic.SOUNDS -> {
                dot(42f, 72f, 12f, AprenderColors.Blue)
                stroke(51f, 70f, 51f, 25f, AprenderColors.Blue, 8f)
                stroke(51f, 25f, 74f, 20f, AprenderColors.Blue, 8f)
                stroke(74f, 20f, 74f, 52f, AprenderColors.Blue, 8f)
                dot(65f, 57f, 11f, AprenderColors.Blue)
            }
            LearningTopic.WORDS -> {
                tile(18f, 24f, 64f, 54f, Color.White)
                stroke(31f, 40f, 68f, 40f, AprenderColors.Blue, 5f)
                stroke(31f, 52f, 68f, 52f, AprenderColors.Blue, 5f)
                stroke(31f, 64f, 54f, 64f, AprenderColors.Blue, 5f)
            }
        }
    }
}
