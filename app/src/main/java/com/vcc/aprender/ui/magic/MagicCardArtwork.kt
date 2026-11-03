package com.vcc.aprender.ui.magic

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import com.vcc.aprender.domain.magic.MagicPicture
import com.vcc.aprender.ui.theme.AprenderColors
import kotlin.math.cos
import kotlin.math.sin

private val Purple = Color(0xFF8865B8)
private val Sky = Color(0xFF8DD8F0)
private val Pink = Color(0xFFF29BB4)
private val Gray = Color(0xFFA7AFB8)
private val DarkGray = Color(0xFF5B6470)
private val Cream = Color(0xFFFFE8C2)
private val Lime = Color(0xFF8CC63F)
private val Tan = Color(0xFFDDA86B)
private val Skin = Color(0xFFF2C29B)
private val Night = Color(0xFF34364F)
private val Wing = Color(0x998DD8F0)

/** Original simplified vectors for the trivia cards (questions 21 to 51), drawn in a 100×100 space. */
internal fun DrawScope.drawCardPicture(picture: MagicPicture) {
    when (picture) {
        MagicPicture.TIGER -> bigCat(Orange) { x, y -> line(x, y - 6, x - 3, y + 6, Ink, 3f) }
        MagicPicture.LEOPARD -> bigCat(Gold) { x, y -> dot(x, y, 2.6f, Brown); dot(x - 4, y + 7, 2f, Brown) }
        MagicPicture.HORSE -> {
            line(30f, 60f, 28f, 88f, Tan, 6f); line(40f, 62f, 40f, 88f, Tan, 6f)
            line(62f, 62f, 62f, 88f, Tan, 6f); line(70f, 60f, 72f, 88f, Tan, 6f)
            for (x in listOf(28f, 40f, 62f, 72f)) dot(x, 89f, 3.5f, Ink)
            line(22f, 48f, 12f, 74f, Brown, 6f)
            oval(Tan, 20f, 40f, 56f, 26f)
            polygon(Tan, 62f, 50f, 70f, 20f, 82f, 22f, 78f, 54f)
            polygon(Tan, 74f, 20f, 76f, 8f, 81f, 18f)
            oval(Tan, 72f, 16f, 24f, 14f)
            oval(Brown, 86f, 20f, 10f, 9f)
            line(68f, 20f, 62f, 48f, Brown, 5f)
            dot(80f, 21f, 2f, Ink)
        }
        MagicPicture.FOX -> {
            oval(Orange, 56f, 66f, 36f, 16f); dot(89f, 74f, 6f, Color.White)
            oval(Orange, 28f, 44f, 38f, 42f)
            oval(Color.White, 38f, 50f, 18f, 24f)
            polygon(Orange, 32f, 26f, 32f, 6f, 46f, 20f)
            polygon(Orange, 52f, 20f, 64f, 6f, 64f, 26f)
            dot(48f, 32f, 17f, Orange)
            polygon(Color.White, 38f, 36f, 58f, 36f, 48f, 50f)
            dot(48f, 49f, 2.8f, Ink)
            eyes(41f, 55f, 30f)
        }
        MagicPicture.KANGAROO -> {
            line(38f, 78f, 10f, 92f, Tan, 7f)
            oval(Tan, 30f, 40f, 32f, 44f)
            oval(Cream, 40f, 58f, 18f, 18f)
            line(42f, 84f, 66f, 88f, Tan, 6f)
            line(56f, 50f, 66f, 58f, Tan, 4f)
            polygon(Tan, 48f, 22f, 50f, 4f, 56f, 20f)
            oval(Tan, 44f, 16f, 24f, 18f)
            oval(Tan, 60f, 22f, 14f, 10f)
            dot(56f, 23f, 2f, Ink); dot(73f, 26f, 2f, Ink)
        }
        MagicPicture.TURTLE -> {
            for (x in listOf(24f, 62f)) oval(Lime, x, 52f, 14f, 14f)
            dot(82f, 50f, 9f, Lime)
            dot(85f, 47f, 2f, Ink)
            drawArc(Brown, 180f, 180f, true, Offset(18f, 28f), Size(62f, 52f))
            drawArc(Color(0xFFB87A50), 180f, 180f, true, Offset(30f, 38f), Size(38f, 32f))
            drawRect(Lime, Offset(18f, 53f), Size(62f, 5f))
        }
        MagicPicture.CROCODILE -> {
            line(28f, 60f, 24f, 72f, Green, 6f); line(56f, 60f, 60f, 72f, Green, 6f)
            polygon(Green, 4f, 50f, 20f, 44f, 20f, 60f)
            oval(Green, 14f, 42f, 60f, 20f)
            polygon(Green, 66f, 44f, 96f, 50f, 96f, 56f, 66f, 58f)
            for (x in listOf(74f, 81f, 88f)) polygon(Color.White, x, 50f, x + 3, 50f, x + 1.5f, 54f)
            for (x in listOf(24f, 34f, 44f, 54f)) polygon(Color(0xFF2A6B45), x, 43f, x + 5, 36f, x + 10, 43f)
            dot(68f, 42f, 4f, Color.White); dot(69f, 42f, 2f, Ink)
        }
        MagicPicture.HIPPO -> {
            val hippo = Color(0xFF7E8DC7)
            for (x in listOf(22f, 34f, 54f, 66f)) drawRect(hippo, Offset(x, 66f), Size(9f, 18f))
            oval(hippo, 12f, 40f, 66f, 40f)
            dot(66f, 28f, 5f, hippo); dot(86f, 28f, 5f, hippo)
            oval(hippo, 58f, 28f, 36f, 30f)
            oval(Color(0xFF9DAAE0), 62f, 42f, 32f, 20f)
            dot(70f, 50f, 2.4f, Ink); dot(86f, 50f, 2.4f, Ink)
            eyes(70f, 84f, 36f)
        }
        MagicPicture.OCTOPUS -> {
            for (i in 0 until 6) {
                val x = 32f + i * 7.2f
                val dx = (i - 2.5f) * 10f
                curve(Purple, 6f) { moveTo(x, 44f); quadraticTo(x + dx * 0.4f, 70f, x + dx, 84f) }
            }
            oval(Purple, 28f, 10f, 44f, 40f)
            eyes(42f, 58f, 30f)
            drawArc(Ink, 20f, 140f, false, Offset(44f, 32f), Size(12f, 8f), style = Stroke(2f, cap = StrokeCap.Round))
        }
        MagicPicture.DOLPHIN -> rotate(-12f, Offset(50f, 50f)) {
            val dolphin = Color(0xFF5E9FD0)
            polygon(dolphin, 46f, 38f, 54f, 22f, 62f, 40f)
            polygon(dolphin, 80f, 46f, 96f, 32f, 94f, 58f)
            oval(dolphin, 14f, 38f, 70f, 22f)
            oval(dolphin, 2f, 47f, 20f, 7f)
            oval(Color(0xFFB7DCF2), 20f, 50f, 50f, 9f)
            polygon(dolphin, 38f, 56f, 32f, 70f, 48f, 58f)
            dot(24f, 45f, 2f, Ink)
        }
        MagicPicture.SNAKE -> {
            curve(Lime, 11f) {
                moveTo(22f, 86f); cubicTo(70f, 96f, 86f, 70f, 54f, 62f)
                cubicTo(24f, 54f, 30f, 32f, 58f, 30f)
            }
            for ((x, y) in listOf(40f to 88f, 66f to 80f, 52f to 62f, 36f to 50f)) dot(x, y, 3f, Gold)
            oval(Lime, 54f, 20f, 22f, 16f)
            dot(66f, 26f, 2f, Ink)
            line(76f, 28f, 86f, 26f, Red, 2f)
        }
        MagicPicture.FROG -> {
            oval(Lime, 14f, 76f, 24f, 10f); oval(Lime, 62f, 76f, 24f, 10f)
            oval(Lime, 20f, 40f, 60f, 44f)
            oval(Color(0xFFE6F0A0), 34f, 56f, 32f, 24f)
            dot(36f, 38f, 11f, Lime); dot(64f, 38f, 11f, Lime)
            dot(36f, 37f, 6f, Color.White); dot(64f, 37f, 6f, Color.White)
            dot(36f, 37f, 3f, Ink); dot(64f, 37f, 3f, Ink)
            drawArc(Ink, 20f, 140f, false, Offset(36f, 44f), Size(28f, 12f), style = Stroke(2.5f, cap = StrokeCap.Round))
        }
        MagicPicture.CROW -> {
            val crow = Color(0xFF2E2E3A)
            line(44f, 70f, 40f, 90f, Ink, 2.5f); line(54f, 70f, 56f, 90f, Ink, 2.5f)
            line(34f, 90f, 46f, 90f, Ink, 2.5f); line(50f, 90f, 62f, 90f, Ink, 2.5f)
            polygon(crow, 30f, 56f, 6f, 76f, 18f, 80f, 40f, 64f)
            oval(crow, 24f, 36f, 50f, 36f)
            dot(66f, 30f, 13f, crow)
            polygon(DarkGray, 76f, 26f, 94f, 33f, 76f, 37f)
            dot(70f, 27f, 2.2f, Color.White)
        }
        MagicPicture.PENGUIN -> {
            oval(Orange, 32f, 84f, 14f, 7f); oval(Orange, 54f, 84f, 14f, 7f)
            oval(Night, 16f, 40f, 14f, 30f); oval(Night, 70f, 40f, 14f, 30f)
            oval(Night, 26f, 10f, 48f, 78f)
            oval(Color.White, 34f, 30f, 32f, 54f)
            eyes(42f, 58f, 30f)
            polygon(Orange, 46f, 35f, 54f, 35f, 50f, 41f)
        }
        MagicPicture.PARROT -> {
            polygon(Red, 36f, 68f, 26f, 92f, 40f, 92f, 46f, 70f)
            line(46f, 72f, 44f, 86f, DarkGray, 2.5f); line(54f, 72f, 56f, 86f, DarkGray, 2.5f)
            oval(Green, 30f, 30f, 34f, 46f)
            oval(Color(0xFF2A6B45), 32f, 42f, 20f, 28f)
            dot(56f, 28f, 15f, Green)
            dot(58f, 25f, 6f, Color.White); dot(59f, 25f, 2.5f, Ink)
            polygon(Gold, 66f, 22f, 82f, 30f, 72f, 44f, 66f, 36f)
        }
        MagicPicture.KOALA -> {
            line(26f, 6f, 42f, 96f, Brown, 7f)
            val koala = Color(0xFFA9B1BA)
            oval(koala, 32f, 46f, 40f, 40f)
            dot(36f, 24f, 11f, koala); dot(68f, 24f, 11f, koala)
            dot(36f, 24f, 6f, Pink); dot(68f, 24f, 6f, Pink)
            dot(52f, 36f, 19f, koala)
            oval(Ink, 47f, 34f, 10f, 13f)
            eyes(44f, 60f, 32f)
        }
        MagicPicture.ORANGUTAN -> {
            val rust = Color(0xFFC0532E)
            line(30f, 54f, 18f, 86f, rust, 8f); line(70f, 54f, 82f, 86f, rust, 8f)
            oval(rust, 26f, 46f, 48f, 42f)
            dot(50f, 32f, 18f, rust)
            oval(Color(0xFFB07A55), 38f, 22f, 24f, 24f)
            eyes(45f, 55f, 30f)
            dot(48f, 37f, 1.5f, Ink); dot(52f, 37f, 1.5f, Ink)
        }
        MagicPicture.MANDRILL -> {
            val fur = Color(0xFF5A4636)
            oval(fur, 24f, 48f, 52f, 40f)
            dot(50f, 32f, 18f, fur)
            oval(Cream, 40f, 40f, 20f, 12f)
            oval(AprenderColors.Blue, 36f, 24f, 10f, 18f); oval(AprenderColors.Blue, 54f, 24f, 10f, 18f)
            oval(Red, 46f, 20f, 8f, 24f)
            dot(42f, 22f, 2.5f, Color.White); dot(58f, 22f, 2.5f, Color.White)
        }
        MagicPicture.RHINO -> {
            val rhino = Color(0xFF8E96C9)
            for (x in listOf(18f, 30f, 48f, 60f)) drawRect(rhino, Offset(x, 62f), Size(9f, 20f))
            oval(rhino, 10f, 36f, 62f, 34f)
            oval(rhino, 58f, 36f, 32f, 26f)
            polygon(rhino, 66f, 38f, 64f, 26f, 72f, 36f)
            polygon(Cream, 80f, 42f, 94f, 22f, 88f, 46f)
            polygon(Cream, 72f, 40f, 76f, 30f, 78f, 40f)
            dot(70f, 46f, 2.2f, Ink)
        }
        MagicPicture.DEER -> {
            for (x in listOf(28f, 38f, 56f, 64f)) line(x, 62f, x, 92f, Brown, 5f)
            oval(Brown, 20f, 44f, 52f, 24f)
            for ((x, y) in listOf(32f to 50f, 44f to 52f, 56f to 50f)) dot(x, y, 2.5f, Cream)
            polygon(Brown, 58f, 50f, 66f, 22f, 76f, 24f, 70f, 54f)
            oval(Brown, 62f, 16f, 24f, 16f)
            val antler = Color(0xFFC8A57A)
            line(68f, 18f, 60f, 4f, antler, 3f); line(63f, 9f, 56f, 8f, antler, 3f)
            line(78f, 18f, 86f, 4f, antler, 3f); line(83f, 9f, 90f, 8f, antler, 3f)
            dot(78f, 22f, 2f, Ink); dot(86f, 26f, 2.5f, Ink)
        }
        MagicPicture.COW -> {
            val cow = Color(0xFFF7E7C8)
            for (x in listOf(18f, 30f, 48f, 58f)) drawRect(cow, Offset(x, 64f), Size(8f, 20f))
            oval(cow, 12f, 40f, 60f, 32f)
            dot(30f, 50f, 7f, Ink); dot(52f, 60f, 6f, Ink)
            line(66f, 30f, 60f, 20f, Brown, 4f); line(86f, 30f, 92f, 20f, Brown, 4f)
            oval(cow, 62f, 24f, 28f, 32f)
            dot(70f, 30f, 5f, Ink)
            oval(Pink, 62f, 44f, 28f, 16f)
            dot(70f, 52f, 2f, Ink); dot(82f, 52f, 2f, Ink)
            eyes(70f, 82f, 38f)
        }
        MagicPicture.SEAHORSE -> {
            drawArc(Orange, -90f, 300f, false, Offset(36f, 62f), Size(22f, 24f), style = Stroke(7f, cap = StrokeCap.Round))
            oval(Gold, 26f, 38f, 12f, 18f)
            oval(Orange, 36f, 26f, 22f, 44f)
            oval(Orange, 40f, 12f, 24f, 18f)
            line(60f, 22f, 76f, 28f, Orange, 6f)
            dot(54f, 18f, 2.4f, Ink)
        }
        MagicPicture.SLOTH -> {
            line(6f, 18f, 94f, 18f, Brown, 5f)
            oval(Green, 12f, 8f, 14f, 8f); oval(Green, 74f, 10f, 14f, 8f)
            val fur = Color(0xFFB59A7C)
            line(36f, 20f, 40f, 40f, fur, 8f); line(64f, 20f, 60f, 40f, fur, 8f)
            oval(fur, 30f, 30f, 40f, 54f)
            oval(Cream, 37f, 40f, 26f, 20f)
            oval(DarkGray, 40f, 45f, 9f, 6f); oval(DarkGray, 51f, 45f, 9f, 6f)
            dot(44f, 48f, 1.6f, Color.White); dot(56f, 48f, 1.6f, Color.White)
            dot(50f, 54f, 2f, Ink)
        }
        MagicPicture.GIRAFFE -> {
            for (x in listOf(22f, 30f, 48f, 56f)) line(x, 64f, x, 94f, Gold, 5f)
            line(16f, 58f, 10f, 72f, Brown, 2f)
            oval(Gold, 16f, 50f, 46f, 22f)
            polygon(Gold, 46f, 56f, 58f, 56f, 78f, 16f, 70f, 12f)
            oval(Gold, 66f, 6f, 24f, 12f)
            line(72f, 8f, 70f, 2f, Brown, 2.5f); line(78f, 8f, 78f, 2f, Brown, 2.5f)
            for ((x, y) in listOf(26f to 58f, 38f to 56f, 50f to 62f, 32f to 66f, 60f to 44f, 68f to 30f)) dot(x, y, 3f, Brown)
            dot(80f, 11f, 1.8f, Ink)
        }
        MagicPicture.KIWI_BIRD -> {
            line(46f, 74f, 44f, 90f, Ink, 2.5f); line(58f, 74f, 60f, 90f, Ink, 2.5f)
            oval(Brown, 26f, 36f, 52f, 42f)
            curve(DarkGray, 3f) { moveTo(30f, 48f); quadraticTo(14f, 52f, 8f, 70f) }
            dot(34f, 46f, 2f, Ink)
        }
        MagicPicture.OSTRICH -> {
            line(44f, 64f, 40f, 94f, Pink, 4f); line(56f, 64f, 60f, 94f, Pink, 4f)
            oval(Cream, 16f, 44f, 20f, 18f)
            oval(Ink, 28f, 42f, 46f, 26f)
            line(66f, 48f, 62f, 14f, Pink, 5f)
            dot(64f, 12f, 6f, Pink)
            polygon(Orange, 68f, 10f, 78f, 13f, 68f, 16f)
            dot(64f, 10f, 1.6f, Ink)
        }
        MagicPicture.EEL -> {
            curve(DarkGray, 9f) {
                moveTo(12f, 82f); cubicTo(40f, 96f, 70f, 74f, 50f, 54f)
                cubicTo(30f, 34f, 50f, 14f, 80f, 20f)
            }
            dot(84f, 21f, 6f, DarkGray)
            dot(86f, 19f, 1.8f, Color.White)
        }
        MagicPicture.WHALE -> {
            val whale = Color(0xFF5465B8)
            polygon(whale, 76f, 58f, 96f, 40f, 94f, 72f)
            oval(whale, 8f, 40f, 74f, 40f)
            oval(Color(0xFF8E9BE0), 16f, 62f, 54f, 14f)
            dot(24f, 54f, 2.5f, Ink)
            line(40f, 36f, 36f, 22f, Sky, 3f); line(40f, 36f, 44f, 22f, Sky, 3f)
        }
        MagicPicture.HUMMINGBIRD -> {
            polygon(Pink, 40f, 44f, 26f, 10f, 56f, 40f)
            polygon(Pink, 30f, 52f, 10f, 68f, 26f, 72f)
            rotate(-15f, Offset(48f, 50f)) { oval(Gold, 28f, 42f, 38f, 18f) }
            dot(64f, 40f, 9f, Gold)
            line(72f, 41f, 94f, 46f, Ink, 2f)
            dot(66f, 38f, 1.8f, Ink)
        }
        MagicPicture.FLAMINGO -> {
            line(50f, 56f, 50f, 96f, Pink, 3f)
            line(50f, 74f, 40f, 66f, Pink, 3f); line(40f, 66f, 44f, 82f, Pink, 3f)
            oval(Pink, 28f, 40f, 42f, 22f)
            curve(Pink, 5f) { moveTo(64f, 46f); cubicTo(80f, 36f, 52f, 26f, 60f, 10f) }
            dot(62f, 10f, 6f, Pink)
            polygon(Ink, 66f, 8f, 72f, 14f, 66f, 16f)
            dot(61f, 8f, 1.6f, Ink)
        }
        MagicPicture.OWL -> {
            line(14f, 86f, 86f, 86f, Brown, 4f)
            polygon(Brown, 28f, 30f, 30f, 12f, 42f, 24f)
            polygon(Brown, 58f, 24f, 70f, 12f, 72f, 30f)
            oval(Brown, 26f, 20f, 48f, 64f)
            oval(Cream, 36f, 48f, 28f, 32f)
            dot(40f, 38f, 10f, Color.White); dot(60f, 38f, 10f, Color.White)
            dot(40f, 38f, 4.5f, Ink); dot(60f, 38f, 4.5f, Ink)
            polygon(Orange, 46f, 46f, 54f, 46f, 50f, 54f)
            oval(Orange, 38f, 82f, 8f, 6f); oval(Orange, 54f, 82f, 8f, 6f)
        }
        MagicPicture.BEE -> {
            oval(Wing, 30f, 18f, 22f, 28f); oval(Wing, 46f, 16f, 22f, 28f)
            polygon(Ink, 18f, 56f, 8f, 58f, 18f, 60f)
            val body = ovalPath(16f, 42f, 52f, 32f)
            clipPath(body) {
                drawPath(body, Gold)
                for (x in listOf(30f, 44f, 58f)) drawRect(Ink, Offset(x, 40f), Size(6f, 36f))
            }
            dot(74f, 56f, 13f, Gold)
            eyes(72f, 80f, 53f)
            line(72f, 44f, 68f, 34f, Ink, 2f); line(78f, 44f, 82f, 34f, Ink, 2f)
        }
        MagicPicture.MOSQUITO -> {
            oval(Wing, 30f, 10f, 20f, 34f); oval(Wing, 46f, 8f, 18f, 32f)
            for ((x, ex) in listOf(36f to 22f, 44f to 40f, 52f to 62f)) {
                line(x, 50f, ex, 66f, Ink, 1.5f); line(ex, 66f, ex - 4, 92f, Ink, 1.5f)
            }
            rotate(15f, Offset(40f, 46f)) { oval(Gray, 10f, 40f, 44f, 12f) }
            dot(66f, 44f, 6f, DarkGray)
            dot(56f, 44f, 8f, Brown)
            line(70f, 46f, 92f, 62f, Ink, 1.5f)
            line(68f, 40f, 78f, 28f, Ink, 1.2f)
        }
        MagicPicture.BUTTERFLY -> {
            val magenta = Color(0xFFE0507A)
            oval(Pink, 10f, 18f, 38f, 34f); oval(Pink, 52f, 18f, 38f, 34f)
            oval(magenta, 16f, 50f, 30f, 28f); oval(magenta, 54f, 50f, 30f, 28f)
            for ((x, y) in listOf(26f to 32f, 74f to 32f, 30f to 62f, 70f to 62f)) dot(x, y, 4f, Color.White)
            oval(Purple, 44f, 30f, 12f, 50f)
            dot(50f, 28f, 8f, Purple)
            line(47f, 22f, 40f, 10f, Purple, 1.8f); line(53f, 22f, 60f, 10f, Purple, 1.8f)
            eyes(47f, 53f, 28f)
        }
        MagicPicture.FLY -> {
            for ((x, ex) in listOf(40f to 26f, 50f to 50f, 60f to 74f)) {
                line(x, 56f, ex, 70f, Brown, 1.8f); line(ex, 70f, ex + (ex - 50) * 0.3f, 88f, Brown, 1.8f)
            }
            rotate(-25f, Offset(40f, 40f)) { oval(Color(0x99C9D1D9), 16f, 24f, 34f, 16f) }
            rotate(25f, Offset(60f, 40f)) { oval(Color(0x99C9D1D9), 50f, 24f, 34f, 16f) }
            oval(DarkGray, 34f, 40f, 32f, 24f)
            dot(50f, 36f, 10f, Night)
            dot(43f, 34f, 5f, Red); dot(57f, 34f, 5f, Red)
        }
        MagicPicture.GRASSHOPPER -> {
            line(30f, 58f, 26f, 76f, Lime, 2.5f); line(42f, 58f, 40f, 76f, Lime, 2.5f)
            oval(Lime, 22f, 42f, 56f, 18f)
            polygon(Green, 56f, 48f, 76f, 26f, 82f, 30f, 66f, 54f)
            line(78f, 30f, 92f, 72f, Green, 3f)
            oval(Lime, 10f, 36f, 18f, 20f)
            dot(16f, 42f, 2.4f, Ink)
            line(14f, 38f, 6f, 20f, Green, 1.5f); line(18f, 37f, 16f, 18f, Green, 1.5f)
        }
        MagicPicture.PINEAPPLE -> {
            polygon(Green, 50f, 38f, 38f, 10f, 48f, 22f, 50f, 4f, 54f, 22f, 64f, 10f)
            polygon(Green, 50f, 38f, 30f, 22f, 70f, 22f)
            val fruit = ovalPath(28f, 32f, 44f, 60f)
            clipPath(fruit) {
                drawPath(fruit, Gold)
                for (i in -6..6) {
                    line(28f + i * 8, 32f, 72f + i * 8, 92f, Orange, 2f)
                    line(72f - i * 8, 32f, 28f - i * 8, 92f, Orange, 2f)
                }
            }
        }
        MagicPicture.WATERMELON -> {
            val whole = ovalPath(40f, 16f, 54f, 54f)
            clipPath(whole) {
                drawPath(whole, Lime)
                for (x in listOf(48f, 60f, 72f, 84f)) line(x, 14f, x - 4, 72f, Green, 4f)
            }
            drawArc(Green, 0f, 180f, true, Offset(6f, 30f), Size(56f, 56f))
            drawArc(Color.White, 0f, 180f, true, Offset(9f, 33f), Size(50f, 50f))
            drawArc(Red, 0f, 180f, true, Offset(12f, 36f), Size(44f, 44f))
            for ((x, y) in listOf(24f to 64f, 34f to 70f, 44f to 64f, 34f to 62f)) oval(Ink, x - 1.5f, y - 2.5f, 3f, 5f)
        }
        MagicPicture.TOMATO -> {
            dot(50f, 56f, 32f, Red)
            oval(Color(0x66FFFFFF), 28f, 40f, 12f, 8f)
            polygon(Green, 50f, 22f, 60f, 30f, 74f, 26f, 62f, 34f, 50f, 32f, 38f, 34f, 26f, 26f, 40f, 30f)
            line(50f, 26f, 50f, 14f, Green, 4f)
        }
        MagicPicture.ALMOND -> {
            almond(30f, 54f, -30f); almond(50f, 46f, 0f); almond(70f, 54f, 30f)
        }
        MagicPicture.COCONUT -> {
            dot(50f, 52f, 36f, Brown)
            dot(50f, 52f, 29f, Color.White)
            dot(50f, 52f, 22f, Color(0xFFEDEFF1))
        }
        MagicPicture.DATE -> {
            val date = Color(0xFF7A3B22)
            for ((x, y, a) in listOf(Triple(30f, 40f, 20f), Triple(56f, 42f, -15f), Triple(40f, 62f, -5f), Triple(64f, 64f, 25f))) {
                rotate(a, Offset(x, y)) {
                    oval(date, x - 18, y - 8, 36f, 16f)
                    line(x - 10, y - 2, x + 8, y - 3, Color(0xFFB36B4A), 2f)
                }
            }
        }
        MagicPicture.CUCUMBER -> rotate(-35f, Offset(50f, 50f)) {
            oval(Green, 12f, 36f, 76f, 28f)
            for (y in listOf(44f, 50f, 56f)) line(20f, y, 80f, y, Lime, 2f)
        }
        MagicPicture.LEMON -> {
            dot(16f, 56f, 5f, Gold); dot(84f, 56f, 5f, Gold)
            oval(Gold, 16f, 34f, 68f, 44f)
            oval(Color(0x66FFFFFF), 30f, 42f, 18f, 8f)
            line(52f, 34f, 56f, 24f, Brown, 2.5f)
            rotate(-20f, Offset(64f, 22f)) { oval(Green, 56f, 16f, 26f, 11f) }
        }
        MagicPicture.PEAS -> {
            rotate(-25f, Offset(50f, 50f)) {
                oval(Green, 8f, 38f, 84f, 26f)
                oval(Lime, 12f, 40f, 76f, 14f)
                for (i in 0 until 6) dot(22f + i * 11.5f, 47f, 5.5f, Color(0xFFB7DD7A))
            }
            dot(18f, 82f, 4.5f, Color(0xFFB7DD7A)); dot(84f, 80f, 4.5f, Color(0xFFB7DD7A))
        }
        MagicPicture.PEPPER -> {
            line(50f, 26f, 54f, 10f, Green, 5f)
            oval(Gold, 18f, 26f, 30f, 62f); oval(Gold, 52f, 26f, 30f, 62f)
            oval(Color(0xFFF4B82E), 34f, 24f, 32f, 66f)
            oval(Color(0x66FFFFFF), 26f, 36f, 8f, 22f)
            oval(Green, 40f, 22f, 20f, 8f)
        }
        MagicPicture.POTATO -> {
            oval(Color(0xFFD9C27A), 14f, 28f, 72f, 46f)
            oval(Color(0x55FFFFFF), 50f, 34f, 20f, 8f)
            for ((x, y) in listOf(28f to 44f, 40f to 56f, 54f to 48f, 66f to 60f, 34f to 64f, 70f to 44f)) {
                oval(Color(0xFFA88E4E), x - 1.5f, y - 2.5f, 3f, 5f)
            }
        }
        MagicPicture.BEETROOT -> {
            line(46f, 40f, 34f, 12f, Green, 4f); line(52f, 40f, 56f, 8f, Green, 4f); line(56f, 40f, 76f, 16f, Green, 4f)
            rotate(-20f, Offset(34f, 14f)) { oval(Green, 26f, 4f, 16f, 22f) }
            oval(Green, 50f, 2f, 14f, 22f)
            rotate(30f, Offset(76f, 16f)) { oval(Green, 68f, 6f, 16f, 22f) }
            polygon(Color(0xFFB0245E), 42f, 76f, 58f, 76f, 48f, 96f)
            dot(50f, 60f, 22f, Color(0xFFB0245E))
            oval(Color(0x55FFFFFF), 56f, 54f, 8f, 12f)
        }
        MagicPicture.PARSNIP -> {
            line(50f, 32f, 38f, 10f, Green, 3f); line(50f, 32f, 50f, 6f, Green, 3f); line(50f, 32f, 62f, 10f, Green, 3f)
            for ((x, y) in listOf(38f to 12f, 50f to 8f, 62f to 12f)) dot(x, y, 6f, Green)
            polygon(Cream, 38f, 32f, 62f, 32f, 52f, 94f)
            line(44f, 46f, 52f, 47f, Tan, 1.5f); line(46f, 62f, 54f, 62f, Tan, 1.5f)
        }
        MagicPicture.PUMPKIN -> {
            line(50f, 32f, 54f, 12f, Green, 6f)
            oval(Orange, 10f, 30f, 44f, 56f); oval(Orange, 46f, 30f, 44f, 56f)
            oval(Color(0xFFF6A84F), 30f, 28f, 40f, 60f)
            line(50f, 32f, 50f, 84f, Color(0xFFD9772A), 2f)
        }
        MagicPicture.ONION -> {
            val onion = Path().apply {
                moveTo(50f, 14f); cubicTo(56f, 32f, 92f, 42f, 82f, 72f)
                cubicTo(76f, 90f, 24f, 90f, 18f, 72f); cubicTo(8f, 42f, 44f, 32f, 50f, 14f); close()
            }
            drawPath(onion, Purple)
            for ((sx, ex) in listOf(40f to 30f, 50f to 50f, 60f to 70f)) {
                curve(Color(0xFFB79AD8), 2f) { moveTo(50f, 18f); quadraticTo(sx, 50f, ex, 84f) }
            }
            line(44f, 86f, 40f, 92f, Tan, 2f); line(56f, 86f, 60f, 92f, Tan, 2f)
        }
        MagicPicture.SPINACH -> {
            for ((angle, color) in listOf(-30f to Green, 30f to Green, 0f to Color(0xFF2A6B45))) {
                rotate(angle, Offset(50f, 86f)) {
                    oval(color, 36f, 16f, 28f, 50f)
                    line(50f, 22f, 50f, 88f, Lime, 2f)
                }
            }
        }
        MagicPicture.BROCCOLI -> {
            polygon(Lime, 42f, 50f, 58f, 50f, 62f, 92f, 38f, 92f)
            for ((x, y) in listOf(30f to 42f, 50f to 30f, 70f to 42f, 40f to 50f, 60f to 50f, 50f to 44f)) dot(x, y, 15f, Green)
            for ((x, y) in listOf(28f to 38f, 48f to 26f, 68f to 38f)) dot(x, y, 4f, Color(0xFF2A6B45))
        }
        MagicPicture.TULIP -> {
            line(50f, 46f, 50f, 94f, Green, 4f)
            rotate(-20f, Offset(50f, 80f)) { oval(Green, 30f, 54f, 14f, 36f) }
            rotate(20f, Offset(50f, 80f)) { oval(Green, 56f, 54f, 14f, 36f) }
            val cup = Path().apply {
                moveTo(30f, 18f); lineTo(40f, 28f); lineTo(50f, 12f); lineTo(60f, 28f); lineTo(70f, 18f)
                cubicTo(74f, 46f, 62f, 54f, 50f, 54f); cubicTo(38f, 54f, 26f, 46f, 30f, 18f); close()
            }
            drawPath(cup, Color(0xFFE76F9A))
        }
        MagicPicture.ROSE -> {
            line(50f, 60f, 50f, 96f, Green, 4f)
            rotate(-30f, Offset(70f, 70f)) { oval(Green, 56f, 64f, 26f, 12f) }
            dot(50f, 42f, 30f, Red)
            for (r in listOf(22f, 14f, 7f)) {
                drawArc(Color(0xFFA82A33), 200f, 260f, false, Offset(50f - r, 42f - r), Size(r * 2, r * 2),
                    style = Stroke(2.5f, cap = StrokeCap.Round))
            }
        }
        MagicPicture.GERBERA -> {
            line(50f, 60f, 50f, 96f, Green, 4f)
            repeat(16) { i ->
                rotate(i * 22.5f, Offset(50f, 42f)) { oval(if (i % 2 == 0) Orange else Gold, 45f, 8f, 10f, 30f) }
            }
            dot(50f, 42f, 10f, Brown)
        }
        MagicPicture.HOT_AIR_BALLOON -> {
            line(36f, 64f, 42f, 82f, Brown, 1.5f); line(64f, 64f, 58f, 82f, Brown, 1.5f)
            drawRect(Brown, Offset(40f, 80f), Size(20f, 14f))
            val balloon = ovalPath(18f, 4f, 64f, 68f)
            clipPath(balloon) {
                drawPath(balloon, Gold)
                oval(AprenderColors.Blue, 32f, 2f, 36f, 72f)
                oval(Orange, 42f, 2f, 16f, 72f)
            }
        }
        MagicPicture.BOAT -> {
            drawRect(Red, Offset(40f, 34f), Size(26f, 26f))
            drawRect(Sky, Offset(46f, 40f), Size(14f, 10f))
            drawRect(AprenderColors.Blue, Offset(36f, 30f), Size(34f, 6f))
            polygon(Color(0xFFE7EEF5), 6f, 58f, 94f, 54f, 84f, 80f, 18f, 80f)
            polygon(AprenderColors.Blue, 12f, 72f, 88f, 70f, 84f, 80f, 18f, 80f)
            dot(80f, 63f, 4f, Red); dot(80f, 63f, 2f, Color(0xFFE7EEF5))
        }
        MagicPicture.AIRPLANE -> {
            polygon(DarkGray, 44f, 50f, 60f, 50f, 50f, 86f, 38f, 86f)
            polygon(Gray, 12f, 46f, 6f, 24f, 22f, 46f)
            oval(Gray, 8f, 42f, 84f, 16f)
            polygon(DarkGray, 44f, 46f, 56f, 46f, 52f, 22f, 44f, 22f)
            for (i in 0 until 5) dot(34f + i * 8, 49f, 2f, Sky)
            oval(Sky, 80f, 45f, 8f, 5f)
        }
        MagicPicture.URANUS -> {
            val planet = ovalPath(16f, 16f, 68f, 68f)
            clipPath(planet) {
                drawPath(planet, Color(0xFF4F8FD6))
                for (y in listOf(30f, 46f, 62f)) oval(Sky, 10f, y, 80f, 8f)
            }
        }
        MagicPicture.SATURN -> rotate(-18f, Offset(50f, 50f)) {
            val ring = Color(0xFF9DAAE0)
            drawArc(ring, 180f, 180f, false, Offset(6f, 38f), Size(88f, 24f), style = Stroke(6f))
            dot(50f, 50f, 26f, Color(0xFFB98BD6))
            for (y in listOf(36f, 58f)) line(30f, y, 70f, y, Color(0xFF8A5BB0), 3f)
            drawArc(ring, 0f, 180f, false, Offset(6f, 38f), Size(88f, 24f), style = Stroke(6f))
        }
        MagicPicture.MARS -> {
            dot(50f, 50f, 34f, Color(0xFFD9573B))
            oval(Color(0xFFA63A28), 28f, 34f, 24f, 14f); oval(Color(0xFFA63A28), 50f, 58f, 22f, 12f)
            oval(Color(0xFFF08A5C), 56f, 28f, 16f, 8f)
        }
        MagicPicture.MOON -> {
            dot(50f, 50f, 34f, Gray)
            for ((x, y, r) in listOf(Triple(38f, 38f, 7f), Triple(62f, 46f, 10f), Triple(44f, 66f, 5f), Triple(66f, 70f, 4f))) {
                dot(x, y, r, Color(0xFF8C949D))
            }
        }
        MagicPicture.STARS -> {
            star(32f, 34f, 20f); star(72f, 30f, 14f); star(62f, 72f, 12f)
        }
        MagicPicture.NOSE -> {
            polygon(Skin, 42f, 12f, 58f, 12f, 64f, 58f, 78f, 70f, 70f, 84f, 30f, 84f, 22f, 70f, 36f, 58f)
            line(48f, 18f, 46f, 60f, Color(0xFFF7D9BE), 3f)
            oval(Color(0xFF7A4A3A), 34f, 76f, 12f, 5f); oval(Color(0xFF7A4A3A), 54f, 76f, 12f, 5f)
        }
        MagicPicture.EYE -> {
            val lid = Path().apply { moveTo(8f, 50f); quadraticTo(50f, 6f, 92f, 50f); quadraticTo(50f, 94f, 8f, 50f); close() }
            drawPath(lid, Skin)
            val white = Path().apply { moveTo(16f, 50f); quadraticTo(50f, 18f, 84f, 50f); quadraticTo(50f, 82f, 16f, 50f); close() }
            drawPath(white, Color.White)
            dot(50f, 50f, 15f, Color(0xFF6BB3E6))
            dot(50f, 50f, 7f, Ink)
            dot(54f, 46f, 2.5f, Color.White)
            for (i in 0..6) {
                val x = 22f + i * 9.3f
                line(x, 32f - if (i in 2..4) 4f else 0f, x - 2, 24f - if (i in 2..4) 4f else 0f, Ink, 1.6f)
            }
        }
        MagicPicture.MOUTH -> {
            oval(Red, 12f, 22f, 76f, 46f)
            oval(Color(0xFF5A1F28), 20f, 30f, 60f, 30f)
            drawRect(Color.White, Offset(30f, 30f), Size(40f, 7f))
            oval(Color(0xFFE88A96), 32f, 44f, 36f, 46f)
            line(50f, 52f, 50f, 78f, Color(0xFFC96777), 2f)
        }
        MagicPicture.LUNGS -> {
            val lung = Color(0xFFD8606A)
            line(50f, 8f, 50f, 40f, Pink, 6f)
            rotate(12f, Offset(30f, 58f)) { oval(lung, 12f, 26f, 34f, 62f) }
            rotate(-12f, Offset(70f, 58f)) { oval(lung, 54f, 26f, 34f, 62f) }
            line(50f, 40f, 36f, 52f, Pink, 4f); line(50f, 40f, 64f, 52f, Pink, 4f)
            line(36f, 52f, 30f, 66f, Color(0xFFA9404A), 2f); line(64f, 52f, 70f, 66f, Color(0xFFA9404A), 2f)
        }
        MagicPicture.BRAIN -> {
            val brain = Color(0xFFF0A3B5)
            for ((x, y, r) in listOf(Triple(30f, 46f, 18f), Triple(46f, 34f, 18f), Triple(64f, 36f, 17f),
                Triple(72f, 54f, 16f), Triple(54f, 58f, 18f), Triple(34f, 62f, 15f))) dot(x, y, r, brain)
            val fold = Color(0xFFC96F86)
            line(50f, 20f, 50f, 74f, fold, 2.5f)
            curve(fold, 2f) { moveTo(22f, 44f); quadraticTo(32f, 36f, 40f, 46f) }
            curve(fold, 2f) { moveTo(60f, 30f); quadraticTo(68f, 42f, 78f, 40f) }
            curve(fold, 2f) { moveTo(28f, 62f); quadraticTo(38f, 54f, 44f, 64f) }
            curve(fold, 2f) { moveTo(58f, 60f); quadraticTo(66f, 52f, 76f, 58f) }
            line(54f, 74f, 56f, 90f, brain, 7f)
        }
        MagicPicture.HEART -> {
            drawRect(Color(0xFFA82A33), Offset(42f, 8f), Size(8f, 24f))
            drawRect(Color(0xFF6C8FCB), Offset(54f, 12f), Size(8f, 20f))
            val heart = Path().apply {
                moveTo(50f, 90f); cubicTo(10f, 64f, 12f, 24f, 36f, 26f)
                cubicTo(44f, 27f, 50f, 34f, 50f, 40f); cubicTo(50f, 34f, 56f, 27f, 64f, 26f)
                cubicTo(88f, 24f, 90f, 64f, 50f, 90f); close()
            }
            drawPath(heart, Red)
            curve(Color(0xFFA82A33), 2f) { moveTo(44f, 44f); quadraticTo(40f, 60f, 48f, 76f) }
        }
        MagicPicture.THERMOMETER -> rotate(-25f, Offset(50f, 50f)) {
            drawRoundRect(Color(0xFFE7EEF5), Offset(8f, 40f), Size(84f, 20f), CornerRadius(10f, 10f))
            drawRoundRect(Gray, Offset(8f, 40f), Size(84f, 20f), CornerRadius(10f, 10f), style = Stroke(1.5f))
            drawRoundRect(Sky, Offset(72f, 40f), Size(20f, 20f), CornerRadius(10f, 10f))
            drawRect(DarkGray, Offset(40f, 44f), Size(22f, 12f))
            for (i in 0 until 3) drawRect(Color(0xFF9BE07A), Offset(43f + i * 6.5f, 47f), Size(4f, 6f))
            line(2f, 50f, 8f, 50f, DarkGray, 3f)
        }
        MagicPicture.HOURGLASS -> {
            drawRect(Brown, Offset(22f, 8f), Size(56f, 6f)); drawRect(Brown, Offset(22f, 86f), Size(56f, 6f))
            polygon(Color(0x668DD8F0), 28f, 14f, 72f, 14f, 54f, 50f, 72f, 86f, 28f, 86f, 46f, 50f)
            polygon(Gold, 36f, 30f, 64f, 30f, 50f, 48f)
            polygon(Gold, 50f, 70f, 66f, 84f, 34f, 84f)
            line(50f, 50f, 50f, 70f, Gold, 1.5f)
        }
        MagicPicture.TAPE_MEASURE -> {
            drawRect(Gold, Offset(48f, 56f), Size(46f, 12f))
            for (i in 0 until 8) line(52f + i * 5.5f, 56f, 52f + i * 5.5f, if (i % 2 == 0) 62f else 60f, Ink, 1.2f)
            drawRect(DarkGray, Offset(90f, 54f), Size(4f, 16f))
            drawRoundRect(Night, Offset(8f, 24f), Size(50f, 50f), CornerRadius(12f, 12f))
            dot(33f, 49f, 15f, Gray)
            dot(33f, 49f, 6f, Night)
        }
        MagicPicture.WHISK -> {
            drawRoundRect(Orange, Offset(30f, 4f), Size(30f, 12f), CornerRadius(6f, 6f))
            line(45f, 16f, 45f, 30f, Orange, 6f)
            dot(56f, 36f, 11f, Gray); dot(56f, 36f, 4f, DarkGray)
            line(56f, 36f, 74f, 46f, Orange, 5f); line(74f, 46f, 82f, 44f, Orange, 6f)
            line(42f, 36f, 40f, 66f, DarkGray, 3f); line(50f, 36f, 52f, 66f, DarkGray, 3f)
            drawOval(DarkGray, Offset(30f, 62f), Size(16f, 32f), style = Stroke(2.5f))
            drawOval(DarkGray, Offset(46f, 62f), Size(16f, 32f), style = Stroke(2.5f))
        }
        MagicPicture.WRENCH -> rotate(-40f, Offset(50f, 50f)) {
            drawRoundRect(Gray, Offset(14f, 44f), Size(60f, 12f), CornerRadius(6f, 6f))
            dot(20f, 50f, 3f, Color.White)
            dot(78f, 50f, 16f, Gray)
            drawRect(Color.White, Offset(80f, 44f), Size(16f, 12f))
            drawRect(DarkGray, Offset(64f, 46f), Size(8f, 8f))
        }
        MagicPicture.PLIERS -> {
            line(10f, 30f, 46f, 46f, Red, 8f); line(10f, 70f, 46f, 54f, Red, 8f)
            polygon(Gray, 44f, 42f, 92f, 46f, 92f, 50f, 48f, 52f)
            polygon(Gray, 44f, 58f, 92f, 54f, 92f, 50f, 48f, 48f)
            dot(52f, 50f, 4f, DarkGray)
        }
        else -> drawExpansionPicture(picture)
    }
}

private fun ovalPath(x: Float, y: Float, w: Float, h: Float) =
    Path().apply { addOval(androidx.compose.ui.geometry.Rect(x, y, x + w, y + h)) }

private fun DrawScope.curve(color: Color, width: Float, build: Path.() -> Unit) =
    drawPath(Path().apply(build), color, style = Stroke(width, cap = StrokeCap.Round))

private fun DrawScope.bigCat(color: Color, mark: DrawScope.(Float, Float) -> Unit) {
    for (x in listOf(26f, 38f, 60f, 70f)) line(x, 60f, x, 86f, color, 7f)
    line(18f, 48f, 6f, 34f, color, 4f)
    oval(color, 16f, 38f, 62f, 28f)
    oval(Color(0xFFFFF3DD), 26f, 56f, 42f, 10f)
    for (x in listOf(28f, 40f, 52f, 64f)) mark(x, 48f)
    polygon(color, 70f, 32f, 72f, 22f, 80f, 30f)
    polygon(color, 82f, 30f, 88f, 22f, 90f, 32f)
    dot(80f, 42f, 13f, color)
    oval(Color(0xFFFFF3DD), 78f, 44f, 14f, 10f)
    dot(85f, 47f, 2.5f, Ink)
    dot(78f, 38f, 2f, Ink); dot(87f, 38f, 2f, Ink)
}

private fun DrawScope.almond(x: Float, y: Float, angle: Float) = rotate(angle, Offset(x, y)) {
    val shell = Path().apply {
        moveTo(x, y - 24); cubicTo(x + 16, y - 10, x + 14, y + 18, x, y + 22)
        cubicTo(x - 14, y + 18, x - 16, y - 10, x, y - 24); close()
    }
    drawPath(shell, Brown)
    line(x, y - 18, x, y + 16, Color(0xFF7A4A2E), 1.5f)
}

private fun DrawScope.star(cx: Float, cy: Float, r: Float) {
    val points = FloatArray(20)
    for (i in 0 until 10) {
        val radius = if (i % 2 == 0) r else r * 0.45f
        val angle = -Math.PI / 2 + i * Math.PI / 5
        points[i * 2] = cx + radius * cos(angle).toFloat()
        points[i * 2 + 1] = cy + radius * sin(angle).toFloat()
    }
    polygon(Gold, *points)
}
