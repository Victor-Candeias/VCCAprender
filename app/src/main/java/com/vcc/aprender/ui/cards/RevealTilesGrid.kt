package com.vcc.aprender.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vcc.aprender.domain.cards.RevealTiles

@Composable
fun RevealTilesGrid(
    illustration: String,
    tiles: RevealTiles,
    enabled: Boolean,
    onReveal: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(
            illustration,
            modifier = Modifier.fillMaxWidth().padding(18.dp),
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            repeat(tiles.rows) { row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    repeat(tiles.columns) { col ->
                        val index = row * tiles.columns + col
                        val hidden = index !in tiles.revealed
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(2.dp)
                                .then(
                                    if (hidden) Modifier
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .semantics {
                                            contentDescription = "Revelar quadrado ${index + 1} de ${tiles.total}"
                                        }
                                        .clickable(enabled = enabled) { onReveal(index) }
                                    else Modifier
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (hidden) Text("?", style = MaterialTheme.typography.headlineMedium)
                        }
                    }
                }
            }
        }
    }
}
