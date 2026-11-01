package com.vcc.aprender

import com.vcc.aprender.data.cards.OfflineCardCatalog
import com.vcc.aprender.domain.cards.AnswerFeedbackFactory
import com.vcc.aprender.domain.cards.FeedbackKind
import com.vcc.aprender.domain.cards.DiscoverCardSession
import com.vcc.aprender.domain.cards.evaluate
import org.junit.Assert.*
import org.junit.Test

class AnswerFeedbackTest {
    private val card = OfflineCardCatalog.cards.first()

    @Test fun correctAnswerShowsPositiveFeedback() {
        val feedback = AnswerFeedbackFactory.from(card, card.evaluate(card.correctOptionId))
        assertEquals(FeedbackKind.CORRECT, feedback.kind)
        assertEquals("Muito bem!", feedback.title)
    }

    @Test fun wrongAnswerExplainsCorrectChoice() {
        val wrong = card.options.first { it.id != card.correctOptionId }
        val feedback = AnswerFeedbackFactory.from(card, card.evaluate(wrong.id))
        assertEquals(FeedbackKind.INCORRECT, feedback.kind)
        assertTrue(feedback.message.contains(card.options.first { it.id == card.correctOptionId }.label))
    }

    @Test fun duplicateAnswersDoNotReplaceFirstAnswer() {
        val session = DiscoverCardSession(listOf(card))
        val wrong = card.options.first { it.id != card.correctOptionId }.id
        assertEquals(session.submit(wrong), session.submit(card.correctOptionId))
    }

    @Test(expected = IllegalArgumentException::class)
    fun unknownOptionIsRejected() {
        card.evaluate("unknown")
    }
}
