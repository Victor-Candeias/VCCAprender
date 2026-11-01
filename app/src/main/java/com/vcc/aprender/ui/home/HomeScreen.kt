package com.vcc.aprender.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.vcc.aprender.domain.model.LearningTopic
import com.vcc.aprender.ui.theme.AprenderColors
import com.vcc.aprender.ui.components.TopicIllustration

private data class TopicArtwork(val symbol: String, val background: Color)

private fun artworkFor(topic: LearningTopic): TopicArtwork = when (topic) {
    LearningTopic.LETTERS -> TopicArtwork("🔤", AprenderColors.Sky)
    LearningTopic.NUMBERS -> TopicArtwork("🔢", AprenderColors.Sunshine)
    LearningTopic.COLORS -> TopicArtwork("🎨", AprenderColors.Coral)
    LearningTopic.SHAPES -> TopicArtwork("🔷", AprenderColors.Lavender)
    LearningTopic.ANIMALS -> TopicArtwork("🐻", AprenderColors.Mint)
    LearningTopic.SOUNDS -> TopicArtwork("🎵", AprenderColors.Sunshine)
    LearningTopic.WORDS -> TopicArtwork("📖", AprenderColors.Lavender)
}

@Composable
fun HomeScreen(state: HomeUiState, onRetry: () -> Unit, onTopicClick: (LearningTopic) -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.TopCenter) {
    LazyColumn(
        modifier = Modifier.widthIn(max = 720.dp).fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(AprenderColors.Sky, RoundedCornerShape(28.dp))
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("🌈  VCCAprender", style = MaterialTheme.typography.headlineLarge, color = AprenderColors.Navy)
                Text("Aprender, brincar e descobrir!", style = MaterialTheme.typography.titleMedium, color = AprenderColors.Navy)
                Text("Escolhe uma aventura e começa a explorar.", style = MaterialTheme.typography.bodyLarge, color = AprenderColors.Ink)
            }
        }
        item {
            Text("O que vamos descobrir hoje?", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 10.dp, bottom = 4.dp))
        }
        when (state) {
            HomeUiState.Loading -> item {
                Box(modifier = Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is HomeUiState.Error -> item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(state.message, color = MaterialTheme.colorScheme.error)
                    Button(onClick = onRetry) { Text("Tentar novamente") }
                }
            }
            is HomeUiState.Success -> items(state.topics, key = { it.name }) { topic ->
                ElevatedCard(
                    onClick = { onTopicClick(topic) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 108.dp).semantics {
                        contentDescription = "Abrir ${topic.title}: ${topic.description}"
                    },
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TopicIllustration(topic, modifier = Modifier.size(76.dp))
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(topic.title, style = MaterialTheme.typography.titleMedium)
                            Text(topic.description, style = MaterialTheme.typography.bodyLarge)
                        }
                        Text("›", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
    }
}
