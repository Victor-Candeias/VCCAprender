package com.vcc.aprender.samplegame

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.vcc.aprender.pluginsdk.GameProtocol

/**
 * An independently installable APK using the public Game Plugin SDK v1.
 * No profiles, names, files or database access are shared with this game.
 */
class SampleGameActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // The signature permission limits callers, but the plugin also checks
        // the actual for-result caller and matching release/debug certificate.
        val hostPackage = "com.vcc.aprender"
        if (callingPackage != hostPackage ||
            packageManager.checkSignatures(hostPackage, packageName) != PackageManager.SIGNATURE_MATCH
        ) {
            setResult(RESULT_CANCELED)
            finish()
            return
        }
        val request = GameProtocol.readRequest(intent)
        if (request == null || request.gameId != "com.vcc.aprender.sample.quiz") {
            setResult(RESULT_CANCELED)
            finish()
            return
        }

        val density = resources.displayMetrics.density
        fun dp(value: Int) = (value * density).toInt()
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(dp(24), dp(64), dp(24), dp(24))
        }
        val title = TextView(this).apply {
            text = "Jogo Exemplo VCC"
            textSize = 26f
            gravity = Gravity.CENTER
        }
        val question = TextView(this).apply {
            text = "Que cor tem normalmente o céu num dia limpo?"
            textSize = 20f
            gravity = Gravity.CENTER
            setPadding(0, dp(20), 0, dp(16))
        }
        layout.addView(title, ViewGroup.LayoutParams(-1, -2))
        layout.addView(question, ViewGroup.LayoutParams(-1, -2))

        listOf("Verde", "Azul", "Vermelho").forEachIndexed { index, option ->
            val button = Button(this).apply {
                text = option
                contentDescription = "Resposta ${'A' + index}: $option"
                setOnClickListener {
                    setResult(
                        RESULT_OK,
                        GameProtocol.createResultIntent(
                            request = request,
                            correct = if (index == 1) 1 else 0,
                            total = 1
                        )
                    )
                    finish()
                }
            }
            layout.addView(button, LinearLayout.LayoutParams(-1, dp(58)).apply {
                topMargin = dp(10)
            })
        }
        setContentView(layout)
    }
}
