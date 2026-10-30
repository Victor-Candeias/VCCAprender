package com.vcc.aprender

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vcc.aprender.di.AppContainer
import com.vcc.aprender.ui.navigation.AprenderNavHost
import com.vcc.aprender.ui.theme.AprenderTheme

class MainActivity : ComponentActivity() {
    private val container by lazy { AppContainer() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AprenderTheme {
                AprenderNavHost(container)
            }
        }
    }
}
