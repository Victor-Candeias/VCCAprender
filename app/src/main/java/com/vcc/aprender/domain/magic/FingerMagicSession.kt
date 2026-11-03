package com.vcc.aprender.domain.magic

/**
 * One fully revealed answer per question.
 *
 * After a correct answer the smile is visible immediately. After a wrong
 * answer the wrong smile appears first; the ViewModel reveals the correct
 * smile shortly afterwards. Neither path allows a second attempt.
 */
data class FingerMagicSession(
    val questions: List<MagicQuestion> = FingerMagicContent.questions,
    val index: Int = 0,
    val attempted: Set<Int> = emptySet(),
    val visibleWrong: Set<Int> = emptySet(),
    val solved: Boolean = false,
    val completed: Boolean = false,
    val showCorrectAnswer: Boolean = false
) {
    init {
        require(questions.isNotEmpty() && index in questions.indices)
    }

    val question: MagicQuestion get() = questions[index]
    val answered: Boolean get() = attempted.isNotEmpty()
    val canAdvance: Boolean get() = answered && showCorrectAnswer && !completed

    fun canReveal(option: Int): Boolean =
        !completed && !answered && option in question.options.indices

    fun reveal(option: Int): FingerMagicSession {
        if (!canReveal(option)) return this
        val correct = option == question.correctIndex
        return copy(
            attempted = setOf(option),
            visibleWrong = if (correct) emptySet() else setOf(option),
            solved = correct,
            showCorrectAnswer = correct
        )
    }

    /** Called automatically after the wrong smile has been displayed. */
    fun revealCorrectAnswer(): FingerMagicSession =
        if (answered && !solved && !showCorrectAnswer && !completed) {
            copy(showCorrectAnswer = true)
        } else this

    fun next(): FingerMagicSession = when {
        !canAdvance -> this
        index == questions.lastIndex -> copy(completed = true)
        else -> FingerMagicSession(questions = questions, index = index + 1)
    }
}
