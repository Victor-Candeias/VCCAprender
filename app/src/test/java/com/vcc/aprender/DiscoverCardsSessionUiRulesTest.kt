package com.vcc.aprender

import com.vcc.aprender.data.cards.OfflineCardCatalog
import com.vcc.aprender.domain.cards.DiscoverCardSession
import org.junit.Assert.*
import org.junit.Test

class DiscoverCardsSessionUiRulesTest {
    @Test fun mustAnswerBeforeNextWhenUsingUiFlow() {
        val session = DiscoverCardSession(OfflineCardCatalog.cards.take(2))
        assertNull(session.answer)
        val answer = session.submit(session.current.correctOptionId)
        assertTrue(answer.correct)
        assertEquals(answer, session.answer)
        assertTrue(session.next())
        assertNull(session.answer)
    }

    @Test fun restartReturnsToFirstCard() {
        val session = DiscoverCardSession(OfflineCardCatalog.cards.take(2))
        session.submit(session.current.correctOptionId)
        session.next()
        session.reset()
        assertEquals(0, session.index)
        assertNull(session.answer)
    }
}
