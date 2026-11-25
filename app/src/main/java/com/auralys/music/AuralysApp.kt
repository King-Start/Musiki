package com.auralys.music

import android.app.Application
import com.auralys.music.data.MusicRepository
import com.auralys.music.data.SettingsRepository

class AuralysApp : Application() {

    lateinit var settingsRepository: SettingsRepository
        private set

    override fun onCreate() {
        super.onCreate()
        settingsRepository = SettingsRepository(this)
        MusicRepository.init(this)
    }
}
