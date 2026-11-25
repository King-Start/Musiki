package com.auralys.music.ui.subscreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.auralys.music.data.Catalog
import com.auralys.music.data.HistoryEntity
import com.auralys.music.data.MusicRepository
import com.auralys.music.data.Song
import com.auralys.music.ui.components.EmptyState
import com.auralys.music.ui.components.SongRow
import com.auralys.music.ui.theme.ChipSurface
import com.auralys.music.ui.theme.NavSurface
import com.auralys.music.ui.theme.TextPrimary
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HistoryViewModel : ViewModel() {

    data class MonthGroup(val label: String, val songs: List<Song>)

    val groups: StateFlow<List<MonthGroup>> = MusicRepository.historyDao()
        .observeAll()
        .map { entities -> groupByMonth(entities) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private fun groupByMonth(entities: List<HistoryEntity>): List<MonthGroup> {
        val format = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        val seen = mutableSetOf<Pair<String, String>>()
        val result = mutableListOf<MonthGroup>()
        entities.forEach { entity ->
            val song = Catalog.byId(entity.songId) ?: return@forEach
            calendar.timeInMillis = entity.playedAt
            val label = format.format(calendar.time)
            val key = label to song.id
            if (seen.add(key)) {
                val existing = result.firstOrNull { it.label == label }
                if (existing == null) {
                    result.add(MonthGroup(label, listOf(song)))
                } else {
                    val idx = result.indexOf(existing)
                    result[idx] = existing.copy(songs = existing.songs + song)
                }
            }
        }
        return result
    }
}

@Composable
fun HistoryScreen(nav: NavController) {
    val vm: HistoryViewModel = viewModel()
    val groups by vm.groups.collectAsStateWithLifecycle()
    var online by rememberSaveable { mutableStateOf(true) }

    SubScaffold(
        title = "Riwayat",
        nav = nav,
        trailing = {
            Icon(Icons.Rounded.Search, "Cari", tint = TextPrimary, modifier = Modifier.size(24.dp))
        }
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HistoryChip("Lokal", !online) { online = false }
                HistoryChip("Online", online) { online = true }
            }
            Spacer(Modifier.height(8.dp))
            if (groups.isEmpty()) {
                EmptyState("Riwayat pemutaran akan muncul di sini")
            } else {
                LazyColumn(contentPadding = PaddingValues(bottom = 200.dp)) {
                    groups.forEach { group ->
                        item {
                            Text(
                                text = group.label,
                                style = MaterialTheme.typography.titleLarge,
                                color = TextPrimary,
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                            )
                        }
                        items(group.songs.size) { idx ->
                            val song = group.songs[idx]
                            SongRow(
                                song = song,
                                subtitle = "${song.artist} • ${song.durationLabel}",
                                onMenu = {},
                                onClick = { MusicRepository.playSongs(group.songs, idx) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryChip(label: String, active: Boolean, onClick: () -> Unit) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (active) NavSurface else ChipSurface)
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 9.dp)
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
    }
}
