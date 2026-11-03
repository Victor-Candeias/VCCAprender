package com.vcc.aprender

import android.content.ComponentName
import com.vcc.aprender.plugins.InstalledGame
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.ui.home.HomeScreen
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenUiTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun onlyFingerMagicIsOfferedAsPlayableActivity() {
        var opened = false
        composeRule.setContent {
            HomeScreen(
                onFingerMagicClick = { opened = true },
                onSettingsClick = {}
            )
        }
        composeRule.onNodeWithText("VCCAprender").assertIsDisplayed()
        composeRule.onNodeWithText("Magia na ponta dos dedos").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithTag("home-finger-magic").performScrollTo().performClick()
        composeRule.runOnIdle { assertEquals(true, opened) }
    }

    @Test fun formerTopicActivitiesAreNotDisplayed() {
        composeRule.setContent {
            HomeScreen(onFingerMagicClick = {}, onSettingsClick = {})
        }
        listOf("Letras", "Números", "Cores", "Formas", "Animais", "Sons", "Palavras")
            .forEach { composeRule.onNodeWithText(it).assertDoesNotExist() }
    }


    @Test fun trustedAdditionalGameHasItsOwnClickableCard() {
        val game = InstalledGame(
            gameId = "com.vcc.aprender.sample.quiz",
            title = "Jogo exemplo: Descobre a cor",
            component = ComponentName("com.vcc.aprender.samplegame", "com.vcc.aprender.samplegame.SampleGameActivity")
        )
        var selected: InstalledGame? = null
        composeRule.setContent {
            HomeScreen(
                onFingerMagicClick = {},
                onSettingsClick = {},
                installedGames = listOf(game),
                onInstalledGameClick = { selected = it }
            )
        }
        composeRule.onNodeWithText("Magia na ponta dos dedos").assertExists()
        composeRule.onNodeWithText(game.title).assertExists()
        composeRule.onNodeWithTag("installed-game-${game.gameId}")
            .performScrollTo().performClick()
        composeRule.runOnIdle { assertEquals(game, selected) }
    }

    @Test fun settingsOptionRemainsAvailable() {
        var opened = false
        composeRule.setContent {
            HomeScreen(
                onFingerMagicClick = {},
                onSettingsClick = { opened = true }
            )
        }
        composeRule.onNodeWithText("Configurações").performScrollTo().performClick()
        composeRule.runOnIdle { assertEquals(true, opened) }
    }
}
