package com.vcc.aprender

import androidx.compose.ui.test.assertDoesNotExist
import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.ui.home.SettingsScreen
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsScreenUiTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun onlyGameRelatedSettingsAreDisplayed() {
        composeRule.setContent {
            SettingsScreen(onProfiles = {}, onProgress = {}, onParents = {}, onBack = {})
        }
        listOf(
            "Mudar de perfil", "Ver progresso", "Área parental e preferências",
            "Voltar ao início"
        ).forEach { composeRule.onNodeWithText(it).assertExists() }
        listOf(
            "Leitura e reconhecimento de palavras",
            "Descobre a Resposta A/B/C",
            "Editor de atividades"
        ).forEach { composeRule.onNodeWithText(it).assertDoesNotExist() }
    }

    @Test fun profilesProgressAndParentalPreferencesRemainAccessible() {
        var profiles = false
        var progress = false
        var parents = false
        var back = false
        composeRule.setContent {
            SettingsScreen(
                onProfiles = { profiles = true },
                onProgress = { progress = true },
                onParents = { parents = true },
                onBack = { back = true }
            )
        }
        composeRule.onNodeWithText("Mudar de perfil").performScrollTo().performClick()
        composeRule.onNodeWithText("Ver progresso").performScrollTo().performClick()
        composeRule.onNodeWithText("Área parental e preferências").performScrollTo().performClick()
        composeRule.onNodeWithText("Voltar ao início").performScrollTo().performClick()
        composeRule.runOnIdle {
            assertTrue(profiles)
            assertTrue(progress)
            assertTrue(parents)
            assertTrue(back)
        }
    }
}
