package com.vcc.aprender.domain.cards

class DiscoverCardSession(cards: List<DiscoverCard>) {
    private val deck = cards.toList()
    init { require(deck.isNotEmpty()) { "A sessão precisa de cartões." } }

    var index: Int = 0
        private set
    var answer: CardAnswer? = null
        private set

    val current: DiscoverCard get() = deck[index]
    val size: Int get() = deck.size
    val isLast: Boolean get() = index == deck.lastIndex

    fun submit(optionId: String): CardAnswer {
        answer?.let { return it }
        return current.evaluate(optionId).also { answer = it }
    }

    fun next(): Boolean {
        if (isLast) return false
        index++
        answer = null
        return true
    }

    fun reset() {
        index = 0
        answer = null
    }
}
