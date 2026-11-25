package com.auralys.music.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auralys_settings")

/** Kunci preferensi yang dipakai layar Pengaturan / Pemutar dan audio. */
object Prefs {
    val SYNC_ACCOUNT = booleanPreferencesKey("sync_account")
    val DATA_SAVER = booleanPreferencesKey("data_saver")
    val CROSSFADE = booleanPreferencesKey("crossfade")
    val SKIP_SILENCE = booleanPreferencesKey("skip_silence")
    val SKIP_SILENCE_INSTANT = booleanPreferencesKey("skip_silence_instant")
    val NORMALIZATION = booleanPreferencesKey("normalization")
    val AUDIO_OFFLOAD = booleanPreferencesKey("audio_offload")
    val VARISPEED = booleanPreferencesKey("varispeed")
    val HW_PROCESSING = booleanPreferencesKey("hw_processing")
    val PROGRESSIVE_SEEK = booleanPreferencesKey("progressive_seek")
    val SLEEP_TIMER = booleanPreferencesKey("sleep_timer")
    val SLEEP_REPEAT = booleanPreferencesKey("sleep_repeat")
    val SLEEP_END_OF_TRACK = booleanPreferencesKey("sleep_end_of_track")
    val SLEEP_FADE = booleanPreferencesKey("sleep_fade")
    val HISTORY_DAYS = intPreferencesKey("history_days")
}

data class AppSettings(
    val syncAccount: Boolean = true,
    val dataSaver: Boolean = false,
    val crossfade: Boolean = false,
    val skipSilence: Boolean = false,
    val skipSilenceInstant: Boolean = false,
    val normalization: Boolean = true,
    val audioOffload: Boolean = false,
    val varispeed: Boolean = false,
    val hwProcessing: Boolean = true,
    val progressiveSeek: Boolean = false,
    val sleepTimer: Boolean = false,
    val sleepRepeat: Boolean = false,
    val sleepEndOfTrack: Boolean = false,
    val sleepFade: Boolean = false,
    val historyDays: Int = 30
)

class SettingsRepository(context: Context) {

    private val store = context.applicationContext.dataStore

    val settings: Flow<AppSettings> = store.data.map { p ->
        AppSettings(
            syncAccount = p[Prefs.SYNC_ACCOUNT] ?: true,
            dataSaver = p[Prefs.DATA_SAVER] ?: false,
            crossfade = p[Prefs.CROSSFADE] ?: false,
            skipSilence = p[Prefs.SKIP_SILENCE] ?: false,
            skipSilenceInstant = p[Prefs.SKIP_SILENCE_INSTANT] ?: false,
            normalization = p[Prefs.NORMALIZATION] ?: true,
            audioOffload = p[Prefs.AUDIO_OFFLOAD] ?: false,
            varispeed = p[Prefs.VARISPEED] ?: false,
            hwProcessing = p[Prefs.HW_PROCESSING] ?: true,
            progressiveSeek = p[Prefs.PROGRESSIVE_SEEK] ?: false,
            sleepTimer = p[Prefs.SLEEP_TIMER] ?: false,
            sleepRepeat = p[Prefs.SLEEP_REPEAT] ?: false,
            sleepEndOfTrack = p[Prefs.SLEEP_END_OF_TRACK] ?: false,
            sleepFade = p[Prefs.SLEEP_FADE] ?: false,
            historyDays = p[Prefs.HISTORY_DAYS] ?: 30
        )
    }

    suspend fun setBool(key: Preferences.Key<Boolean>, value: Boolean) {
        store.edit { it[key] = value }
    }

    suspend fun setInt(key: Preferences.Key<Int>, value: Int) {
        store.edit { it[key] = value }
    }
}
