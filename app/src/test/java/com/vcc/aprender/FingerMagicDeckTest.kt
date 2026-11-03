package com.vcc.aprender

import com.vcc.aprender.domain.magic.FingerMagicContent
import com.vcc.aprender.domain.magic.FingerMagicDeck
import org.junit.Assert.*
import org.junit.Test
import kotlin.random.Random

class FingerMagicDeckTest {
    @Test fun sameSeedProducesExactlyTheSameOrder() {
        val a = FingerMagicDeck.newSession(Random(2026))
        val b = FingerMagicDeck.newSession(Random(2026))
        assertEquals(a.questions.map { it.id }, b.questions.map { it.id })
    }

    @Test fun shufflePreservesEveryQuestionExactlyOnce() {
        val canonical = FingerMagicContent.questions.map { it.id }
        val shuffled = FingerMagicDeck.newSession(Random(77)).questions.map { it.id }
        assertEquals(86, shuffled.size)
        assertEquals(shuffled.size, shuffled.distinct().size)
        assertEquals(canonical.toSet(), shuffled.toSet())
        assertNotEquals(canonical, shuffled)
        assertEquals(canonical, FingerMagicContent.questions.map { it.id })
    }

    @Test fun newGamesUsingTheSameRandomGeneratorGetDifferentOrders() {
        val seeded = Random(99)
        val first = FingerMagicDeck.newSession(seeded)
        val second = FingerMagicDeck.newSession(seeded)
        assertNotEquals(first.questions.map { it.id }, second.questions.map { it.id })
    }

    @Test fun advancingDoesNotReorderOrRepeatQuestions() {
        var session = FingerMagicDeck.newSession(Random(42))
        val expected = session.questions.map { it.id }
        for (position in expected.indices) {
            assertFalse(session.completed)
            assertEquals(position, session.index)
            assertEquals(expected[position], session.question.id)
            assertEquals(session, session.next()) // must solve to continue
            session = session.reveal(session.question.correctIndex).next()
            assertEquals(expected, session.questions.map { it.id })
        }
        assertTrue(session.completed)
        assertEquals(86, session.questions.size)
    }

    @Test fun correctAnswerAlwaysTravelsWithItsShuffledQuestion() {
        val expected = FingerMagicContent.questions.associateBy { it.id }
        val deck = FingerMagicDeck.newSession(Random(123))
        deck.questions.forEach { question ->
            assertEquals(expected[question.id]?.correctIndex, question.correctIndex)
            assertEquals(expected[question.id]?.options, question.options)
            assertEquals(expected[question.id]?.explanation, question.explanation)
        }
    }
}
