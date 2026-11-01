package com.vcc.aprender

import com.vcc.aprender.data.cards.OfflineCardCatalog
import com.vcc.aprender.domain.cards.AnswerFeedbackFactory
import com.vcc.aprender.domain.cards.CardAnswer
import com.vcc.aprender.domain.cards.FeedbackKind
import com.vcc.aprender.domain.cards.evaluate
import org.junit.Assert.*
import org.junit.Test

class FeedbackBoundaryTest {
    private val card = OfflineCardCatalog.cards.first()

    @Test fun correctAnswerProducesPositiveFeedback() {
        val feedback = AnswerFeedbackFactory.from(card, card.evaluate(card.correctOptionId))
        assertEquals(FeedbackKind.CORRECT, feedback.kind)
        assertTrue(feedback.title.isNotBlank())
    }

    @Test fun incorrectAnswerIncludesCorrectOption() {
        val wrong = card.options.first { it.id != card.correctOptionId }
        val feedback = AnswerFeedbackFactory.from(card, card.evaluate(wrong.id))
        assertEquals(FeedbackKind.INCORRECT, feedback.kind)
        assertTrue(feedback.message.contains(card.options.first { it.id == card.correctOptionId }.label))
    }

    @Test(expected = IllegalArgumentException::class)
    fun feedbackRejectsDifferentCard() {
        AnswerFeedbackFactory.from(card, CardAnswer("other", card.correctOptionId, true))
    }

    @Test(expected = IllegalArgumentException::class)
    fun feedbackRejectsInconsistentCorrectFlag() {
        AnswerFeedbackFactory.from(card, CardAnswer(card.id, card.correctOptionId, false))
    }
}
