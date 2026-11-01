package com.vcc.aprender

import com.vcc.aprender.data.cards.OfflineCardCatalog
import com.vcc.aprender.domain.cards.CardDecks
import com.vcc.aprender.domain.cards.CardDifficulty
import com.vcc.aprender.domain.cards.DeckFilter
import com.vcc.aprender.domain.model.LearningTopic
import org.junit.Assert.*
import org.junit.Test

class CardDecksTest {
    @Test fun allFilterKeepsEntireCatalog() {
        assertEquals(OfflineCardCatalog.cards.size, CardDecks.filter(OfflineCardCatalog.cards, DeckFilter()).size)
    }

    @Test fun topicFilterKeepsOnlyMatchingCards() {
        val selected = CardDecks.filter(OfflineCardCatalog.cards, DeckFilter(topic = LearningTopic.ANIMALS))
        assertTrue(selected.isNotEmpty())
        assertTrue(selected.all { it.topic == LearningTopic.ANIMALS })
    }

    @Test fun difficultyFilterKeepsOnlyMatchingCards() {
        val selected = CardDecks.filter(OfflineCardCatalog.cards, DeckFilter(difficulty = CardDifficulty.EASY))
        assertTrue(selected.all { it.difficulty == CardDifficulty.EASY })
    }

    @Test fun emptyCombinationReturnsNoDeck() {
        assertNull(CardDecks.build(emptyList(), DeckFilter()))
    }

    @Test fun deckHasUniqueCardsAndStableId() {
        val deck = CardDecks.build(OfflineCardCatalog.cards, DeckFilter())!!
        assertEquals("ALL-ALL", deck.id)
        assertEquals(deck.cards.size, deck.cards.map { it.id }.distinct().size)
    }
}
