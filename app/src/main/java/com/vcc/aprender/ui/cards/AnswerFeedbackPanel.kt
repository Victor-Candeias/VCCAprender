package com.vcc.aprender.ui.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import com.vcc.aprender.ui.theme.AprenderColors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vcc.aprender.domain.cards.AnswerFeedback
import com.vcc.aprender.ui.components.BrandArtwork
import com.vcc.aprender.ui.components.BrandSymbol

@Composable
fun AnswerFeedbackPanel(feedback: AnswerFeedback) {
    AnimatedVisibility(visible = true) {
    Card(modifier = Modifier.fillMaxWidth().semantics { liveRegion = LiveRegionMode.Polite }, shape = RoundedCornerShape(24.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth().background(if (feedback.emoji.contains("🥳")) AprenderColors.Mint else AprenderColors.Sunshine).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BrandArtwork(if (feedback.emoji.contains("🥳")) BrandSymbol.Success else BrandSymbol.Encourage,
                description = feedback.title)
            Text(feedback.title, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
            Text(feedback.message, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface)
        }
    }
    }
}
