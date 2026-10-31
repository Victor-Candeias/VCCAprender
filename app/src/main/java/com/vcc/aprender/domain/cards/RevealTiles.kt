package com.vcc.aprender.domain.cards

data class RevealTiles(
    val columns: Int = 3,
    val rows: Int = 3,
    val revealed: Set<Int> = emptySet()
) {
    init {
        require(columns in 2..6 && rows in 2..6) { "Grelha inválida." }
        require(revealed.all { it in 0 until columns * rows }) { "Quadrado fora da grelha." }
    }

    val total: Int get() = columns * rows
    val remaining: Int get() = total - revealed.size
    val allRevealed: Boolean get() = remaining == 0

    fun reveal(index: Int): RevealTiles {
        require(index in 0 until total) { "Quadrado fora da grelha." }
        return copy(revealed = revealed + index)
    }

    fun revealAll(): RevealTiles = copy(revealed = (0 until total).toSet())
}
