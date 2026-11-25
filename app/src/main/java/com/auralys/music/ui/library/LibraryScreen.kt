package com.auralys.music.ui.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.CloudDone
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FileDownloadOff
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.QueueMusic
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.TrendingUp
import androidx.compose.material.icons.rounded.Upload
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.auralys.music.data.MusicRepository
import com.auralys.music.ui.Routes
import com.auralys.music.ui.components.EmptyState
import com.auralys.music.ui.components.PlaylistTile
import com.auralys.music.ui.components.SongRow
import com.auralys.music.ui.theme.ChipSurface
import com.auralys.music.ui.theme.NavSurface
import com.auralys.music.ui.theme.TextPrimary
import com.auralys.music.ui.theme.TextSecondary

private val libraryTabs = listOf("Semua", "Playlist", "Lagu", "Album", "Artis", "Podcast")

@Composable
fun LibraryScreen(nav: NavController) {
    val vm: LibraryViewModel = viewModel()
    var tab by rememberSaveable { mutableStateOf("Semua") }
    val liked by vm.likedSongs.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Spacer(Modifier.height(28.dp))
        Text(
            text = "Pustaka",
            style = MaterialTheme.typography.headlineSmall,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(18.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(26.dp)
        ) {
            Spacer(Modifier.width(20.dp))
            libraryTabs.forEach { t ->
                val active = tab == t
                Column(
                    modifier = Modifier
                        .clickable { tab = t }
                        .padding(vertical = 6.dp)
                ) {
                    Text(
                        text = t,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = if (active) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal
                        ),
                        color = TextPrimary
                    )
                    Spacer(Modifier.height(6.dp))
                    Box(
                        Modifier
                            .height(2.dp)
                            .fillMaxWidth()
                            .background(if (active) TextPrimary else Color.Transparent)
                    )
                }
            }
            Spacer(Modifier.width(20.dp))
        }

        Spacer(Modifier.height(14.dp))

        // Baris sortir + jumlah + ikon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(22.dp))
                    .background(ChipSurface)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tanggal ditambahkan", style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                Icon(Icons.Rounded.ArrowDropDown, null, tint = TextSecondary, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(ChipSurface),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.ArrowDownward, null, tint = TextPrimary, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.weight(1f))
            val countLabel = when (tab) {
                "Playlist", "Semua" -> "2 playlist"
                "Lagu" -> "${liked.size} lagu"
                "Album" -> "0 album"
                "Artis" -> "0 artis"
                else -> null
            }
            if (countLabel != null) {
                Text(countLabel, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                Spacer(Modifier.width(12.dp))
            }
            if (tab != "Podcast") {
                Icon(Icons.Rounded.Search, null, tint = TextPrimary, modifier = Modifier.size(22.dp))
                Spacer(Modifier.width(16.dp))
                Icon(Icons.Rounded.GridView, null, tint = TextPrimary, modifier = Modifier.size(22.dp))
            }
        }

        Spacer(Modifier.height(16.dp))

        Box(Modifier.fillMaxSize()) {
            when (tab) {
                "Semua", "Playlist" -> AllOrPlaylistTab(nav, liked.size)
                "Lagu" -> SongsTab(liked)
                "Album" -> FilteredEmptyTab(
                    chips = listOf("Disukai", "Pustaka", "Diunggah"),
                    message = "Pustaka album akan muncul di sini"
                )
                "Artis" -> FilteredEmptyTab(
                    chips = listOf("Disukai", "Pustaka"),
                    message = "Pustaka artis akan muncul di sini"
                )
                "Podcast" -> PodcastTab()
            }

            // FAB tambah
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 200.dp)
            ) {
                Surface(color = NavSurface, shape = CircleShape, shadowElevation = 6.dp, modifier = Modifier.size(56.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Rounded.Add, null, tint = TextPrimary, modifier = Modifier.size(26.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun AllOrPlaylistTab(nav: NavController, likedCount: Int) {
    LazyColumn(contentPadding = PaddingValues(bottom = 220.dp)) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                PlaylistTile(
                    title = "Terpopuler Bulanan",
                    subtitle = "$likedCount lagu",
                    modifier = Modifier.weight(1f),
                    onClick = { nav.navigate(Routes.list("teratas")) }
                )
                PlaylistTile(
                    title = "Terpopuler Mingguan",
                    subtitle = "$likedCount lagu",
                    modifier = Modifier.weight(1f),
                    onClick = { nav.navigate(Routes.list("teratas")) }
                )
            }
        }
        item {
            Text(
                text = "Koleksi",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
            )
        }
        item { KoleksiRow(Icons.Rounded.Favorite, "Disukai") { nav.navigate(Routes.list("disukai")) } }
        item { KoleksiRow(Icons.Rounded.FileDownloadOff, "Diunduh") { nav.navigate(Routes.list("diunduh")) } }
        item { KoleksiRow(Icons.Rounded.CloudDone, "Tersimpan di cache") { nav.navigate(Routes.list("cache")) } }
        item { KoleksiRow(Icons.Rounded.QueueMusic, "Di perangkat") { nav.navigate(Routes.list("perangkat")) } }
        item { KoleksiRow(Icons.Rounded.TrendingUp, "Teratas saya 50") { nav.navigate(Routes.list("teratas")) } }
        item { KoleksiRow(Icons.Rounded.Upload, "Diunggah") { nav.navigate(Routes.list("diunggah")) } }
    }
}

@Composable
private fun KoleksiRow(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = TextPrimary, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(18.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )
        Icon(Icons.Rounded.ChevronRight, null, tint = TextSecondary, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun SongsTab(liked: List<com.auralys.music.data.Song>) {
    var filter by rememberSaveable { mutableStateOf("Disukai") }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            listOf("Disukai", "Pustaka", "Diunggah", "Diunduh").forEach { f ->
                val active = filter == f
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (active) NavSurface else ChipSurface)
                        .clickable { filter = f }
                        .padding(horizontal = 16.dp, vertical = 9.dp)
                ) {
                    Text(f, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                }
            }
        }
        Spacer(Modifier.height(18.dp))
        if (liked.isEmpty()) {
            EmptyState("Ketik untuk mencari lagu di perangkat")
        } else {
            LazyColumn(contentPadding = PaddingValues(bottom = 220.dp)) {
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

@Composable
private fun FilteredEmptyTab(chips: List<String>, message: String) {
    var filter by rememberSaveable { mutableStateOf(chips.first()) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            chips.forEach { f ->
                val active = filter == f
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (active) NavSurface else ChipSurface)
                        .clickable { filter = f }
                        .padding(horizontal = 16.dp, vertical = 9.dp)
                ) {
                    Text(f, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                }
            }
        }
        Spacer(Modifier.height(40.dp))
        EmptyState(message)
    }
}

@Composable
private fun PodcastTab() {
    var filter by rememberSaveable { mutableStateOf("Episode") }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            listOf("Episode", "Saluran", "Diunduh").forEach { f ->
                val active = filter == f
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (active) NavSurface else ChipSurface)
                        .clickable { filter = f }
                        .padding(horizontal = 16.dp, vertical = 9.dp)
                ) {
                    Text(f, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        PodcastRow("Episode baru", "Playlist otomatis")
        PodcastRow("Episode untuk nanti", "Playlist otomatis")
    }
}

@Composable
private fun PodcastRow(title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(ChipSurface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Rounded.QueueMusic,
                null,
                tint = Color(0xFF3C4043),
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(Modifier.width(16.dp))
        Column {
            Text(title, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
            Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
        }
    }
}
