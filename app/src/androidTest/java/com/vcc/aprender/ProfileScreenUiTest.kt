package com.vcc.aprender

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.ui.profiles.ProfileScreen
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileScreenUiTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun addProfileOpensDialogAndCancelReturnsToList() {
        composeRule.setContent {
            ProfileScreen(
                profiles = emptyList(),
                error = null,
                onClearError = {},
                onSave = { _, _, _, _ -> },
                onSelect = {},
                onDelete = {}
            )
        }
        composeRule.onNodeWithText("Quem vai aprender?").assertIsDisplayed()
        composeRule.onNodeWithText("➕ Adicionar criança").performClick()
        composeRule.onNodeWithText("Novo perfil").assertIsDisplayed()
        composeRule.onNodeWithText("Cancelar").performClick()
        composeRule.onNodeWithText("➕ Adicionar criança").assertIsDisplayed()
    }

    @Test fun errorDialogCanBeDismissed() {
        var cleared = false
        composeRule.setContent {
            ProfileScreen(
                profiles = emptyList(),
                error = "Erro de teste",
                onClearError = { cleared = true },
                onSave = { _, _, _, _ -> },
                onSelect = {},
                onDelete = {}
            )
        }
        composeRule.onNodeWithText("Erro de teste").assertIsDisplayed()
        composeRule.onNodeWithText("OK").performClick()
        composeRule.runOnIdle { assertTrue(cleared) }
    }
}
