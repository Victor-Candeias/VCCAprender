package com.vcc.aprender

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.ui.components.GameAnswerButton
import com.vcc.aprender.ui.components.GameCompletionBanner
import com.vcc.aprender.ui.components.GameFeedback
import com.vcc.aprender.ui.components.GameProgress
import com.vcc.aprender.ui.components.LocalGameAnimationsEnabled
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameMotionUiTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun reducedMotionRetainsAccessibleAnswerFeedbackProgressAndCelebration() {
        var clicks = 0
        composeRule.setContent {
            CompositionLocalProvider(LocalGameAnimationsEnabled provides false) {
                Column {
                    GameProgress(current = 2, total = 5)
                    GameAnswerButton(text = "Gato", enabled = true, selected = true, onClick = { clicks++ })
                    GameFeedback(message = "Muito bem!", positive = true)
                    GameCompletionBanner(title = "Concluído!", message = "Joga novamente.")
                }
            }
        }
        composeRule.onNodeWithText("Exercício 2 de 5").assertExists()
        composeRule.onNodeWithContentDescription("Gato").performClick()
        composeRule.onNodeWithText("Muito bem!").assertExists()
        composeRule.onNodeWithText("Concluído!").assertExists()
        composeRule.onNodeWithText("Joga novamente.").assertExists()
        composeRule.runOnIdle { assertEquals(1, clicks) }
    }

    @Test fun incorrectAnswerShowsGentleTextWithoutCelebration() {
        composeRule.setContent {
            CompositionLocalProvider(LocalGameAnimationsEnabled provides false) {
                GameFeedback(message = "Boa tentativa!", positive = false)
            }
        }
        composeRule.onNodeWithText("Boa tentativa!").assertIsDisplayed()
        composeRule.onNodeWithText("Boa tentativa").assertExists()
        composeRule.onNodeWithText("Resposta correta").assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Continua a tentar").assertDoesNotExist()
    }
}
