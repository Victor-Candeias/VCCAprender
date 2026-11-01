package com.vcc.aprender

import com.vcc.aprender.data.cards.OfflineCardCatalog
import com.vcc.aprender.domain.cards.DiscoverCardSession
import org.junit.Assert.*
import org.junit.Test

class SessionBoundaryTest {
    @Test(expected = IllegalArgumentException::class)
    fun emptyDeckIsRejected() { DiscoverCardSession(emptyList()) }

    @Test fun singleCardCannotAdvanceAndResetClearsAnswer() {
        val card = OfflineCardCatalog.cards.first()
        val session = DiscoverCardSession(listOf(card))
        assertTrue(session.isLast)
        assertTrue(session.submit(card.correctOptionId).correct)
        assertFalse(session.next())
        assertEquals(0, session.index)
        session.reset()
        assertNull(session.answer)
        assertEquals(0, session.index)
    }

    @Test fun progressingThroughDeckResetsAnswer() {
        val deck = OfflineCardCatalog.cards.take(3)
        val session = DiscoverCardSession(deck)
        repeat(2) { index ->
            session.submit(session.current.correctOptionId)
            assertTrue(session.next())
            assertEquals(index + 1, session.index)
            assertNull(session.answer)
        }
        assertTrue(session.isLast)
        assertFalse(session.next())
        session.reset()
        assertEquals(deck.first().id, session.current.id)
    }
}
