package com.vcc.aprender

import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.ui.components.GameAnswerButton
import com.vcc.aprender.ui.components.GameFeedback
import com.vcc.aprender.ui.components.GameProgress
import com.vcc.aprender.ui.components.FamilyHeader
import com.vcc.aprender.ui.components.FamilyProgress
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameDesignSystemUiTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun gameAnswerCardInvokesCallback() {
        var clicks = 0
        composeRule.setContent {
            GameAnswerButton(text = "Gato", prefix = "A)", enabled = true, onClick = { clicks++ })
        }
        composeRule.onNodeWithContentDescription("A) Gato").performClick()
        composeRule.runOnIdle { assertEquals(1, clicks) }
    }

    @Test fun disabledGameAnswerCardDoesNotInvokeCallback() {
        var clicks = 0
        composeRule.setContent {
            GameAnswerButton(text = "Cão", enabled = false, onClick = { clicks++ })
        }
        composeRule.onNodeWithContentDescription("Cão").assertIsNotEnabled()
        composeRule.runOnIdle { assertEquals(0, clicks) }
    }

    @Test fun progressAndFeedbackShowReadableInformation() {
        composeRule.setContent {
            androidx.compose.foundation.layout.Column {
                GameProgress(current = 2, total = 4, label = "Cartão")
                GameFeedback(message = "Muito bem!", positive = true)
                FamilyProgress(value = 75, label = "Acertos")
            }
        }
        composeRule.onNodeWithText("Cartão 2 de 4").assertIsDisplayed()
        composeRule.onNodeWithText("Muito bem!").assertExists()
        composeRule.onNodeWithText("Acertos: 75%").assertExists()
    }

    @Test fun familyHeaderDisplaysTitleAndSubtitle() {
        composeRule.setContent {
            FamilyHeader("📊", "Evolução", "Descobre o teu progresso")
        }
        composeRule.onNodeWithText("Evolução").assertIsDisplayed()
        composeRule.onNodeWithText("Descobre o teu progresso").assertExists()
    }
}
