package com.vcc.aprender.domain.magic

/**
 * One fully revealed answer per question. A correct choice shows the smile;
 * a wrong choice ends the attempt and leaves only the correct picture visible,
 * without showing either smile.
 */
data class FingerMagicSession(
    val questions: List<MagicQuestion> = FingerMagicContent.questions,
    val index: Int = 0,
    val attempted: Set<Int> = emptySet(),
    val solved: Boolean = false,
    val completed: Boolean = false
) {
    init {
        require(questions.isNotEmpty() && index in questions.indices)
    }

    val question: MagicQuestion get() = questions[index]
    val answered: Boolean get() = attempted.isNotEmpty()
    val canAdvance: Boolean get() = answered && !completed

    fun canReveal(option: Int): Boolean =
        !completed && !answered && option in question.options.indices

    fun reveal(option: Int): FingerMagicSession {
        if (!canReveal(option)) return this
        return copy(
            attempted = setOf(option),
            solved = option == question.correctIndex
        )
    }

    fun next(): FingerMagicSession = when {
        !canAdvance -> this
        index == questions.lastIndex -> copy(completed = true)
        else -> FingerMagicSession(questions = questions, index = index + 1)
    }
}
