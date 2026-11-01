package com.vcc.aprender.data.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.parentalDataStore by preferencesDataStore(name = "parental_settings")

data class ParentalSettings(
    val audioEnabled: Boolean = true,
    val speechRate: Float = 0.85f,
    val animationsEnabled: Boolean = true
) {
    init { require(speechRate in 0.5f..1.5f) }
}

class ParentalSettingsRepository(context: Context) {
    private val store = context.applicationContext.parentalDataStore
    private val audioKey = booleanPreferencesKey("audio_enabled")
    private val rateKey = floatPreferencesKey("speech_rate")
    private val animationsKey = booleanPreferencesKey("animations_enabled")
    val settings: Flow<ParentalSettings> = store.data.map { preferences ->
        ParentalSettings(
            audioEnabled = preferences[audioKey] ?: true,
            speechRate = (preferences[rateKey] ?: 0.85f).coerceIn(0.5f, 1.5f),
            animationsEnabled = preferences[animationsKey] ?: true
        )
    }

    suspend fun setAnimationsEnabled(enabled: Boolean) {
        store.edit { it[animationsKey] = enabled }
    }

    suspend fun setAudioEnabled(enabled: Boolean) {
        store.edit { it[audioKey] = enabled }
    }

    suspend fun setSpeechRate(rate: Float) {
        require(rate in 0.5f..1.5f)
        store.edit { it[rateKey] = rate }
    }
}
