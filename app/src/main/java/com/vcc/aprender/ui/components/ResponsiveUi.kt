package com.vcc.aprender.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

/**
 * Based on the space actually available to the composable, NOT orientation or device type.
 * The height constraint prevents two unusably short panes on landscape phones.
 */
object ResponsiveRules {
    fun useTwoPanes(widthDp: Float, heightDp: Float, minimumWidthDp: Float = 840f): Boolean =
        widthDp >= minimumWidthDp && heightDp >= 360f

    fun columnsFor(widthDp: Float, minimumCardDp: Float = 290f): Int =
        when {
            widthDp < minimumCardDp * 2 + 12f -> 1
            widthDp < minimumCardDp * 3 + 24f -> 2
            else -> 3
        }
}

/**
 * Single scrolling column on a phone or short viewport.
 * On tablets: two independently scrolling panes, so questions and answers remain reachable
 * with font scaling or TalkBack enabled.
 *
 * Call this as a full-screen destination (or with bounded height). Never nest it inside
 * an ancestor verticalScroll/LazyColumn.
 */
@Composable
fun AdaptiveTwoPaneScreen(
    primary: @Composable () -> Unit,
    secondary: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    minimumWidthDp: Float = 840f
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        val twoPanes = ResponsiveRules.useTwoPanes(maxWidth.value, maxHeight.value, minimumWidthDp)
        if (twoPanes) {
            Row(
                modifier = Modifier.fillMaxSize().widthIn(max = 1200.dp).padding(20.dp)
                    .testTag("va030-two-pane"),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f).fillMaxHeight().verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) { primary() }
                Column(
                    modifier = Modifier.weight(1f).fillMaxHeight().verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) { secondary() }
            }
        } else {
            Column(
                modifier = Modifier.widthIn(max = 680.dp).fillMaxWidth().fillMaxHeight()
                    .verticalScroll(rememberScrollState()).padding(20.dp)
                    .testTag("va030-stacked"),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                primary()
                secondary()
            }
        }
    }
}
