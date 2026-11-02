package com.vcc.aprender

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.vcc.aprender.data.profile.ChildProfile
import com.vcc.aprender.ui.profiles.ProfileScreen
import org.junit.Assert.assertTrue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.data.settings.ParentalSettings
import com.vcc.aprender.ui.components.AdaptiveTwoPaneScreen
import com.vcc.aprender.ui.parents.ParentalSettingsScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResponsiveLayoutsUiTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun phonePortraitRendersStackedQuestionAndAnswer() {
        composeRule.setContent {
            Box(Modifier.requiredSize(width = 360.dp, height = 720.dp)) {
                AdaptiveTwoPaneScreen(
                    primary = { Text("Pergunta") },
                    secondary = { Button(onClick = {}) { Text("Resposta") } }
                )
            }
        }
        composeRule.onNodeWithTag("va030-stacked").assertExists()
        composeRule.onNodeWithText("Pergunta").assertExists()
        composeRule.onNodeWithText("Resposta").assertExists()
    }

    @Test fun shortLandscapeViewportKeepsSingleScrollableColumn() {
        composeRule.setContent {
            Box(Modifier.requiredSize(width = 900.dp, height = 320.dp)) {
                AdaptiveTwoPaneScreen(
                    primary = { Text("Pergunta") },
                    secondary = { Text("Resposta") }
                )
            }
        }
        composeRule.onNodeWithTag("va030-stacked").assertExists()
        composeRule.onNodeWithText("Resposta").assertExists()
    }

    @Test fun landscapeTabletRendersTwoIndependentPanes() {
        composeRule.setContent {
            Box(Modifier.requiredSize(width = 1100.dp, height = 720.dp)) {
                AdaptiveTwoPaneScreen(
                    primary = { Text("Pergunta") },
                    secondary = { Text("Resposta") }
                )
            }
        }
        composeRule.onNodeWithTag("va030-two-pane").assertExists()
        composeRule.onNodeWithText("Pergunta").assertExists()
        composeRule.onNodeWithText("Resposta").assertExists()
    }

    @Test fun tabletProfilesAreLaidOutInTwoColumns() {
        composeRule.setContent {
            Box(Modifier.requiredSize(width = 900.dp, height = 720.dp)) {
                ProfileScreen(
                    profiles = listOf(
                        ChildProfile(id = 1, name = "Ana", age = 5),
                        ChildProfile(id = 2, name = "Bruno", age = 6)
                    ),
                    error = null,
                    onClearError = {},
                    onSave = { _, _, _, _ -> },
                    onSelect = {},
                    onDelete = {}
                )
            }
        }
        val first = composeRule.onNodeWithText("Ana").fetchSemanticsNode().boundsInRoot.left
        val second = composeRule.onNodeWithText("Bruno").fetchSemanticsNode().boundsInRoot.left
        assertTrue("Os perfis devem ocupar colunas distintas no tablet", second > first)
    }

    @Test fun enlargedFontsKeepParentalSwitchAndReturnActionInTree() {
        composeRule.setContent {
            CompositionLocalProvider(LocalDensity provides Density(density = 1f, fontScale = 2f)) {
                Box(Modifier.requiredSize(width = 360.dp, height = 600.dp)) {
                    ParentalSettingsScreen(
                        settings = ParentalSettings(animationsEnabled = false),
                        onAudioChanged = {},
                        onSpeechRateChanged = {},
                        onAnimationsChanged = {},
                        onBack = {}
                    )
                }
            }
        }
        composeRule.onNodeWithTag("va030-stacked").assertExists()
        composeRule.onNodeWithText("Animações desativadas").assertExists()
        composeRule.onNodeWithText("Voltar ao início").assertExists()
    }

    @Test fun parentalControlsRemainPresentOnTablet() {
        composeRule.setContent {
            Box(Modifier.requiredSize(width = 900.dp, height = 680.dp)) {
                ParentalSettingsScreen(
                    settings = ParentalSettings(animationsEnabled = false),
                    onAudioChanged = {},
                    onSpeechRateChanged = {},
                    onAnimationsChanged = {},
                    onBack = {}
                )
            }
        }
        composeRule.onNodeWithTag("va030-two-pane").assertExists()
        composeRule.onNodeWithText("Área parental").assertExists()
        composeRule.onNodeWithText("Animações desativadas").assertExists()
    }
}
