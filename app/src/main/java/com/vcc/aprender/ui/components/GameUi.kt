package com.vcc.aprender.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vcc.aprender.ui.theme.AprenderColors

@Composable
fun GameProgress(current: Int, total: Int, label: String = "Exercício") {
    val fraction = if (total > 0) (current.toFloat() / total).coerceIn(0f, 1f) else 0f
    val animated by animateFloatAsState(fraction, label = "Progresso do jogo")
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        Text("$label $current de $total", style = MaterialTheme.typography.titleMedium)
        LinearProgressIndicator(
            progress = { animated },
            modifier = Modifier.fillMaxWidth().semantics {
                contentDescription = "$label $current de $total"
            },
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
    val tint = when {
        correct -> AprenderColors.Mint
        selected -> AprenderColors.Sunshine
        else -> Color.White
    }
    Card(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth().heightIn(min = 64.dp).semantics {
            contentDescription = listOfNotNull(prefix, text).joinToString(" ")
        },
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(tint).padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (prefix != null) Text(prefix, style = MaterialTheme.typography.titleMedium)
            Text(text, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun GameFeedback(message: String?, positive: Boolean) {
    AnimatedVisibility(visible = message != null) {
        Card(
            modifier = Modifier.fillMaxWidth().semantics { liveRegion = LiveRegionMode.Polite },
            shape = RoundedCornerShape(22.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().background(if (positive) AprenderColors.Mint else AprenderColors.Sunshine).padding(16.dp)) {
            BrandArtwork(if (positive) BrandSymbol.Success else BrandSymbol.Encourage,
                description = if (positive) "Resposta correta" else "Continua a tentar")
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
