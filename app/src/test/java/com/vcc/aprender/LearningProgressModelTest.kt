package com.vcc.aprender

import com.vcc.aprender.data.progress.LearningProgress
import org.junit.Assert.assertEquals
import org.junit.Test

class LearningProgressModelTest {
    @Test fun initialProgressIsZero() {
        val progress = LearningProgress(profileId = 1, topicId = "LETTERS")
        assertEquals(0, progress.completedActivities)
        assertEquals(0, progress.correctAnswers)
        assertEquals(0, progress.totalAnswers)
    }

    @Test fun progressCanBeCopiedForNewAnswer() {
        val initial = LearningProgress(profileId = 1, topicId = "LETTERS")
        val updated = initial.copy(correctAnswers = 1, totalAnswers = 1)
        assertEquals(1, updated.correctAnswers)
        assertEquals(1, updated.totalAnswers)
        assertEquals(initial.profileId, updated.profileId)
    }
}
