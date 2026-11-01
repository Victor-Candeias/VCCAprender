package com.vcc.aprender.ui.components

import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

/** Motion only plays when allowed by BOTH the parent and Android system settings. */
object GameMotionPolicy {
    fun enabled(parentalEnabled: Boolean, systemAnimatorScale: Float): Boolean =
        parentalEnabled && systemAnimatorScale > 0f
}

val LocalGameAnimationsEnabled = compositionLocalOf { true }

private fun animatorScale(context: Context): Float = try {
    Settings.Global.getFloat(context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f)
} catch (_: SecurityException) {
    1f
}

@Composable
fun GameMotionProvider(parentalEnabled: Boolean, content: @Composable () -> Unit) {
    val context = LocalContext.current
    var systemScale by remember(context) { mutableFloatStateOf(animatorScale(context)) }
    DisposableEffect(context) {
        val uri = Settings.Global.getUriFor(Settings.Global.ANIMATOR_DURATION_SCALE)
        val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                systemScale = animatorScale(context)
            }
        }
        context.contentResolver.registerContentObserver(uri, false, observer)
        systemScale = animatorScale(context)
        onDispose { context.contentResolver.unregisterContentObserver(observer) }
    }
    CompositionLocalProvider(
        LocalGameAnimationsEnabled provides GameMotionPolicy.enabled(parentalEnabled, systemScale),
        content = content
    )
}

/** AnimatedContent targets the actual question index so old and new questions keep distinct keys. */
@Composable
fun <T> GameQuestionTransition(
    target: T,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    val animate = LocalGameAnimationsEnabled.current
    AnimatedContent(
        targetState = target,
        modifier = modifier,
        transitionSpec = {
            if (animate) {
                (fadeIn(tween(220)) + slideInHorizontally(tween(220)) { it / 7 }) togetherWith
                    (fadeOut(tween(160)) + slideOutHorizontally(tween(160)) { -it / 7 })
            } else {
                EnterTransition.None togetherWith ExitTransition.None
            }
        },
        label = "Mudança de pergunta"
    ) { visibleTarget ->
        content(visibleTarget)
    }
}
