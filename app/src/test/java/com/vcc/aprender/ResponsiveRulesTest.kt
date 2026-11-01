package com.vcc.aprender

import com.vcc.aprender.ui.components.ResponsiveRules
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ResponsiveRulesTest {
    @Test fun phonePortraitStaysStacked() {
        assertFalse(ResponsiveRules.useTwoPanes(widthDp = 360f, heightDp = 800f))
    }

    @Test fun shortLandscapePhoneStaysStackedEvenAtLargeWidth() {
        assertFalse(ResponsiveRules.useTwoPanes(widthDp = 900f, heightDp = 320f))
        assertFalse(ResponsiveRules.useTwoPanes(widthDp = 800f, heightDp = 360f))
    }

    @Test fun landscapeTabletShowsTwoPanes() {
        assertTrue(ResponsiveRules.useTwoPanes(widthDp = 1280f, heightDp = 800f))
        assertTrue(ResponsiveRules.useTwoPanes(widthDp = 900f, heightDp = 440f))
    }

    @Test fun parentalSettingsCanSplitSlightlyEarlier() {
        assertTrue(ResponsiveRules.useTwoPanes(760f, 620f, minimumWidthDp = 760f))
        assertFalse(ResponsiveRules.useTwoPanes(759f, 620f, minimumWidthDp = 760f))
        assertFalse(ResponsiveRules.useTwoPanes(800f, 300f, minimumWidthDp = 760f))
    }

    @Test fun adaptiveGridHasOneTwoOrThreeColumns() {
        assertEquals(1, ResponsiveRules.columnsFor(320f))
        assertEquals(1, ResponsiveRules.columnsFor(590f))
        assertEquals(2, ResponsiveRules.columnsFor(700f))
        assertEquals(3, ResponsiveRules.columnsFor(980f))
    }
}
