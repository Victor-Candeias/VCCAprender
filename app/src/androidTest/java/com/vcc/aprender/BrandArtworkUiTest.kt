package com.vcc.aprender

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.ui.components.BrandArtwork
import com.vcc.aprender.ui.components.BrandSymbol
import com.vcc.aprender.ui.components.avatarSymbol
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BrandArtworkUiTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun everySymbolHasAccessibleLabel() {
        composeRule.setContent {
            Column {
                BrandSymbol.entries.forEach { symbol ->
                    BrandArtwork(symbol, description = "Ícone ${symbol.name}")
                }
            }
        }
        BrandSymbol.entries.forEach { symbol ->
            composeRule.onNodeWithContentDescription("Ícone ${symbol.name}").assertExists()
        }
    }

    @Test fun savedEmojiAvatarsRemainCompatible() {
        assertEquals(BrandSymbol.Bear, avatarSymbol("🐻"))
        assertEquals(BrandSymbol.Fox, avatarSymbol("🦊"))
        assertEquals(BrandSymbol.Panda, avatarSymbol("🐼"))
        assertEquals(BrandSymbol.Star, avatarSymbol("🌟"))
    }
}
