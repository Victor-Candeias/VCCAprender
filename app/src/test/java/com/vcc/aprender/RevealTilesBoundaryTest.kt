package com.vcc.aprender

import com.vcc.aprender.domain.cards.RevealTiles
import org.junit.Assert.*
import org.junit.Test

class RevealTilesBoundaryTest {
    @Test fun revealingSameTileTwiceIsIdempotent() {
        val first = RevealTiles().reveal(0)
        assertEquals(first, first.reveal(0))
        assertEquals(8, first.remaining)
    }

    @Test fun revealAllWorksOnCustomGrid() {
        val tiles = RevealTiles(columns = 2, rows = 4).revealAll()
        assertEquals(8, tiles.total)
        assertEquals(0, tiles.remaining)
        assertTrue(tiles.allRevealed)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidGridSizeIsRejected() { RevealTiles(columns = 1) }

    @Test(expected = IllegalArgumentException::class)
    fun invalidInitialRevealedTileIsRejected() { RevealTiles(revealed = setOf(9)) }

    @Test(expected = IllegalArgumentException::class)
    fun outOfBoundsTileIsRejected() { RevealTiles().reveal(-1) }
}
