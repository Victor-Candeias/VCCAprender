package com.vcc.aprender.domain.progress

import com.vcc.aprender.data.progress.AnswerHistory

data class CompetencyStats(
    val topicId: String,
    val correct: Int,
    val total: Int,
    val accuracy: Int,
    val lastAnsweredAt: Long
)

data class DailyStats(
    val dayStartUtc: Long,
    val correct: Int,
    val total: Int
)

data class CompetencyReport(
    val competencies: List<CompetencyStats>,
    val dailyHistory: List<DailyStats>,
    val recentAnswers: List<AnswerHistory>
)

object CompetencyReportCalculator {
    private const val DAY_MS = 86_400_000L

    fun calculate(answers: List<AnswerHistory>): CompetencyReport {
        val competencies = answers.groupBy { it.topicId }.map { (topic, values) ->
            val correct = values.count { it.correct }
            CompetencyStats(topic, correct, values.size,
                ProgressSummaryCalculator.percentage(correct, values.size),
                values.maxOf { it.answeredAt })
        }.sortedBy { it.topicId }

        val days = answers.groupBy { Math.floorDiv(it.answeredAt, DAY_MS) * DAY_MS }
            .map { (day, values) -> DailyStats(day, values.count { it.correct }, values.size) }
            .sortedByDescending { it.dayStartUtc }

        return CompetencyReport(
            competencies = competencies,
            dailyHistory = days,
            recentAnswers = answers.sortedWith(
                compareByDescending<AnswerHistory> { it.answeredAt }.thenByDescending { it.id }
            ).take(30)
        )
    }
}
