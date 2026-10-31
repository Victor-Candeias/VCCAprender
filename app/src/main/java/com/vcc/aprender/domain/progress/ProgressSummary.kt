package com.vcc.aprender.domain.progress

import com.vcc.aprender.data.progress.LearningProgress

data class TopicSummary(
    val topicId: String,
    val completedActivities: Int,
    val correctAnswers: Int,
    val totalAnswers: Int,
    val accuracyPercent: Int,
    val lastActivityAt: Long
)

data class ProgressSummary(
    val completedActivities: Int,
    val correctAnswers: Int,
    val totalAnswers: Int,
    val accuracyPercent: Int,
    val activeTopics: Int,
    val topics: List<TopicSummary>
)

object ProgressSummaryCalculator {
    fun calculate(progress: List<LearningProgress>): ProgressSummary {
        val topics = progress.map { item ->
            TopicSummary(
                topicId = item.topicId,
                completedActivities = item.completedActivities.coerceAtLeast(0),
                correctAnswers = item.correctAnswers.coerceIn(0, item.totalAnswers.coerceAtLeast(0)),
                totalAnswers = item.totalAnswers.coerceAtLeast(0),
                accuracyPercent = percentage(item.correctAnswers, item.totalAnswers),
                lastActivityAt = item.lastActivityAt
            )
        }
        val total = topics.sumOf { it.totalAnswers }
        val correct = topics.sumOf { it.correctAnswers }
        return ProgressSummary(
            completedActivities = topics.sumOf { it.completedActivities },
            correctAnswers = correct,
            totalAnswers = total,
            accuracyPercent = percentage(correct, total),
            activeTopics = topics.count { it.totalAnswers > 0 || it.completedActivities > 0 },
            topics = topics.sortedWith(compareByDescending<TopicSummary> { it.lastActivityAt }.thenBy { it.topicId })
        )
    }

    fun percentage(correct: Int, total: Int): Int {
        if (total <= 0) return 0
        return ((correct.coerceIn(0, total).toLong() * 100) / total).toInt()
    }
}
