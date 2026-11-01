package com.vcc.aprender.ui.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vcc.aprender.data.cards.OfflineCardCatalog
import com.vcc.aprender.domain.cards.DiscoverCard
import com.vcc.aprender.domain.cards.CardDecks
import com.vcc.aprender.domain.cards.CardDifficulty
import com.vcc.aprender.domain.cards.DeckFilter
import com.vcc.aprender.domain.model.LearningTopic

@Composable
fun DeckSelectionScreen(onStart: (DeckFilter) -> Unit, onBack: () -> Unit, cards: List<DiscoverCard> = OfflineCardCatalog.cards) {
    var topic by remember { mutableStateOf<LearningTopic?>(null) }
    var difficulty by remember { mutableStateOf<CardDifficulty?>(null) }
    val filter = DeckFilter(topic, difficulty)
    val count = CardDecks.filter(cards, filter).size
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Escolher baralho", style = MaterialTheme.typography.headlineMedium)
        Text("Categoria", style = MaterialTheme.typography.titleMedium)
        FilterChip(selected = topic == null, onClick = { topic = null }, label = { Text("Todas") })
        LearningTopic.entries.forEach { item ->
            FilterChip(selected = topic == item, onClick = { topic = item }, label = { Text(item.title) })
        }
        Text("Nível", style = MaterialTheme.typography.titleMedium)
        FilterChip(selected = difficulty == null, onClick = { difficulty = null }, label = { Text("Todos") })
        CardDifficulty.entries.forEach { level ->
            val label = when (level) {
                CardDifficulty.EASY -> "Fácil"
                CardDifficulty.MEDIUM -> "Médio"
                CardDifficulty.HARD -> "Difícil"
            }
            FilterChip(selected = difficulty == level, onClick = { difficulty = level }, label = { Text(label) })
        }
        Text("Cartões disponíveis: $count")
        if (count == 0) Text("Ainda não existem cartões para esta combinação.")
        Button(onClick = { onStart(filter) }, enabled = count > 0, modifier = Modifier.fillMaxWidth()) {
            Text("Começar jogo")
        }
        Button(onClick = onBack) { Text("Voltar ao início") }
    }
}
