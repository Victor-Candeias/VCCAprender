package com.vcc.aprender

import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.AdaptiveIconDrawable
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.xmlpull.v1.XmlPullParser

/** Device tests: minSdk 26 guarantees adaptive icon support. */
@RunWith(AndroidJUnit4::class)
class LauncherBrandingTest {
    private val context: Context get() = ApplicationProvider.getApplicationContext()

    @Test fun launcherAndRoundIconsAreAdaptive() {
        val appInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
        // Android may select the round resource as the application icon.
        assertTrue(appInfo.icon in listOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher_round))
        // ApplicationInfo does not expose roundIcon in the public Android SDK.
        var roundIcon = 0
        context.assets.openXmlResourceParser("AndroidManifest.xml").use { manifest ->
            while (manifest.eventType != XmlPullParser.END_DOCUMENT) {
                if (manifest.eventType == XmlPullParser.START_TAG && manifest.name == "application") {
                    roundIcon = manifest.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "roundIcon", 0)
                    break
                }
                manifest.next()
            }
        }
        assertEquals(R.mipmap.ic_launcher_round, roundIcon)
        assertTrue(context.getDrawable(R.mipmap.ic_launcher) is AdaptiveIconDrawable)
        assertTrue(context.getDrawable(R.mipmap.ic_launcher_round) is AdaptiveIconDrawable)
    }

    @Test fun splashActivityUsesSplashTheme() {
        val info = context.packageManager.getActivityInfo(
            ComponentName(context, MainActivity::class.java), 0
        )
        assertEquals(R.style.Theme_VCCAprender_Splash, info.themeResource)
        assertNotNull(context.getDrawable(R.drawable.ic_launcher_foreground))
        assertNotNull(context.getDrawable(R.drawable.ic_launcher_monochrome))
        assertEquals(0xFF2156B5.toInt(), context.getColor(R.color.brand_blue))
    }
}
