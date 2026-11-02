package com.vcc.aprender.ui.magic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.semantics.clearAndSetSemantics
import com.vcc.aprender.domain.magic.MagicPicture
import com.vcc.aprender.ui.theme.AprenderColors

private val Ink = AprenderColors.Ink
private val Red = Color(0xFFE04449)
private val Green = Color(0xFF348555)
private val Gold = Color(0xFFFFCB45)
private val Orange = Color(0xFFEF923D)
private val Brown = Color(0xFF945C3B)

/** Original question-specific vectors, available offline at every display density. */
@Composable
fun MagicArtwork(picture: MagicPicture, modifier: Modifier = Modifier) {
    Canvas(modifier.aspectRatio(1f).clearAndSetSemantics { }) {
        val unit = size.minDimension / 100f
        withTransform({
            translate((size.width - unit * 100) / 2, (size.height - unit * 100) / 2)
            scale(unit, unit, pivot = Offset.Zero)
        }) {
            when (picture) {
                MagicPicture.CAT -> {
                    polygon(Orange, 22f, 45f, 20f, 15f, 42f, 32f, 59f, 32f, 80f, 15f, 78f, 45f)
                    oval(Orange, 20f, 30f, 60f, 55f)
                    polygon(Color(0xFFF4B5A4), 25f, 34f, 25f, 23f, 36f, 33f)
                    polygon(Color(0xFFF4B5A4), 64f, 33f, 75f, 23f, 75f, 34f)
                    eyes(37f, 63f, 52f)
                    polygon(Red, 45f, 62f, 55f, 62f, 50f, 68f)
                    line(50f, 68f, 43f, 74f, Ink, 2f); line(50f, 68f, 57f, 74f, Ink, 2f)
                    for (y in listOf(60f, 68f)) {
                        line(15f, y - 3, 35f, y, Ink, 2f); line(65f, y, 85f, y - 3, Ink, 2f)
                    }
                }
                MagicPicture.DOG -> {
                    oval(Brown, 10f, 27f, 25f, 53f); oval(Brown, 65f, 27f, 25f, 53f)
                    oval(Color(0xFFDDA86B), 25f, 23f, 50f, 62f)
                    eyes(38f, 62f, 47f)
                    oval(Color(0xFFFFE1AF), 34f, 55f, 32f, 23f)
                    oval(Ink, 43f, 57f, 14f, 10f)
                    oval(Color(0xFFED8F94), 46f, 69f, 10f, 16f)
                }
                MagicPicture.BIRD -> {
                    line(42f, 72f, 38f, 86f, Brown, 3f); line(60f, 72f, 62f, 86f, Brown, 3f)
                    line(32f, 86f, 44f, 86f, Brown, 3f); line(56f, 86f, 69f, 86f, Brown, 3f)
                    polygon(AprenderColors.Blue, 31f, 57f, 9f, 42f, 16f, 71f, 39f, 71f)
                    oval(Color(0xFF61B5DD), 25f, 34f, 49f, 43f)
                    oval(AprenderColors.Blue, 30f, 45f, 30f, 22f)
                    dot(64f, 32f, 18f, Color(0xFF61B5DD))
                    polygon(Orange, 77f, 29f, 94f, 36f, 77f, 41f)
                    dot(68f, 28f, 3f, Ink)
                }
                MagicPicture.FISH -> {
                    polygon(Orange, 68f, 50f, 93f, 27f, 93f, 73f)
                    polygon(Gold, 35f, 31f, 54f, 15f, 62f, 36f)
                    oval(Orange, 10f, 29f, 66f, 43f)
                    oval(Gold, 42f, 40f, 22f, 20f)
                    dot(26f, 44f, 5f, Color.White); dot(26f, 44f, 2.7f, Ink)
                    line(12f, 56f, 22f, 57f, Ink, 2f)
                    dot(13f, 21f, 4f, Color(0xFF8DD8F0)); dot(22f, 10f, 3f, Color(0xFF8DD8F0))
                }
                MagicPicture.APPLE -> {
                    line(49f, 32f, 53f, 15f, Brown, 5f)
                    oval(Green, 53f, 13f, 24f, 12f)
                    oval(Red, 17f, 29f, 41f, 54f); oval(Red, 43f, 29f, 41f, 54f)
                    line(31f, 43f, 27f, 54f, Color(0xFFFFA8A7), 5f)
                }
                MagicPicture.BANANA -> {
                    val banana = Path().apply {
                        moveTo(19f, 28f); cubicTo(28f, 69f, 63f, 80f, 83f, 24f)
                        lineTo(87f, 39f); cubicTo(72f, 99f, 21f, 91f, 13f, 36f); close()
                    }
                    drawPath(banana, Gold)
                    line(19f, 29f, 16f, 22f, Brown, 6f); line(83f, 25f, 85f, 18f, Brown, 5f)
                    val crease = Path().apply { moveTo(24f, 48f); cubicTo(42f, 82f, 67f, 79f, 79f, 48f) }
                    drawPath(crease, Color(0xFFD79C19), style = Stroke(2f))
                }
                MagicPicture.CARROT -> {
                    line(56f, 29f, 55f, 9f, Green, 7f); line(61f, 30f, 76f, 15f, Green, 7f)
                    line(51f, 30f, 38f, 14f, Green, 7f)
                    polygon(Orange, 34f, 31f, 74f, 37f, 31f, 92f)
                    line(43f, 46f, 55f, 49f, Brown, 2f); line(39f, 60f, 48f, 63f, Brown, 2f)
                }
                MagicPicture.STRAWBERRY -> {
                    val fruit = Path().apply {
                        moveTo(50f, 88f); cubicTo(5f, 60f, 13f, 20f, 50f, 32f)
                        cubicTo(87f, 20f, 95f, 60f, 50f, 88f); close()
                    }
                    drawPath(fruit, Red)
                    polygon(Green, 50f, 19f, 56f, 29f, 73f, 25f, 64f, 37f, 50f, 34f, 37f, 40f, 28f, 26f, 44f, 29f)
                    for ((x, y) in listOf(31f to 45f, 49f to 46f, 67f to 44f, 39f to 59f, 59f to 59f, 49f to 73f)) {
                        oval(Gold, x - 1.5f, y - 3, 3f, 6f)
                    }
                }
                MagicPicture.RED, MagicPicture.BLUE, MagicPicture.YELLOW -> {
                    val color = when (picture) {
                        MagicPicture.RED -> Red
                        MagicPicture.BLUE -> AprenderColors.Blue
                        else -> Gold
                    }
                    dot(50f, 50f, 34f, color)
                    oval(Color.White.copy(alpha = 0.5f), 29f, 25f, 16f, 10f)
                }
                MagicPicture.CIRCLE -> dot(50f, 50f, 34f, AprenderColors.Blue)
                MagicPicture.TRIANGLE -> polygon(Orange, 50f, 13f, 90f, 83f, 10f, 83f)
                MagicPicture.SQUARE -> drawRect(Green, Offset(19f, 19f), Size(62f, 62f))
                MagicPicture.SUN -> {
                    repeat(8) { i ->
                        val angle = Math.PI * i / 4
                        line(50f + 32 * kotlin.math.cos(angle).toFloat(), 50f + 32 * kotlin.math.sin(angle).toFloat(),
                            50f + 43 * kotlin.math.cos(angle).toFloat(), 50f + 43 * kotlin.math.sin(angle).toFloat(), Orange, 5f)
                    }
                    dot(50f, 50f, 25f, Gold)
                }
                MagicPicture.CLOUD -> {
                    oval(Color(0xFFB8D8EB), 10f, 43f, 80f, 30f)
                    dot(36f, 43f, 21f, Color(0xFFB8D8EB)); dot(60f, 37f, 24f, Color(0xFFB8D8EB))
                }
                MagicPicture.SNOW -> {
                    repeat(6) { i ->
                        val angle = Math.PI * i / 3
                        fun p(r: Float, a: Double) = Offset(50 + r * kotlin.math.cos(a).toFloat(), 50 + r * kotlin.math.sin(a).toFloat())
                        drawLine(AprenderColors.Blue, p(0f, angle), p(37f, angle), 4f, StrokeCap.Round)
                        drawLine(AprenderColors.Blue, p(24f, angle), p(35f, angle - .24), 3f, StrokeCap.Round)
                        drawLine(AprenderColors.Blue, p(24f, angle), p(35f, angle + .24), 3f, StrokeCap.Round)
                    }
                }
                MagicPicture.UMBRELLA -> {
                    line(50f, 19f, 50f, 76f, Brown, 4f)
                    drawArc(Brown, 0f, 180f, false, Offset(33f, 66f), Size(17f, 21f), style = Stroke(4f))
                    val canopy = Path().apply {
                        moveTo(9f, 50f); cubicTo(12f, 4f, 88f, 4f, 91f, 50f)
                        quadraticTo(78f, 42f, 65f, 50f); quadraticTo(50f, 42f, 35f, 50f)
                        quadraticTo(22f, 42f, 9f, 50f); close()
                    }
                    drawPath(canopy, AprenderColors.Blue)
                    line(50f, 18f, 50f, 46f, Color(0xFF8DD8F0), 3f)
                }
                MagicPicture.RAINBOW -> {
                    listOf(Red, Orange, Gold, Green, AprenderColors.Blue, Color(0xFF8865B8)).forEachIndexed { i, color ->
                        val inset = 10f + i * 5f
                        drawArc(color, 180f, 180f, false, Offset(inset, 17f + i * 5f),
                            Size(100 - inset * 2, 96 - i * 10f), style = Stroke(5.5f))
                    }
                }
                MagicPicture.TREE -> {
                    drawRect(Brown, Offset(43f, 48f), Size(14f, 40f))
                    line(50f, 67f, 67f, 51f, Brown, 7f)
                    dot(29f, 41f, 21f, Green); dot(71f, 41f, 21f, Green)
                    dot(50f, 25f, 23f, Green); dot(50f, 46f, 25f, Green)
                    oval(Color(0xFF5EA468), 29f, 22f, 19f, 12f)
                }
                MagicPicture.FLOWER -> {
                    line(50f, 40f, 50f, 91f, Green, 5f)
                    oval(Green, 50f, 68f, 24f, 11f); oval(Green, 25f, 76f, 25f, 11f)
                    repeat(6) { i ->
                        val angle = Math.PI * i / 3
                        dot(50 + 20 * kotlin.math.cos(angle).toFloat(), 36 + 20 * kotlin.math.sin(angle).toFloat(), 13f, Color(0xFFE880A3))
                    }
                    dot(50f, 36f, 13f, Gold)
                }
                MagicPicture.BALL -> {
                    dot(50f, 50f, 35f, Color.White)
                    drawCircle(Ink, 35f, Offset(50f, 50f), style = Stroke(2f))
                    polygon(Ink, 50f, 35f, 65f, 46f, 59f, 63f, 41f, 63f, 35f, 46f)
                    line(50f, 35f, 50f, 16f, Ink, 2f); line(65f, 46f, 83f, 39f, Ink, 2f)
                    line(59f, 63f, 70f, 78f, Ink, 2f); line(41f, 63f, 30f, 78f, Ink, 2f)
                    line(35f, 46f, 17f, 39f, Ink, 2f)
                }
                MagicPicture.BOOK -> {
                    polygon(AprenderColors.Blue, 9f, 22f, 50f, 29f, 91f, 22f, 91f, 80f, 50f, 87f, 9f, 80f)
                    polygon(Color(0xFFFFF8DE), 15f, 18f, 50f, 25f, 50f, 80f, 15f, 73f)
                    polygon(Color.White, 50f, 25f, 85f, 18f, 85f, 73f, 50f, 80f)
                    line(50f, 26f, 50f, 80f, AprenderColors.Blue, 2f)
                    for (y in listOf(38f, 49f, 60f)) {
                        line(23f, y, 42f, y + 4, Brown, 2f); line(58f, y + 4, 77f, y, AprenderColors.Blue, 2f)
                    }
                }
                MagicPicture.CLOCK -> {
                    dot(50f, 50f, 38f, AprenderColors.Blue); dot(50f, 50f, 32f, Color.White)
                    repeat(12) { i ->
                        val angle = Math.PI * i / 6
                        dot(50 + 27 * kotlin.math.cos(angle).toFloat(), 50 + 27 * kotlin.math.sin(angle).toFloat(), 1.5f, Ink)
                    }
                    line(50f, 50f, 50f, 28f, Ink, 3f); line(50f, 50f, 65f, 59f, Ink, 4f)
                    dot(50f, 50f, 3f, Red)
                }
            }
        }
    }
}

@Composable
fun MagicFace(happy: Boolean, modifier: Modifier = Modifier) {
    Canvas(modifier.aspectRatio(1f).clearAndSetSemantics { }) {
        val u = size.minDimension / 100f
        withTransform({ scale(u, u, pivot = Offset.Zero) }) {
            dot(50f, 50f, 35f, Gold)
            eyes(38f, 62f, 42f)
            drawArc(Ink, if (happy) 0f else 180f, 180f, false,
                Offset(33f, if (happy) 43f else 62f), Size(34f, 24f), style = Stroke(3.5f, cap = StrokeCap.Round))
        }
    }
}

private fun DrawScope.dot(x: Float, y: Float, radius: Float, color: Color) = drawCircle(color, radius, Offset(x, y))
private fun DrawScope.oval(color: Color, x: Float, y: Float, w: Float, h: Float) = drawOval(color, Offset(x, y), Size(w, h))
private fun DrawScope.line(x: Float, y: Float, endX: Float, endY: Float, color: Color, width: Float) =
    drawLine(color, Offset(x, y), Offset(endX, endY), width, StrokeCap.Round)
private fun DrawScope.eyes(left: Float, right: Float, y: Float) {
    dot(left, y, 3f, Ink); dot(right, y, 3f, Ink)
}
private fun DrawScope.polygon(color: Color, vararg coordinates: Float) {
    val path = Path().apply {
        moveTo(coordinates[0], coordinates[1])
        for (i in 2 until coordinates.size step 2) lineTo(coordinates[i], coordinates[i + 1])
        close()
    }
    drawPath(path, color)
}
