package com.vcc.aprender.ui.magic

import android.graphics.Paint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import com.vcc.aprender.domain.magic.MagicPicture

/**
 * Fully offline, original illustrative tiles for VA-033.
 * These deliberately do NOT reproduce commercial car logos or football crests:
 * the brand/club name is shown inside a neutral car/shield pictogram.
 * Called inside the existing 100 x 100 vector drawing transform.
 */
internal fun DrawScope.drawExpansionPicture(picture: MagicPicture) {
    val value = picture.label
    val kind = picture.name.substringBefore('_')
    val tint = when (kind) {
        "SPACE" -> Color(0xFF51619D)
        "AUTO" -> Color(0xFF2286A5)
        "CLUB" -> Color(0xFF328C65)
        "NUMBER" -> Color(0xFF9969B1)
        else -> Color(0xFFE8A542)
    }
    drawRoundRect(
        color = tint.copy(alpha = 0.19f),
        topLeft = Offset(4f, 4f),
        size = Size(92f, 92f),
        cornerRadius = CornerRadius(13f)
    )
    when {
        value.all { it.isDigit() } -> {
            dot(50f, 42f, 32f, tint)
            tileText(value, 50f, 51f, 37f, android.graphics.Color.WHITE)
            return
        }
        kind == "SPACE" -> {
            dot(50f, 36f, 24f, tint)
            dot(42f, 29f, 6f, Color.White.copy(alpha = 0.32f))
            if (value == "Saturno" || value == "Urano") {
                drawOval(
                    tint.copy(alpha = 0.7f), Offset(13f, 26f), Size(74f, 22f),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
                )
            }
        }
        kind == "AUTO" -> {
            // Simple original car silhouette, not a brand mark.
            drawRoundRect(tint, Offset(15f, 32f), Size(70f, 19f), CornerRadius(8f))
            drawRoundRect(tint.copy(alpha = 0.8f), Offset(29f, 23f), Size(43f, 18f), CornerRadius(7f))
            dot(30f, 54f, 7f, Color(0xFF25364F))
            dot(72f, 54f, 7f, Color(0xFF25364F))
        }
        kind == "CLUB" -> {
            // Generic football shield. Official emblems and trademarks are not bundled.
            polygon(tint, 31f, 14f, 69f, 14f, 72f, 41f, 50f, 61f, 28f, 41f)
            drawCircle(Color.White, radius = 10f, center = Offset(50f, 33f))
            dot(50f, 33f, 4f, tint)
        }
        kind == "NUMBER" -> {
            dot(50f, 34f, 22f, tint)
            tileText(value, 50f, 43f, 24f, android.graphics.Color.WHITE)
        }
        else -> {
            // General knowledge uses a neutral illustrated discovery badge.
            drawRoundRect(tint, Offset(28f, 13f), Size(44f, 42f), CornerRadius(8f))
            drawRect(Color.White, Offset(38f, 21f), Size(24f, 4f))
            drawRect(Color.White, Offset(38f, 30f), Size(19f, 4f))
            drawRect(Color.White, Offset(38f, 39f), Size(22f, 4f))
        }
    }
    val lines = splitTileLabel(value)
    val size = when {
        lines.size >= 3 -> 10f
        value.length >= 18 -> 10.5f
        else -> 12f
    }
    val bottom = if (lines.size == 1) 81f else if (lines.size == 2) 74f else 68f
    for ((i, line) in lines.withIndex()) {
        tileText(line, 50f, bottom + i * (size + 2f), size, android.graphics.Color.rgb(30, 49, 80))
    }
}

private fun splitTileLabel(label: String): List<String> {
    if (label.length <= 15) return listOf(label)
    val words = label.split(" ")
    val lines = mutableListOf<String>()
    var current = ""
    for (word in words) {
        val next = if (current.isEmpty()) word else "$current $word"
        if (next.length > 15 && current.isNotEmpty()) {
            lines.add(current)
            current = word
        } else {
            current = next
        }
    }
    if (current.isNotEmpty()) lines.add(current)
    return if (lines.size <= 3) lines else listOf(lines[0], lines[1], lines.drop(2).joinToString(" "))
}

private fun DrawScope.tileText(value: String, x: Float, y: Float, fontSize: Float, color: Int) {
    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color = color
        textSize = fontSize
        typeface = android.graphics.Typeface.create(
            android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD
        )
        textAlign = Paint.Align.CENTER
    }
    // The incoming Compose canvas is already scaled to the 100x100 local coordinates.
    drawContext.canvas.nativeCanvas.drawText(value, x, y, textPaint)
}
