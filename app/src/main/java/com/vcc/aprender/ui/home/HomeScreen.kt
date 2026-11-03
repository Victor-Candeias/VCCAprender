package com.vcc.aprender.ui.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.vcc.aprender.domain.magic.FingerMagicContent
import com.vcc.aprender.ui.components.BrandArtwork
import com.vcc.aprender.ui.components.BrandSymbol
import com.vcc.aprender.ui.theme.AprenderColors

/**
 * VA-034: one activity only. Account, progress and parental tools remain under Settings.
 * Do not surface LearningTopic cards here; they are historical progress metadata.
 */
@Composable
fun HomeScreen(
    onFingerMagicClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
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
                    BrandArtwork(BrandSymbol.Book, description = null)
                    Text(
                        "VCCAprender",
                        modifier = Modifier.semantics {
                            heading()
                            contentDescription = "VCCAprender"
                        },
                        style = MaterialTheme.typography.headlineLarge,
                        color = AprenderColors.Navy
                    )
                    Text(
                        "Uma aventura, muitas descobertas!",
                        style = MaterialTheme.typography.titleMedium,
                        color = AprenderColors.Navy
                    )
                    Text(
                        "Joga, aprende e encontra todos os sorrisos.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = AprenderColors.Ink
                    )
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    "Vamos jogar?",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.semantics { heading() }.padding(top = 10.dp, bottom = 4.dp)
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                ElevatedCard(
                    onClick = onFingerMagicClick,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth().testTag("home-finger-magic")
                ) {
                    Row(
                        Modifier.fillMaxWidth().background(AprenderColors.Lavender).padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        BrandArtwork(
                            BrandSymbol.Star,
                            modifier = Modifier.size(64.dp),
                            description = null
                        )
                        Column(
                            Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                "Magia na ponta dos dedos",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                "${FingerMagicContent.questions.size} perguntas para descobrir sorrisos!",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Button(
                    onClick = onSettingsClick,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)
                ) { Text("Configurações") }
            }
        }
    }
}
