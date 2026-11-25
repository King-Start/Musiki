package com.auralys.music.ui.subscreens

import android.app.Application
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.auralys.music.AuralysApp
import com.auralys.music.data.AppSettings
import com.auralys.music.data.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: SettingsRepository = (app as AuralysApp).settingsRepository

    val settings: StateFlow<AppSettings> = repo.settings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppSettings())

    fun setBool(key: Preferences.Key<Boolean>, value: Boolean) {
        viewModelScope.launch { repo.setBool(key, value) }
    }

    fun setInt(key: Preferences.Key<Int>, value: Int) {
        viewModelScope.launch { repo.setInt(key, value) }
    }
}
