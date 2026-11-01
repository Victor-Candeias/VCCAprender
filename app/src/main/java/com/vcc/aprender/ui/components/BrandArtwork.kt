package com.vcc.aprender.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.vcc.aprender.ui.theme.AprenderColors

enum class BrandSymbol { Star, Bear, Fox, Panda, Shield, Book, Trophy, Chart, Sound, Puzzle, Success, Encourage, Lock, Add, Calendar, Target, Person }

@Composable
fun BrandArtwork(symbol: BrandSymbol, modifier: Modifier = Modifier, description: String? = symbol.name) {
    // When a heading, button or feedback already says what this symbol represents,
    // hide the artwork from TalkBack to prevent repeated announcements.
    val accessibilityModifier = if (description == null) Modifier.clearAndSetSemantics { }
        else Modifier.semantics { contentDescription = description }
    Canvas(modifier = modifier.size(48.dp).background(AprenderColors.Sky, CircleShape)
        .then(accessibilityModifier)) {
        val u = size.minDimension / 100f
        fun circle(x: Float, y: Float, r: Float, color: Color) =
            drawCircle(color, r*u, Offset(x*u,y*u))
        fun line(x: Float,y: Float, x2: Float,y2: Float,color: Color, w: Float = 7f) =
            drawLine(color, Offset(x*u,y*u), Offset(x2*u,y2*u),w*u)
        fun polygon(points: List<Pair<Float,Float>>, color: Color) {
            val p=Path().apply {
                moveTo(points.first().first*u,points.first().second*u)
                points.drop(1).forEach { lineTo(it.first*u,it.second*u) }
                close()
            }
            drawPath(p,color)
        }
        when(symbol) {
            BrandSymbol.Star -> {
                val vertices=(0 until 10).map { i ->
                    val angle=Math.PI*i/5.0-Math.PI/2
                    val radius=if(i%2==0) 35.0 else 16.0
                    Pair((50+radius*kotlin.math.cos(angle)).toFloat(),(50+radius*kotlin.math.sin(angle)).toFloat())
                }
                polygon(vertices, Color(0xFFFFBA38))
            }
            BrandSymbol.Success -> {
                circle(50f,50f,34f,Color(0xFF278653))
                line(30f,50f,45f,65f,Color.White)
                line(45f,65f,72f,36f,Color.White)
            }
            BrandSymbol.Trophy -> {
                val gold = Color(0xFFFFBA38)
                polygon(listOf(28f to 20f,72f to 20f,67f to 52f,50f to 65f,33f to 52f),gold)
                line(28f,28f,16f,28f,gold,5f); line(16f,28f,22f,48f,gold,5f)
                line(22f,48f,34f,53f,gold,5f)
                line(72f,28f,84f,28f,gold,5f); line(84f,28f,78f,48f,gold,5f)
                line(78f,48f,66f,53f,gold,5f)
                line(50f,61f,50f,79f,AprenderColors.Blue)
                line(34f,82f,66f,82f,AprenderColors.Blue)
            }
            BrandSymbol.Bear, BrandSymbol.Fox, BrandSymbol.Panda, BrandSymbol.Person -> {
                val face=when(symbol) { BrandSymbol.Fox->Color(0xFFEB9152); BrandSymbol.Panda->Color.White; BrandSymbol.Person->Color(0xFFDCA879); else->Color(0xFFB77B50) }
                if(symbol==BrandSymbol.Fox) {
                    polygon(listOf(18f to 42f,20f to 12f,43f to 33f),face)
                    polygon(listOf(57f to 33f,80f to 12f,82f to 42f),face)
                } else if(symbol!=BrandSymbol.Person) {
                    circle(27f,31f,14f,face);circle(73f,31f,14f,face)
                }
                if(symbol==BrandSymbol.Panda) {
                    circle(27f,31f,14f,AprenderColors.Ink);circle(73f,31f,14f,AprenderColors.Ink)
                }
                circle(50f,53f,30f,face)
                if(symbol==BrandSymbol.Fox) {
                    polygon(listOf(23f to 51f,50f to 67f,77f to 51f,65f to 76f,35f to 76f),Color.White)
                }
                if(symbol==BrandSymbol.Panda) {
                    circle(38f,49f,10f,AprenderColors.Ink);circle(62f,49f,10f,AprenderColors.Ink)
                }
                val eye=if(symbol==BrandSymbol.Panda) Color.White else AprenderColors.Ink
                circle(39f,49f,4f,eye);circle(61f,49f,4f,eye)
                circle(50f,65f,6f,AprenderColors.Ink)
            }
            BrandSymbol.Book -> {
                polygon(listOf(18f to 27f,48f to 32f,48f to 79f,18f to 73f),Color.White)
                polygon(listOf(52f to 32f,82f to 27f,82f to 73f,52f to 79f),Color.White)
                line(50f,30f,50f,80f,AprenderColors.Blue,4f)
            }
            BrandSymbol.Sound -> {
                circle(35f,69f,12f,AprenderColors.Blue)
                line(45f,68f,45f,27f,AprenderColors.Blue)
                line(45f,27f,73f,21f,AprenderColors.Blue)
                line(73f,21f,73f,58f,AprenderColors.Blue)
                circle(64f,60f,11f,AprenderColors.Blue)
            }
            BrandSymbol.Shield -> {
                polygon(listOf(50f to 16f,79f to 28f,74f to 65f,50f to 84f,26f to 65f,21f to 28f),AprenderColors.Blue)
                line(38f,50f,48f,61f,Color.White)
                line(48f,61f,65f,40f,Color.White)
            }
            BrandSymbol.Lock -> {
                drawArc(AprenderColors.Blue, 180f, 180f, false,
                    Offset(33f*u,18f*u), Size(34f*u,44f*u), style=Stroke(7f*u))
                polygon(listOf(24f to 42f,76f to 42f,76f to 82f,24f to 82f),AprenderColors.Blue)
                circle(50f,59f,6f,Color.White)
                line(50f,60f,50f,71f,Color.White,4f)
            }
            BrandSymbol.Chart -> {
                line(24f,76f,77f,76f,AprenderColors.Blue,5f)
                line(30f,72f,30f,52f,Color(0xFF3E9B70),11f)
                line(49f,72f,49f,38f,Color(0xFFFFBA38),11f)
                line(68f,72f,68f,23f,AprenderColors.Blue,11f)
            }
            BrandSymbol.Calendar -> {
                polygon(listOf(20f to 25f,80f to 25f,80f to 82f,20f to 82f),Color.White)
                line(20f,35f,80f,35f,AprenderColors.Blue,12f)
                line(34f,19f,34f,32f,AprenderColors.Ink,5f)
                line(66f,19f,66f,32f,AprenderColors.Ink,5f)
                for(x in listOf(34f,50f,66f)) for(y in listOf(53f,69f)) circle(x,y,4f,AprenderColors.Blue)
            }
            BrandSymbol.Puzzle -> {
                polygon(listOf(22f to 26f,47f to 26f,47f to 48f,72f to 48f,72f to 78f,22f to 78f),AprenderColors.Blue)
                circle(47f,58f,10f,AprenderColors.Blue)
                circle(38f,26f,9f,AprenderColors.Blue)
                circle(22f,56f,9f,AprenderColors.Sky)
            }
            BrandSymbol.Target -> {
                circle(50f,50f,32f,AprenderColors.Blue)
                circle(50f,50f,23f,Color.White)
                circle(50f,50f,13f,Color(0xFFEC655E))
            }
            BrandSymbol.Encourage -> {
                circle(50f,50f,31f,Color(0xFFFFC44D))
                circle(40f,44f,4f,AprenderColors.Ink);circle(60f,44f,4f,AprenderColors.Ink)
                line(38f,61f,50f,68f,AprenderColors.Ink,4f)
                line(50f,68f,64f,58f,AprenderColors.Ink,4f)
            }
            BrandSymbol.Add -> {
                circle(50f,50f,32f,AprenderColors.Blue)
                line(50f,32f,50f,68f,Color.White)
                line(32f,50f,68f,50f,Color.White)
            }
        }
    }
}

fun avatarSymbol(avatar: String): BrandSymbol = when (avatar) {
    "🐻" -> BrandSymbol.Bear
    "🦊" -> BrandSymbol.Fox
    "🐼" -> BrandSymbol.Panda
    else -> BrandSymbol.Star
}
