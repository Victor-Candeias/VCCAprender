package com.vcc.aprender.ui.magic

import android.graphics.Paint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import com.vcc.aprender.domain.magic.MagicPicture
import kotlin.math.cos
import kotlin.math.sin

/**
 * Distinct, original, offline vector artwork for expanded questions.
 * Images remain local and do not require external web assets or loading.
 * Each drawing uses the 100x100 coordinates supplied by MagicArtwork.
 */
internal fun DrawScope.drawExpansionPicture(picture: MagicPicture) {
    val violet = Color(0xFF6559A4)
    val blue = Color(0xFF3998D0)
    val teal = Color(0xFF31978A)
    val green = Color(0xFF489A5C)
    val yellow = Color(0xFFFFCA51)
    val pink = Color(0xFFE98AA8)
    val dark = Color(0xFF283650)
    val paper = Color(0xFFFFF8EC)
    val value = picture.label

    drawRoundRect(Color(0xFFF1F6FC), Offset(3f, 3f), Size(94f, 94f), CornerRadius(12f))
    when (picture) {
        MagicPicture.SPACE_JUPITER -> {
            dot(50f, 46f, 35f, Color(0xFFDEB287))
            for (y in listOf(30f, 41f, 55f, 66f)) line(24f, y, 76f, y, Color(0xFFC18265), 5f)
            oval(Color(0xFFB9664C), 55f, 48f, 16f, 9f)
        }
        MagicPicture.SPACE_TERRA -> {
            dot(50f, 46f, 34f, blue)
            polygon(green, 27f, 20f, 50f, 18f, 60f, 30f, 47f, 42f, 38f, 56f, 23f, 43f)
            polygon(green, 64f, 46f, 76f, 53f, 61f, 73f, 51f, 64f)
            oval(Color.White.copy(alpha = .65f), 23f, 20f, 18f, 6f)
        }
        MagicPicture.SPACE_VENUS -> {
            dot(50f, 46f, 34f, Color(0xFFE9C883))
            line(20f, 36f, 80f, 44f, Color(0xFFB78F5A), 5f)
            line(23f, 52f, 76f, 58f, Color(0xFFF7E0AA), 7f)
        }
        MagicPicture.SPACE_NEPTUNO -> {
            dot(50f, 46f, 34f, Color(0xFF3C60C9))
            line(23f, 41f, 77f, 41f, Color(0xFF80B5EF), 6f)
            line(27f, 57f, 72f, 57f, Color(0xFF244C9D), 7f)
        }
        MagicPicture.SPACE_MERCURIO -> {
            dot(50f, 46f, 29f, Color(0xFFA6A7A4))
            dot(39f, 35f, 8f, Color(0xFF777B7E))
            dot(58f, 51f, 11f, Color(0xFF858987))
            dot(45f, 65f, 5f, Color(0xFFC2C3C0))
        }
        MagicPicture.SPACE_VIA_LACTEA, MagicPicture.SPACE_ANDROMEDA,
        MagicPicture.SPACE_GALAXIA_DO_TRIANGULO -> {
            val tone = when (picture) {
                MagicPicture.SPACE_VIA_LACTEA -> Color(0xFF527ED1)
                MagicPicture.SPACE_ANDROMEDA -> violet
                else -> teal
            }
            // Spiral galaxies have distinct orientations and arms.
            val rotation = when (picture) {
                MagicPicture.SPACE_ANDROMEDA -> 0.5
                MagicPicture.SPACE_GALAXIA_DO_TRIANGULO -> 1.3
                else -> 0.0
            }
            for (arm in 0..2) {
                for (i in 1..26) {
                    val theta = i * .29 + arm * Math.PI * 2 / 3 + rotation
                    val radius = 3 + i * 1.25
                    dot(50f + (cos(theta) * radius).toFloat(),
                        46f + (sin(theta) * radius * .72).toFloat(), 1.5f + i / 22f, tone)
                }
            }
            dot(50f, 46f, 8f, yellow)
        }
        MagicPicture.SPACE_UM_PLANETA -> {
            dot(50f, 46f, 31f, teal)
            oval(Color(0xFF8CCDD1), 35f, 27f, 22f, 12f)
        }
        MagicPicture.SPACE_UMA_LUA -> {
            dot(50f, 46f, 28f, Color(0xFFBEC5CE))
            dot(40f, 36f, 6f, Color(0xFF909CAB))
            dot(60f, 55f, 9f, Color(0xFFA0ACB9))
        }
        MagicPicture.SPACE_UM_METEORO -> {
            polygon(yellow, 7f, 17f, 63f, 45f, 51f, 63f, 20f, 50f)
            line(9f, 33f, 56f, 57f, Orange, 8f)
            dot(68f, 63f, 17f, Color(0xFF8A8A8E))
            dot(62f, 58f, 5f, Color(0xFF676B72))
        }
        MagicPicture.SPACE_7, MagicPicture.SPACE_8, MagicPicture.SPACE_9,
        MagicPicture.NUMBER_2, MagicPicture.NUMBER_3, MagicPicture.NUMBER_4,
        MagicPicture.NUMBER_5, MagicPicture.NUMBER_6, MagicPicture.NUMBER_10,
        MagicPicture.NUMBER_11, MagicPicture.NUMBER_12, MagicPicture.NUMBER_13,
        MagicPicture.NUMBER_14, MagicPicture.NUMBER_15, MagicPicture.NUMBER_16,
        MagicPicture.NUMBER_18, MagicPicture.NUMBER_19, MagicPicture.NUMBER_20 -> {
            dot(50f, 48f, 35f, violet)
            tileText(value, 50f, 61f, if (value.length == 1) 39f else 34f, android.graphics.Color.WHITE)
        }
        MagicPicture.FACT_TERCA_FEIRA, MagicPicture.FACT_DOMINGO, MagicPicture.FACT_SEXTA_FEIRA -> {
            drawRoundRect(blue, Offset(18f, 19f), Size(64f, 62f), CornerRadius(7f))
            drawRect(paper, Offset(23f, 34f), Size(54f, 41f))
            line(32f, 11f, 32f, 29f, dark, 5f); line(68f, 11f, 68f, 29f, dark, 5f)
            val day = when (picture) {
                MagicPicture.FACT_TERCA_FEIRA -> "TER"
                MagicPicture.FACT_DOMINGO -> "DOM"
                else -> "SEX"
            }
            tileText(day, 50f, 60f, 19f, android.graphics.Color.rgb(35, 57, 82))
        }
        MagicPicture.FACT_VISAO -> {
            oval(Color.White, 9f, 27f, 82f, 40f)
            drawOval(dark, Offset(9f, 27f), Size(82f, 40f), style = Stroke(3f))
            dot(50f, 47f, 19f, blue); dot(50f, 47f, 9f, dark)
            dot(44f, 39f, 5f, Color.White)
        }
        MagicPicture.FACT_AUDICAO -> {
            drawArc(pink, 180f, 270f, false, Offset(25f, 17f), Size(48f, 57f), style = Stroke(12f))
            drawArc(dark, 180f, 205f, false, Offset(37f, 31f), Size(24f, 32f), style = Stroke(4f))
            dot(55f, 72f, 7f, pink)
        }
        MagicPicture.FACT_PALADAR -> {
            oval(pink, 20f, 28f, 60f, 46f)
            oval(Color.White, 23f, 30f, 54f, 15f)
            oval(Color(0xFFE45775), 32f, 52f, 37f, 26f)
            line(51f, 56f, 51f, 68f, dark, 2f)
        }
        MagicPicture.FACT_VIOLINO -> {
            oval(Color(0xFFC98547), 30f, 36f, 40f, 41f)
            oval(Color(0xFFC98547), 35f, 23f, 30f, 26f)
            line(50f, 7f, 50f, 86f, dark, 6f)
            for(x in listOf(45f, 49f, 53f, 57f)) line(x, 9f, x, 88f, paper, 1f)
            line(14f, 62f, 86f, 28f, dark, 3f)
        }
        MagicPicture.FACT_TAMBOR -> {
            oval(paper, 20f, 28f, 60f, 22f)
            polygon(Color(0xFFD9585B), 20f, 38f, 80f, 38f, 72f, 76f, 28f, 76f)
            oval(Color(0xFFD9585B), 28f, 64f, 44f, 16f)
            line(18f, 19f, 56f, 44f, dark, 4f); line(80f, 17f, 44f, 44f, dark, 4f)
        }
        MagicPicture.FACT_PIANO -> {
            drawRoundRect(dark, Offset(13f, 27f), Size(74f, 47f), CornerRadius(5f))
            for (i in 0..6) drawRect(Color.White, Offset(17f + i * 10f, 34f), Size(9f, 32f))
            for(i in listOf(0, 1, 3, 4, 5)) drawRect(dark, Offset(24f + i * 10f, 34f), Size(6f, 18f))
        }
        MagicPicture.FACT_PINTAINHO -> {
            dot(50f, 54f, 27f, yellow)
            dot(50f, 26f, 20f, yellow)
            polygon(Orange, 46f, 30f, 66f, 36f, 46f, 42f)
            dot(42f, 28f, 3f, dark)
            line(39f, 77f, 36f, 88f, Orange, 4f)
            line(61f, 77f, 64f, 88f, Orange, 4f)
        }
        MagicPicture.FACT_MADEIRA -> {
            drawRoundRect(Color(0xFFC99156), Offset(14f, 29f), Size(72f, 40f), CornerRadius(8f))
            for (y in listOf(36f, 48f, 60f)) line(19f, y, 81f, y, Color(0xFF986039), 2f)
            oval(Color(0xFF9F6B3D), 43f, 39f, 13f, 18f)
        }
        MagicPicture.FACT_FERRO -> {
            drawRect(Color(0xFF8C9FAB), Offset(24f, 22f), Size(52f, 54f))
            drawRect(Color(0xFFBAC8D1), Offset(30f, 29f), Size(40f, 40f))
            tileText("Fe", 50f, 58f, 26f, android.graphics.Color.rgb(35, 57, 82))
        }
        MagicPicture.FACT_PLASTICO -> {
            polygon(blue, 41f, 18f, 59f, 18f, 59f, 29f, 67f, 35f, 67f, 81f, 33f, 81f, 33f, 35f, 41f, 29f)
            drawRect(paper, Offset(38f, 48f), Size(24f, 18f))
            drawRect(dark, Offset(41f, 12f), Size(18f, 7f))
        }
        MagicPicture.FACT_CACHECOL -> {
            oval(Color(0xFFD65475), 17f, 18f, 65f, 31f)
            oval(paper, 33f, 22f, 34f, 18f)
            drawRect(Color(0xFFD65475), Offset(57f, 38f), Size(18f, 48f))
            for (x in listOf(60f, 66f, 72f)) line(x, 84f, x, 94f, Color(0xFFD65475), 2f)
        }
        MagicPicture.FACT_OCULOS_DE_SOL -> {
            oval(dark, 8f, 37f, 37f, 30f); oval(dark, 55f, 37f, 37f, 30f)
            line(43f, 39f, 57f, 39f, dark, 5f)
            line(8f, 43f, 2f, 36f, dark, 5f)
            line(92f, 43f, 98f, 36f, dark, 5f)
            line(15f, 43f, 28f, 43f, blue, 3f)
        }
        MagicPicture.FACT_CAPACETE -> {
            drawArc(yellow, 180f, 180f, true, Offset(15f, 15f), Size(70f, 69f))
            line(14f, 54f, 86f, 54f, dark, 5f)
            line(70f, 55f, 70f, 79f, dark, 4f)
            drawRoundRect(dark, Offset(59f, 75f), Size(22f, 7f), CornerRadius(2f))
        }
        MagicPicture.FACT_INVERNO -> {
            repeat(6) { i ->
                val a=i*Math.PI/3
                line(50f, 46f, 50f+(cos(a)*31).toFloat(), 46f+(sin(a)*31).toFloat(), blue, 5f)
            }
            dot(50f, 46f, 7f, blue)
        }
        MagicPicture.FACT_VERAO -> {
            repeat(10) { i ->
                val a=i*Math.PI/5
                line(50f+(cos(a)*29).toFloat(), 46f+(sin(a)*29).toFloat(),
                    50f+(cos(a)*42).toFloat(), 46f+(sin(a)*42).toFloat(), Orange, 5f)
            }
            dot(50f, 46f, 23f, yellow)
        }
        MagicPicture.FACT_OUTONO -> {
            polygon(Color(0xFFE98A40), 50f, 10f, 61f, 28f, 80f, 18f, 71f, 43f,
                89f, 52f, 61f, 59f, 55f, 83f, 43f, 59f, 13f, 54f, 29f, 43f,
                17f, 20f, 40f, 28f)
            line(50f, 50f, 58f, 91f, Color(0xFF91613E), 4f)
        }
        MagicPicture.FACT_AFRICA -> {
            polygon(green, 36f, 19f, 67f, 16f, 80f, 39f, 65f, 57f, 55f, 84f, 40f, 72f, 33f, 47f, 19f, 35f)
        }
        MagicPicture.FACT_ASIA -> {
            polygon(teal, 14f, 25f, 31f, 16f, 48f, 27f, 65f, 14f, 89f, 28f,
                81f, 53f, 63f, 56f, 57f, 81f, 43f, 61f, 26f, 66f, 18f, 49f)
        }
        MagicPicture.FACT_EUROPA -> {
            polygon(blue, 21f, 24f, 53f, 13f, 74f, 28f, 86f, 47f, 66f, 52f,
                57f, 75f, 41f, 58f, 27f, 66f, 19f, 49f)
            dot(37f, 69f, 5f, blue)
        }
        MagicPicture.FACT_LUZ_DO_SOL_E_GOTAS_DE_AGUA -> {
            dot(31f, 29f, 18f, yellow)
            for (i in 0..2) {
                val x=52f+i*15f
                polygon(blue, x, 40f, x+7f, 56f, x, 70f, x-7f, 56f)
            }
            for(i in 0..4) {
                drawArc(listOf(Red, Orange, yellow, green, blue)[i],180f,180f,false,
                    Offset(20f+i*5f, 57f+i*5f), Size(60f-i*10f, 36f-i*7f),style=Stroke(3f))
            }
        }
        MagicPicture.FACT_VENTO_E_NEVE -> {
            for (y in listOf(27f, 47f, 67f)) {
                line(12f, y, 85f, y, blue, 4f)
                dot(83f, y, 4f, blue)
            }
            for(x in listOf(35f, 62f)) {
                line(x, 12f, x, 83f, violet, 2f)
                line(x-10, 40f, x+10, 54f, violet, 2f)
            }
        }
        MagicPicture.FACT_TROVAO_E_GELO -> {
            polygon(yellow, 55f, 12f, 30f, 48f, 49f, 47f, 38f, 85f, 75f, 39f, 56f, 40f)
            dot(24f, 29f, 7f, blue); dot(80f, 68f, 8f, blue)
        }
        MagicPicture.FACT_LAPIS -> {
            polygon(yellow, 35f, 17f, 66f, 17f, 66f, 72f, 50f, 91f, 35f, 72f)
            polygon(Color(0xFFC7986E), 35f, 72f, 66f, 72f, 50f, 91f)
            polygon(dark, 45f, 84f, 55f, 84f, 50f, 91f)
            drawRect(pink, Offset(35f, 10f), Size(31f, 10f))
        }
        MagicPicture.FACT_BORRACHA -> {
            drawRoundRect(pink, Offset(18f, 27f), Size(64f, 44f), CornerRadius(8f))
            drawRect(Color.White, Offset(47f, 29f), Size(9f, 40f))
        }
        MagicPicture.FACT_TESOURA -> {
            oval(pink, 15f, 50f, 25f, 25f); oval(pink, 40f, 50f, 25f, 25f)
            dot(28f, 62f, 7f, Color(0xFFF1F6FC)); dot(53f, 62f, 7f, Color(0xFFF1F6FC))
            line(39f, 50f, 80f, 16f, dark, 6f)
            line(40f, 47f, 86f, 71f, dark, 6f)
            dot(43f, 48f, 5f, yellow)
        }
        MagicPicture.FACT_GALINHA -> {
            oval(Color.White, 25f, 33f, 53f, 43f)
            dot(37f, 32f, 20f, Color.White)
            for (x in listOf(26f, 36f, 46f)) dot(x, 12f, 8f, Red)
            polygon(Orange, 18f, 33f, 4f, 39f, 18f, 44f)
            dot(31f, 30f, 3f, dark)
            line(44f, 74f, 40f, 89f, Orange, 3f)
            line(64f, 74f, 68f, 89f, Orange, 3f)
        }
        MagicPicture.FACT_RA -> {
            dot(50f, 54f, 29f, green)
            dot(34f, 29f, 13f, green); dot(66f, 29f, 13f, green)
            dot(34f, 27f, 5f, Color.White); dot(66f, 27f, 5f, Color.White)
            dot(34f, 27f, 2f, dark); dot(66f, 27f, 2f, dark)
            drawArc(dark, 0f, 180f, false, Offset(35f, 47f), Size(30f, 20f), style=Stroke(2f))
            oval(green, 14f, 74f, 24f, 10f); oval(green, 62f, 74f, 24f, 10f)
        }
        else -> {
            // Visible, accessible fallback: every unrecognised option still has a name.
            dot(50f, 42f, 25f, violet)
            tileText("?", 50f, 54f, 31f, android.graphics.Color.WHITE)
        }
    }
    // Compact option label complements, rather than substitutes, the artwork.
    val lines = splitTileLabel(value)
    val font = if (lines.size > 2) 9f else if (value.length > 15) 10f else 11.5f
    lines.take(3).forEachIndexed { index, word ->
        tileText(word, 50f, 87f + index * 10f - (lines.size-1)*5f, font,
            android.graphics.Color.rgb(30,49,80))
    }
}

private fun splitTileLabel(value: String): List<String> {
    if (value.length <= 15) return listOf(value)
    val result = mutableListOf<String>()
    var line = ""
    for (word in value.split(" ")) {
        val next = if (line.isBlank()) word else "$line $word"
        if (next.length > 15 && line.isNotBlank()) { result.add(line); line=word } else line=next
    }
    if (line.isNotBlank()) result.add(line)
    return result
}

private fun DrawScope.tileText(value: String, x: Float, y: Float, size: Float, color: Int) {
    val paint=Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color=color
        textSize=size
        typeface=android.graphics.Typeface.create(
            android.graphics.Typeface.DEFAULT,android.graphics.Typeface.BOLD)
        textAlign=Paint.Align.CENTER
    }
    drawContext.canvas.nativeCanvas.drawText(value,x,y,paint)
}
