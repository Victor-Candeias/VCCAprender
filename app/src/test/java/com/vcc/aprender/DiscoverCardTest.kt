package com.vcc.aprender

import com.vcc.aprender.data.cards.OfflineCardCatalog
import com.vcc.aprender.domain.cards.CardOption
import com.vcc.aprender.domain.cards.DiscoverCard
import com.vcc.aprender.domain.cards.DiscoverCardSession
import com.vcc.aprender.domain.cards.evaluate
import com.vcc.aprender.domain.model.LearningTopic
import org.junit.Assert.*
import org.junit.Test

class DiscoverCardTest {
    @Test fun catalogHasUniqueIdsAndEveryTopic() {
        val cards = OfflineCardCatalog.cards
        assertEquals(cards.size, cards.map { it.id }.distinct().size)
        LearningTopic.entries.forEach { assertTrue(OfflineCardCatalog.byTopic(it).isNotEmpty()) }
    }

    @Test fun evaluationRecognizesCorrectAndIncorrectAnswers() {
        val card = OfflineCardCatalog.cards.first()
        assertTrue(card.evaluate(card.correctOptionId).correct)
        val wrong = card.options.first { it.id != card.correctOptionId }
        assertFalse(card.evaluate(wrong.id).correct)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidCorrectOptionIsRejected() {
        DiscoverCard(
            id = "invalid", topic = LearningTopic.LETTERS, question = "Teste",
            options = listOf(CardOption("a", "A"), CardOption("b", "B")),
            correctOptionId = "missing"
        )
    }

    @Test fun sessionLocksFirstAnswerUntilNextCard() {
        val session = DiscoverCardSession(OfflineCardCatalog.cards.take(2))
        val first = session.current
        val wrong = first.options.first { it.id != first.correctOptionId }.id
        assertFalse(session.submit(wrong).correct)
        assertFalse(session.submit(first.correctOptionId).correct)
        assertTrue(session.next())
        assertNull(session.answer)
        assertFalse(session.next())
    }
}
