package com.vcc.aprender

import org.junit.Assert.assertEquals
import org.junit.Test

class ProfileValidationTest {
    @Test fun ageLimits() {
        assertEquals(true, (3..8).contains(3))
        assertEquals(true, (3..8).contains(8))
        assertEquals(false, (3..8).contains(9))
    }
}
