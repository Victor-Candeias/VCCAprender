package com.vcc.aprender.plugins

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import com.vcc.aprender.pluginsdk.GameContract
import com.vcc.aprender.pluginsdk.GameValidation

/** A separate APK whose exported Activity and certificate have been verified. */
data class InstalledGame(
    val gameId: String,
    val title: String,
    val component: ComponentName
)

/**
 * Discover only packages declaring ACTION_PLAY (Android 11 package visibility
 * is limited by <queries>). Never loads external APK code into this process.
 *
 * v1 trust policy: exact same signing certificate as the host APK.
 * An untrusted APK may advertise the same action/name; it will be rejected.
 */
class InstalledGameRegistry(private val context: Context) {
    private val manager: PackageManager get() = context.packageManager

    fun installedGames(): List<InstalledGame> {
        val query = Intent(GameContract.ACTION_PLAY).addCategory(Intent.CATEGORY_DEFAULT)
        val matches = if (Build.VERSION.SDK_INT >= 33) {
            manager.queryIntentActivities(
                query,
                PackageManager.ResolveInfoFlags.of(PackageManager.GET_META_DATA.toLong())
            )
        } else {
            @Suppress("DEPRECATION")
            manager.queryIntentActivities(query, PackageManager.GET_META_DATA)
        }
        return matches.mapNotNull { info ->
            val activity = info.activityInfo ?: return@mapNotNull null
            if (!activity.enabled || !activity.exported ||
                activity.packageName == context.packageName ||
                activity.permission != GameContract.PERMISSION_PLAY
            ) return@mapNotNull null

            if (manager.checkSignatures(context.packageName, activity.packageName) !=
                PackageManager.SIGNATURE_MATCH
            ) return@mapNotNull null

            val meta = activity.metaData ?: return@mapNotNull null
            if (meta.getInt(GameContract.META_PROTOCOL_VERSION, -1) != GameContract.PROTOCOL_VERSION) {
                return@mapNotNull null
            }
            val id = meta.getString(GameContract.META_GAME_ID) ?: return@mapNotNull null
            if (!GameValidation.validGameId(id)) return@mapNotNull null
            val title = meta.getString(GameContract.META_GAME_TITLE)
                ?.trim()?.take(80)?.takeIf { it.isNotBlank() } ?: return@mapNotNull null

            InstalledGame(id, title, ComponentName(activity.packageName, activity.name))
        }
            // Refuse ambiguous game IDs, including duplicates in the same APK.
            .groupBy { it.gameId }
            .filterValues { it.size == 1 }
            .values.map { it.single() }
            .sortedWith(compareBy({ it.title.lowercase() }, { it.gameId }))
    }

    /** Recheck trust at the moment of launch and when accepting a result. */
    fun findTrusted(component: ComponentName, gameId: String): InstalledGame? =
        installedGames().firstOrNull { it.component == component && it.gameId == gameId }
}
