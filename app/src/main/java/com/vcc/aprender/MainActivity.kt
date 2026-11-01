package com.vcc.aprender

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.SystemBarStyle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.vcc.aprender.di.AppContainer
import com.vcc.aprender.ui.navigation.AprenderNavHost
import com.vcc.aprender.ui.theme.AprenderTheme

class MainActivity : ComponentActivity() {
    private val container by lazy { AppContainer(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Must run before super.onCreate; splash exits naturally on first Compose draw.
        installSplashScreen()
        super.onCreate(savedInstanceState)
        // Android 15 draws behind system bars. Keep their icons readable on our light background.
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, getColor(R.color.brand_blue)),
            navigationBarStyle = SystemBarStyle.light(getColor(R.color.brand_cream), getColor(R.color.brand_blue))
        )
        setContent {
            AprenderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().testTag("app-window"),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Apply and consume the system/cutout/keyboard insets once, for every destination.
                    Box(modifier = Modifier.fillMaxSize().safeDrawingPadding().testTag("app-safe-content")) {
                        AprenderNavHost(container)
                    }
                }
            }
        }
    }
}
