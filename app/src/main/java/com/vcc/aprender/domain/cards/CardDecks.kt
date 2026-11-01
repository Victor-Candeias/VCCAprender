package com.vcc.aprender.domain.cards

import com.vcc.aprender.domain.model.LearningTopic

data class DeckFilter(
    val topic: LearningTopic? = null,
    val difficulty: CardDifficulty? = null
)

data class CardDeck(
    val id: String,
    val title: String,
    val cards: List<DiscoverCard>
) {
    init {
        require(id.isNotBlank())
        require(title.isNotBlank())
        require(cards.isNotEmpty())
        require(cards.map { it.id }.distinct().size == cards.size)
    }
}

object CardDecks {
    fun filter(cards: List<DiscoverCard>, filter: DeckFilter): List<DiscoverCard> =
        cards.filter { card ->
            (filter.topic == null || card.topic == filter.topic) &&
                (filter.difficulty == null || card.difficulty == filter.difficulty)
        }

    fun build(cards: List<DiscoverCard>, filter: DeckFilter): CardDeck? {
        val selected = filter(cards, filter)
        if (selected.isEmpty()) return null
        val topic = filter.topic?.name ?: "ALL"
        val level = filter.difficulty?.name ?: "ALL"
        return CardDeck("$topic-$level", "Baralho $topic / $level", selected)
    }
}
