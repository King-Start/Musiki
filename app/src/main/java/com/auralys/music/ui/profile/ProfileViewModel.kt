package com.auralys.music.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.auralys.music.AuralysApp
import com.auralys.music.data.Prefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = (app as AuralysApp).settingsRepository

    val syncAccount: Flow<Boolean> = repo.settings.map { it.syncAccount }

    fun setSync(value: Boolean) {
        viewModelScope.launch { repo.setBool(Prefs.SYNC_ACCOUNT, value) }
    }
}
