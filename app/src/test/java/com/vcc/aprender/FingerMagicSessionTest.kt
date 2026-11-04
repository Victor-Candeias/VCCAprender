package com.vcc.aprender

import com.vcc.aprender.domain.magic.FingerMagicContent
import com.vcc.aprender.domain.magic.FingerMagicSession
import com.vcc.aprender.domain.magic.MagicCategory
import org.junit.Assert.*
import org.junit.Test

class FingerMagicSessionTest {
    @Test fun allQuestionsHaveThreeDistinctImagesAndOneCorrectAnswer() {
        val questions = FingerMagicContent.questions
        assertTrue(questions.none { it.prompt.contains("futebol", ignoreCase = true) || it.prompt.contains("marca", ignoreCase = true) || it.prompt.contains("automóvel", ignoreCase = true) })
        assertEquals(86, questions.size)
        assertEquals(86, questions.map { it.id }.distinct().size)
        questions.forEach { question ->
            assertEquals(3, question.options.distinct().size)
            assertTrue(question.correctIndex in 0..2)
            assertFalse(question.prompt.isBlank())
            assertFalse(question.explanation.isBlank())
        }
        assertEquals(
            (1..111).filter { it != 18 && it !in 64..87 }
                .map { "magic-" + it.toString().padStart(2, '0') }.toSet(),
            questions.map { it.id }.toSet()
        )
        assertEquals(36, questions.count { it.category != MagicCategory.CLASSIC })
        val expansion = questions.drop(50)
        assertEquals(12, expansion.count { it.category == MagicCategory.SPACE })
        assertEquals(12, expansion.count { it.category == MagicCategory.MATH })
        assertEquals(12, expansion.count { it.category == MagicCategory.GENERAL })
        // The smile must occur in every position across the deck.
        assertEquals(setOf(0, 1, 2), questions.map { it.correctIndex }.toSet())
    }

    @Test fun wrongAnswerEndsQuestionImmediatelyAndBlocksFurtherAttempts() {
        val initial = FingerMagicSession()
        val correct = initial.question.correctIndex
        val wrong = (correct + 1) % 3
        val answered = initial.reveal(wrong)
        assertFalse(answered.solved)
        assertTrue(answered.answered)
        assertEquals(setOf(wrong), answered.attempted)
        assertTrue(answered.canAdvance)
        assertFalse(answered.canReveal(correct))
        assertEquals(answered, answered.reveal(correct))
        val next = answered.next()
        assertEquals(1, next.index)
        assertFalse(next.answered)
    }

    @Test fun correctAnswerStaysSolvedAndOnlyExplicitNextChangesQuestion() {
        val initial = FingerMagicSession()
        val correct = initial.question.correctIndex
        val solved = initial.reveal(correct)
        assertTrue(solved.solved)
        assertTrue(solved.answered)
        assertTrue(solved.canAdvance)
        assertEquals(initial.index, solved.index)
        assertEquals(solved, solved.reveal((correct + 1) % 3))
        val next = solved.next()
        assertEquals(1, next.index)
        assertFalse(next.solved)
        assertFalse(next.answered)
        assertTrue(next.attempted.isEmpty())
    }

    @Test fun allQuestionsNeedOneAttemptBeforeFinishing() {
        var session = FingerMagicSession()
        repeat(FingerMagicContent.questions.size) { index ->
            assertEquals(index, session.index)
            assertFalse(session.completed)
            assertEquals(session, session.next())
            val wrong = (session.question.correctIndex + 1) % 3
            session = session.reveal(wrong)
            assertTrue(session.canAdvance)
            session = session.next()
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
