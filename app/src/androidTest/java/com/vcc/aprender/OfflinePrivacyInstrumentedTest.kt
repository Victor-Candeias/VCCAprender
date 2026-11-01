package com.vcc.aprender

import android.content.Context
import android.content.pm.PackageManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OfflinePrivacyInstrumentedTest {
    @Test fun appDoesNotRequestInternetOrSensitiveRuntimePermissions() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        @Suppress("DEPRECATION")
        val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
        val permissions = info.requestedPermissions.orEmpty().toSet()
        assertFalse(permissions.contains(android.Manifest.permission.INTERNET))
        assertFalse(permissions.contains(android.Manifest.permission.ACCESS_FINE_LOCATION))
        assertFalse(permissions.contains(android.Manifest.permission.RECORD_AUDIO))
        assertFalse(permissions.contains(android.Manifest.permission.READ_CONTACTS))
    }

    @Test fun privateBackupIsDisabled() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val info = context.applicationInfo
        assertEquals(0, info.flags and android.content.pm.ApplicationInfo.FLAG_ALLOW_BACKUP)
    }
}
