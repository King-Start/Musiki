package com.auralys.music.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auralys.music.data.Catalog
import com.auralys.music.data.MusicRepository
import com.auralys.music.data.Song
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class LibraryViewModel : ViewModel() {

    val likedSongs: StateFlow<List<Song>> = MusicRepository.likedDao()
        .observeAll()
        .map { entities -> entities.mapNotNull { Catalog.byId(it.songId) } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val likedCount: StateFlow<Int> = MusicRepository.likedDao()
        .observeCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
}
