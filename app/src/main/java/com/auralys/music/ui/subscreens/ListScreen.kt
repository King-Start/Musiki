package com.auralys.music.ui.subscreens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.auralys.music.data.MusicRepository
import com.auralys.music.ui.components.EmptyState
import com.auralys.music.ui.components.SongRow
import com.auralys.music.ui.library.LibraryViewModel

@Composable
fun ListScreen(type: String, nav: NavController) {
    val vm: LibraryViewModel = viewModel()
    val liked by vm.likedSongs.collectAsStateWithLifecycle()

    val title = when (type) {
        "disukai" -> "Disukai"
        "diunduh" -> "Diunduh"
        "cache" -> "Tersimpan di cache"
        "perangkat" -> "Di perangkat"
        "teratas" -> "Teratas saya 50"
        "diunggah" -> "Diunggah"
        else -> "Daftar"
    }

    val emptyMessage = when (type) {
        "disukai" -> "Lagu yang kamu sukai akan muncul di sini"
        "diunduh" -> "Tidak ada unduhan"
        "cache" -> "Tidak ada lagu dalam cache"
        "perangkat" -> "Ketik untuk mencari lagu di perangkat"
        "teratas" -> "Lagu teratasmu akan muncul di sini"
        else -> "Belum ada isi"
    }

    SubScaffold(title = title, nav = nav) {
        if (liked.isEmpty()) {
            EmptyState(emptyMessage)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 200.dp)
            ) {
                items(liked.size) { idx ->
                    SongRow(
                        song = liked[idx],
                        onMenu = {},
                        onClick = { MusicRepository.playSongs(liked, idx) }
                    )
                }
            }
        }
    }
}
