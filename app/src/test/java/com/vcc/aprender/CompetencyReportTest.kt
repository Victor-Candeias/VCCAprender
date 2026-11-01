package com.vcc.aprender

import com.vcc.aprender.data.progress.AnswerHistory
import com.vcc.aprender.domain.progress.CompetencyReportCalculator
import org.junit.Assert.*
import org.junit.Test

class CompetencyReportTest {
    private fun answer(id: Long, topic: String, correct: Boolean, at: Long) =
        AnswerHistory(id = id, profileId = 1, topicId = topic, correct = correct, answeredAt = at)

    @Test fun emptyHistoryProducesEmptyReport() {
        val result = CompetencyReportCalculator.calculate(emptyList())
        assertTrue(result.competencies.isEmpty())
        assertTrue(result.dailyHistory.isEmpty())
    }

    @Test fun groupsByCompetencyAndDay() {
        val result = CompetencyReportCalculator.calculate(listOf(
            answer(1, "ANIMALS", true, 1000),
            answer(2, "ANIMALS", false, 2000),
            answer(3, "WORDS", true, 86_401_000)
        ))
        val animals = result.competencies.first { it.topicId == "ANIMALS" }
        assertEquals(2, animals.total)
        assertEquals(50, animals.accuracy)
        assertEquals(2, result.dailyHistory.size)
        assertEquals(3, result.recentAnswers.size)
        assertEquals(3L, result.recentAnswers.first().id)
    }

    @Test fun recentHistoryIsLimitedToThirty() {
        val answers = (1L..40L).map { answer(it, "WORDS", true, it * 1000) }
        val result = CompetencyReportCalculator.calculate(answers)
        assertEquals(30, result.recentAnswers.size)
        assertEquals(40L, result.recentAnswers.first().id)
    }
}
