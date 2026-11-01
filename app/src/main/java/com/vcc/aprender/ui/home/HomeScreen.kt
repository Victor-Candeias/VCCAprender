package com.vcc.aprender.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.vcc.aprender.domain.model.LearningTopic
import com.vcc.aprender.ui.components.TopicIllustration
import com.vcc.aprender.ui.theme.AprenderColors

@Composable
fun HomeScreen(state: HomeUiState, onRetry: () -> Unit, onTopicClick: (LearningTopic) -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            modifier = Modifier.widthIn(max = 1100.dp).fillMaxSize().testTag("va030-home-grid"),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(AprenderColors.Sky, RoundedCornerShape(28.dp))
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("🌈  VCCAprender", modifier = Modifier.semantics {
                        heading()
                        contentDescription = "VCCAprender"
                    }, style = MaterialTheme.typography.headlineLarge,
                        color = AprenderColors.Navy)
                    Text("Aprender, brincar e descobrir!", style = MaterialTheme.typography.titleMedium,
                        color = AprenderColors.Navy)
                    Text("Escolhe uma aventura e começa a explorar.",
                        style = MaterialTheme.typography.bodyLarge, color = AprenderColors.Ink)
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text("O que vamos descobrir hoje?", style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.semantics { heading() }.padding(top = 10.dp, bottom = 4.dp))
            }
            when (state) {
                HomeUiState.Loading -> item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(modifier = Modifier.fillMaxWidth().padding(24.dp),
                        contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is HomeUiState.Error -> item(span = { GridItemSpan(maxLineSpan) }) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(state.message, color = MaterialTheme.colorScheme.error)
                        Button(onClick = onRetry, modifier = Modifier.heightIn(min = 48.dp)) {
                            Text("Tentar novamente")
                        }
                    }
                }
                is HomeUiState.Success -> items(state.topics, key = { it.name }) { topic ->
                    var focusActive by remember(topic) { mutableStateOf(false) }
                    ElevatedCard(
                        onClick = { onTopicClick(topic) },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp)
                            .onFocusChanged { focusActive = it.hasFocus }
                            .border(if (focusActive) 3.dp else 0.dp, AprenderColors.Blue,
                                RoundedCornerShape(24.dp))
                            .semantics { contentDescription = "Abrir ${topic.title}: ${topic.description}" },
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TopicIllustration(topic, modifier = Modifier.size(72.dp), decorative = true)
                            Column(modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(topic.title, style = MaterialTheme.typography.titleMedium)
                                Text(topic.description, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}
