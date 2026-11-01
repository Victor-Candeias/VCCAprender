package com.vcc.aprender.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.vcc.aprender.ui.theme.AprenderColors

enum class BrandSymbol { Star, Bear, Fox, Panda, Shield, Book, Trophy, Chart, Sound, Puzzle, Success, Encourage, Lock, Add, Calendar, Target, Person }

@Composable
fun BrandArtwork(symbol: BrandSymbol, modifier: Modifier = Modifier, description: String = symbol.name) {
    Canvas(modifier = modifier.size(48.dp).background(AprenderColors.Sky, CircleShape)
        .semantics { contentDescription = description }) {
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
            BrandSymbol.Star, BrandSymbol.Success, BrandSymbol.Trophy -> {
                val vertices=(0 until 10).map { i ->
                    val angle=Math.PI*i/5.0-Math.PI/2
                    val radius=if(i%2==0) 35.0 else 16.0
                    Pair((50+radius*kotlin.math.cos(angle)).toFloat(),(50+radius*kotlin.math.sin(angle)).toFloat())
                }
                polygon(vertices, if(symbol==BrandSymbol.Success) Color(0xFF3E9B70) else Color(0xFFFFBA38))
            }
            BrandSymbol.Bear, BrandSymbol.Fox, BrandSymbol.Panda, BrandSymbol.Person -> {
                val face=when(symbol) { BrandSymbol.Fox->Color(0xFFEB9152); BrandSymbol.Panda->Color.White; BrandSymbol.Person->Color(0xFFDCA879); else->Color(0xFFB77B50) }
                if(symbol!=BrandSymbol.Person) {
                    circle(27f,31f,14f,face);circle(73f,31f,14f,face)
                }
                circle(50f,53f,30f,face)
                circle(39f,49f,5f,AprenderColors.Ink);circle(61f,49f,5f,AprenderColors.Ink)
                circle(50f,65f,6f,AprenderColors.Ink)
                if(symbol==BrandSymbol.Fox) polygon(listOf(31f to 72f,69f to 72f,50f to 85f),Color.White)
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
            BrandSymbol.Shield, BrandSymbol.Lock -> {
                polygon(listOf(50f to 16f,79f to 28f,74f to 65f,50f to 84f,26f to 65f,21f to 28f),AprenderColors.Blue)
                line(38f,50f,48f,61f,Color.White)
                line(48f,61f,65f,40f,Color.White)
            }
            BrandSymbol.Chart, BrandSymbol.Calendar -> {
                line(24f,76f,77f,76f,AprenderColors.Blue,5f)
                line(30f,72f,30f,52f,Color(0xFF3E9B70),11f)
                line(49f,72f,49f,38f,Color(0xFFFFBA38),11f)
                line(68f,72f,68f,23f,AprenderColors.Blue,11f)
            }
            BrandSymbol.Puzzle, BrandSymbol.Target -> {
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
