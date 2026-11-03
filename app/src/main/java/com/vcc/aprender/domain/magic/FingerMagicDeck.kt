package com.vcc.aprender.domain.magic

import kotlin.random.Random

/**
 * An ordered snapshot is created once per game, not by Compose on recomposition.
 * The canonical question list never changes, and every question appears exactly once.
 * Random can be injected in tests for reproducible permutations.
 */
object FingerMagicDeck {
    fun shuffledQuestions(random: Random = Random.Default): List<MagicQuestion> =
        FingerMagicContent.questions.shuffled(random)

    fun newSession(random: Random = Random.Default): FingerMagicSession =
        FingerMagicSession(questions = shuffledQuestions(random))
}
