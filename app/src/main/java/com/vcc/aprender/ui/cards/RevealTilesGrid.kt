package com.vcc.aprender.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.vcc.aprender.domain.cards.RevealTiles
import com.vcc.aprender.ui.components.LearningArtwork
import com.vcc.aprender.ui.theme.AprenderColors

@Composable
fun RevealTilesGrid(
    illustration: String,
    tiles: RevealTiles,
    enabled: Boolean,
    onReveal: (Int) -> Unit
) {
    // Squares are at least ~100dp in a 320dp game pane (3x3 default grid).
    Box(modifier = Modifier.widthIn(max = 360.dp).fillMaxWidth(),
        contentAlignment = Alignment.Center) {
        LearningArtwork(
            illustration,
            modifier = Modifier.matchParentSize().padding(18.dp),
            decorative = tiles.remaining > 0
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            repeat(tiles.rows) { row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    repeat(tiles.columns) { col ->
                        val index = row * tiles.columns + col
                        val hidden = index !in tiles.revealed
                        var hasFocus by remember(index) { mutableStateOf(false) }
                        Box(
                            modifier = Modifier.weight(1f).aspectRatio(1f).padding(2.dp)
                                .then(
                                    if (hidden) Modifier
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .onFocusChanged { hasFocus = it.hasFocus }
                                        .border(if (hasFocus) 3.dp else 1.dp,
                                            if (hasFocus) AprenderColors.Blue else AprenderColors.Navy)
                                        .clickable(
                                            enabled = enabled,
                                            role = Role.Button,
                                            onClick = { onReveal(index) }
                                        )
                                        .semantics {
                                            contentDescription = "Revelar quadrado ${index + 1} de ${tiles.total}"
                                            stateDescription = "Por revelar"
                                        }
                                    else Modifier.clearAndSetSemantics { }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (hidden) Text("?", modifier = Modifier.clearAndSetSemantics { },
                                style = MaterialTheme.typography.headlineMedium)
                        }
                    }
                }
            }
        }
    }
}
