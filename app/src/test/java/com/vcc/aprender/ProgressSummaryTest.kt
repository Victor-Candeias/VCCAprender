package com.vcc.aprender

import com.vcc.aprender.data.progress.LearningProgress
import com.vcc.aprender.domain.progress.ProgressSummaryCalculator
import org.junit.Assert.*
import org.junit.Test

class ProgressSummaryTest {
    @Test fun emptyProgressHasZeroTotals() {
        val result = ProgressSummaryCalculator.calculate(emptyList())
        assertEquals(0, result.totalAnswers)
        assertEquals(0, result.accuracyPercent)
        assertEquals(0, result.activeTopics)
    }

    @Test fun combinesMultipleTopics() {
        val result = ProgressSummaryCalculator.calculate(listOf(
            LearningProgress(profileId = 1, topicId = "ANIMALS", completedActivities = 2, correctAnswers = 3, totalAnswers = 4, lastActivityAt = 20),
            LearningProgress(profileId = 1, topicId = "WORDS", completedActivities = 1, correctAnswers = 1, totalAnswers = 2, lastActivityAt = 10)
        ))
        assertEquals(6, result.totalAnswers)
        assertEquals(4, result.correctAnswers)
        assertEquals(66, result.accuracyPercent)
        assertEquals(3, result.completedActivities)
        assertEquals(2, result.activeTopics)
        assertEquals("ANIMALS", result.topics.first().topicId)
    }

    @Test fun percentageHandlesZeroAndInvalidCounts() {
        assertEquals(0, ProgressSummaryCalculator.percentage(3, 0))
        assertEquals(100, ProgressSummaryCalculator.percentage(20, 10))
        assertEquals(0, ProgressSummaryCalculator.percentage(-1, 10))
    }
}
