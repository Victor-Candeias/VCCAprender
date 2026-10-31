package com.vcc.aprender

import com.vcc.aprender.domain.cards.RevealTiles
import org.junit.Assert.*
import org.junit.Test

class RevealTilesTest {
    @Test fun newGridStartsHidden() {
        val tiles = RevealTiles()
        assertEquals(9, tiles.total)
        assertEquals(9, tiles.remaining)
        assertFalse(tiles.allRevealed)
    }

    @Test fun repeatedTouchDoesNotCountTwice() {
        val tiles = RevealTiles().reveal(0).reveal(0)
        assertEquals(8, tiles.remaining)
    }

    @Test fun revealAllAndReset() {
        val revealed = RevealTiles().revealAll()
        assertTrue(revealed.allRevealed)
        assertEquals(0, revealed.remaining)
        assertEquals(9, RevealTiles().remaining)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidTileIsRejected() {
        RevealTiles().reveal(9)
    }
}
