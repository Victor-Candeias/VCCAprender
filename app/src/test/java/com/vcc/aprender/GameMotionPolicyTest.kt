package com.vcc.aprender

import com.vcc.aprender.ui.components.GameMotionPolicy
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GameMotionPolicyTest {
    @Test fun animationIsOnOnlyWhenParentAndSystemAllow() {
        assertTrue(GameMotionPolicy.enabled(parentalEnabled = true, systemAnimatorScale = 1f))
        assertTrue(GameMotionPolicy.enabled(parentalEnabled = true, systemAnimatorScale = 0.5f))
        assertTrue(GameMotionPolicy.enabled(parentalEnabled = true, systemAnimatorScale = 2f))
        assertFalse(GameMotionPolicy.enabled(parentalEnabled = false, systemAnimatorScale = 1f))
        assertFalse(GameMotionPolicy.enabled(parentalEnabled = true, systemAnimatorScale = 0f))
        assertFalse(GameMotionPolicy.enabled(parentalEnabled = false, systemAnimatorScale = 0f))
        assertFalse(GameMotionPolicy.enabled(parentalEnabled = true, systemAnimatorScale = Float.NaN))
    }
}
