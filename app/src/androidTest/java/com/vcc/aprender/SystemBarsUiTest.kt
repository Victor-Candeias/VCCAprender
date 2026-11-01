package com.vcc.aprender

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** Uses the real activity/window so Android 15's enforced edge-to-edge is covered. */
@RunWith(AndroidJUnit4::class)
class SystemBarsUiTest {
    @get:Rule val composeRule = createAndroidComposeRule<MainActivity>()

    @Test fun allDestinationsAreHostedInsideVisibleSystemBarsAndCutouts() {
        val window = composeRule.onNodeWithTag("app-window").assertIsDisplayed().fetchSemanticsNode().boundsInRoot
        val content = composeRule.onNodeWithTag("app-safe-content").assertIsDisplayed().fetchSemanticsNode().boundsInRoot
        val bars = composeRule.runOnIdle {
            requireNotNull(ViewCompat.getRootWindowInsets(composeRule.activity.window.decorView))
                .getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
        }
        assertTrue("O conteúdo deve ter área útil", content.width > 0 && content.height > 0)
        // Exact bounds also catch accidental application of the same insets twice.
        assertEquals("Margem superior", window.top + bars.top, content.top, 1f)
        assertEquals("Margem inferior", window.bottom - bars.bottom, content.bottom, 1f)
        assertEquals("Margem esquerda", window.left + bars.left, content.left, 1f)
        assertEquals("Margem direita", window.right - bars.right, content.right, 1f)
    }
}
