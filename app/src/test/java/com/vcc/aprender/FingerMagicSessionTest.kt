package com.vcc.aprender

import com.vcc.aprender.domain.magic.FingerMagicContent
import com.vcc.aprender.domain.magic.FingerMagicSession
import com.vcc.aprender.domain.magic.MagicCategory
import org.junit.Assert.*
import org.junit.Test

class FingerMagicSessionTest {
    @Test fun allQuestionsHaveThreeDistinctImagesAndOneCorrectAnswer() {
        val questions = FingerMagicContent.questions
        assertEquals(111, questions.size)
        assertEquals(111, questions.map { it.id }.distinct().size)
        questions.forEach { question ->
            assertEquals(3, question.options.distinct().size)
            assertTrue(question.correctIndex in 0..2)
            assertFalse(question.prompt.isBlank())
            assertFalse(question.explanation.isBlank())
        }
        assertEquals(
            (1..111).map { "magic-" + it.toString().padStart(2, '0') }.toSet(),
            questions.map { it.id }.toSet()
        )
        assertEquals(60, questions.count { it.category != MagicCategory.CLASSIC })
        val expansion = questions.drop(51)
        assertEquals(12, expansion.count { it.category == MagicCategory.SPACE })
        assertEquals(12, expansion.count { it.category == MagicCategory.CAR })
        assertEquals(12, expansion.count { it.category == MagicCategory.FOOTBALL })
        assertEquals(12, expansion.count { it.category == MagicCategory.MATH })
        assertEquals(12, expansion.count { it.category == MagicCategory.GENERAL })
        // The smile must occur in every position across the deck.
        assertEquals(setOf(0, 1, 2), questions.map { it.correctIndex }.toSet())
    }

    @Test fun wrongAnswerCanHideAndBeTriedAgainWithoutLosingAttempts() {
        val initial = FingerMagicSession()
        val wrong = (initial.question.correctIndex + 1) % 3
        val revealed = initial.reveal(wrong)
        assertFalse(revealed.solved)
        assertTrue(wrong in revealed.visibleWrong)
        assertEquals(initial.index, revealed.next().index)
        assertEquals(revealed, revealed.reveal(wrong))
        val hidden = revealed.hideWrong(wrong)
        assertTrue(hidden.visibleWrong.isEmpty())
        assertTrue(hidden.canReveal(wrong))
        assertEquals(setOf(wrong), hidden.reveal(wrong).attempted)
    }

    @Test fun correctAnswerStaysVisibleAndOnlyExplicitNextChangesQuestion() {
        val initial = FingerMagicSession()
        val correct = initial.question.correctIndex
        val solved = initial.reveal(correct)
        assertTrue(solved.solved)
        assertEquals(initial.index, solved.index)
        assertEquals(solved, solved.reveal((correct + 1) % 3))
        assertTrue(solved.hideWrong(correct).solved)
        val next = solved.next()
        assertEquals(1, next.index)
        assertFalse(next.solved)
        assertTrue(next.attempted.isEmpty())
        assertTrue(next.visibleWrong.isEmpty())
    }

    @Test fun allQuestionsMustBeSolvedBeforeFinishing() {
        var session = FingerMagicSession()
        repeat(FingerMagicContent.questions.size) { index ->
            assertEquals(index, session.index)
            assertFalse(session.completed)
            assertEquals(session, session.next())
            session = session.reveal(session.question.correctIndex).next()
        }
        assertTrue(session.completed)
        assertEquals(session, session.next())
        assertFalse(session.canReveal(0))
    }

    @Test fun invalidAnswersNeverChangeTheSession() {
        val session = FingerMagicSession()
        assertEquals(session, session.reveal(-1))
        assertEquals(session, session.reveal(3))
    }
}
