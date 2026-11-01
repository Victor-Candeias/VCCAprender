package com.vcc.aprender

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import org.junit.Assert.assertFalse
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.data.settings.ParentalSettings
import com.vcc.aprender.ui.parents.ParentalSettingsScreen
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ParentalSettingsUiTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun animationSwitchInvokesParentalCallback() {
        var changed: Boolean? = null
        composeRule.setContent {
            ParentalSettingsScreen(
                settings = ParentalSettings(animationsEnabled = true),
                onAudioChanged = {},
                onSpeechRateChanged = {},
                onAnimationsChanged = { changed = it },
                onBack = {}
            )
        }
        composeRule.onNodeWithText("Animações ativadas").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Animações").performClick()
        composeRule.runOnIdle { assertFalse(changed ?: true) }
    }

    @Test fun settingsScreenDisplaysCurrentValuesAndBackWorks() {
        var back = false
        composeRule.setContent {
            ParentalSettingsScreen(
                settings = ParentalSettings(audioEnabled = false),
                onAudioChanged = {},
                onSpeechRateChanged = {},
                onBack = { back = true }
            )
        }
        composeRule.onNodeWithText("Área parental").assertIsDisplayed()
        composeRule.onNodeWithText("Áudio desativado").assertIsDisplayed()
        composeRule.onNodeWithText("Voltar ao início").performClick()
        composeRule.runOnIdle { assertTrue(back) }
    }
}
