package com.vcc.aprender.domain.magic

/** Only a fully revealed square counts as an attempt. Each option is recorded once per question. */
data class FingerMagicSession(
    val questions: List<MagicQuestion> = FingerMagicContent.questions,
    val index: Int = 0,
    val attempted: Set<Int> = emptySet(),
    val visibleWrong: Set<Int> = emptySet(),
    val solved: Boolean = false,
    val completed: Boolean = false
) {
    init {
        require(questions.isNotEmpty() && index in questions.indices)
    }

    val question: MagicQuestion get() = questions[index]

    fun canReveal(option: Int): Boolean = !completed && !solved &&
        option in question.options.indices && option !in visibleWrong

    fun reveal(option: Int): FingerMagicSession {
        if (!canReveal(option)) return this
        val correct = option == question.correctIndex
        return copy(
            attempted = attempted + option,
            visibleWrong = if (correct) visibleWrong else visibleWrong + option,
            solved = correct
        )
    }

    fun hideWrong(option: Int): FingerMagicSession = copy(visibleWrong = visibleWrong - option)

    fun next(): FingerMagicSession = when {
        !solved || completed -> this
        index == questions.lastIndex -> copy(completed = true)
        else -> FingerMagicSession(questions, index + 1)
    }
}
