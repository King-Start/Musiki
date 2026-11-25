package com.auralys.music.ui.subscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.material.icons.rounded.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.auralys.music.data.Catalog
import com.auralys.music.data.MusicRepository
import com.auralys.music.ui.components.PlaylistTile
import com.auralys.music.ui.theme.CardSurface
import com.auralys.music.ui.theme.ChipSurface
import com.auralys.music.ui.theme.NavSurface
import com.auralys.music.ui.theme.TextPrimary
import com.auralys.music.ui.theme.TextSecondary
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class StatsViewModel : ViewModel() {

    data class Stats(val songs: Int, val artists: Int, val albums: Int)

    val stats: StateFlow<Stats> = MusicRepository.historyDao()
        .observeAll()
        .map2()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Stats(0, 0, 0))

    private fun kotlinx.coroutines.flow.Flow<List<com.auralys.music.data.HistoryEntity>>.map2() =
        this.map { entities ->
            val songs = entities.map { it.songId }.distinct()
            Stats(
                songs = songs.size,
                artists = songs.mapNotNull { Catalog.byId(it) }.map { it.artist }.distinct().size,
                albums = songs.mapNotNull { Catalog.byId(it) }.map { it.album }.distinct().size
            )
        }
}

@Composable
fun StatsScreen(nav: NavController) {
    val vm: StatsViewModel = viewModel()
    val stats by vm.stats.collectAsStateWithLifecycle()
    var period by rememberSaveable { mutableStateOf("1 minggu") }
    val periods = listOf("1 minggu", "1 bulan", "3 bulan", "6 bulan", "1 tahun", "Semua")

    SubScaffold(
        title = "Statistik",
        nav = nav,
        trailing = {
            Icon(Icons.Rounded.Search, "Cari", tint = TextPrimary, modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(18.dp))
            Icon(Icons.Rounded.Sync, "Segarkan", tint = TextPrimary, modifier = Modifier.size(24.dp))
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 200.dp)
        ) {
            Surface(
                color = CardSurface,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Rounded.TrendingUp, null, tint = TextPrimary, modifier = Modifier.size(26.dp))
                    Spacer(Modifier.width(16.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Recap kamu 2026", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                        Text(
                            "Lagu, artis, dan menit teratas",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                    Icon(Icons.Rounded.ArrowForward, null, tint = TextPrimary, modifier = Modifier.size(22.dp))
                }
            }

            Spacer(Modifier.height(18.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Spacer(Modifier.width(20.dp))
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(ChipSurface)
                        .padding(horizontal = 14.dp, vertical = 9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Berkelanjuan", style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                    Icon(Icons.Rounded.ArrowDropDown, null, tint = TextPrimary, modifier = Modifier.size(20.dp))
                }
                periods.forEach { p ->
                    val active = period == p
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (active) NavSurface else ChipSurface)
                            .clickable { period = p }
                            .padding(horizontal = 16.dp, vertical = 9.dp)
                    ) {
                        Text(p, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                    }
                }
                Spacer(Modifier.width(20.dp))
            }

            Spacer(Modifier.height(20.dp))
            Text(
                "2 playlist",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(Modifier.height(14.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                PlaylistTile("Terpopuler Mingguan", "", Modifier.weight(1f))
                PlaylistTile("Terpopuler Bulanan", "", Modifier.weight(1f))
            }

            Spacer(Modifier.height(26.dp))
            StatLine("${stats.songs} Lagu")
            Spacer(Modifier.height(18.dp))
            StatLine("${stats.artists} Artis")
            Spacer(Modifier.height(18.dp))
            StatLine("${stats.albums} Album")
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun StatLine(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.titleLarge,
        color = TextPrimary,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}
