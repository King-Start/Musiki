package com.auralys.music.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.NorthWest
import androidx.compose.material.icons.rounded.Search
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.auralys.music.data.Catalog
import com.auralys.music.data.MusicRepository
import com.auralys.music.ui.components.EmptyState
import com.auralys.music.ui.components.SongRow
import com.auralys.music.ui.theme.ChipSurface
import com.auralys.music.ui.theme.NavSurface
import com.auralys.music.ui.theme.TextPrimary
import com.auralys.music.ui.theme.TextSecondary

@Composable
fun SearchScreen(nav: NavController) {
    var query by rememberSaveable { mutableStateOf("") }
    var deviceTab by rememberSaveable { mutableStateOf(false) }
    var deviceFilter by rememberSaveable { mutableStateOf("Semua") }
    val recents = rememberSaveable { mutableStateOf(listOf("dj")) }

    val results = Catalog.search(query)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Kembali",
                tint = TextPrimary,
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .clickable { if (!nav.popBackStack()) nav.navigate(com.auralys.music.ui.Routes.HOME) }
            )
            SearchBar(
                modifier = Modifier.weight(1f),
                value = query,
                hint = if (deviceTab) "Cari di perangkat…" else "Cari lagu, album, artis…",
                onValueChange = { query = it }
            )
        }
        Spacer(Modifier.height(16.dp))

        // Toggle YouTube / Perangkat
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(ChipSurface)
                .padding(4.dp)
        ) {
            SegmentTab("YouTube", !deviceTab, Modifier.weight(1f)) { deviceTab = false }
            SegmentTab("Perangkat", deviceTab, Modifier.weight(1f)) { deviceTab = true }
        }

        Spacer(Modifier.height(16.dp))

        if (!deviceTab) {
            if (query.isBlank()) {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 220.dp)
                ) {
                    if (recents.value.isNotEmpty()) {
                        items(recents.value) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { query = item }
                                    .padding(horizontal = 20.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Rounded.History, null, tint = TextPrimary, modifier = Modifier.size(22.dp))
                                Spacer(Modifier.width(16.dp))
                                Text(item, style = MaterialTheme.typography.titleMedium, color = TextPrimary, modifier = Modifier.weight(1f))
                                Icon(
                                    Icons.Rounded.Close, null, tint = TextPrimary,
                                    modifier = Modifier.size(20.dp).clickable {
                                        recents.value = recents.value - item
                                    }
                                )
                                Spacer(Modifier.width(16.dp))
                                Icon(Icons.Rounded.NorthWest, null, tint = TextPrimary, modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                    item {
                        Text(
                            text = "Jelajahi semua",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)
                        )
                    }
                    item {
                        Column(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Catalog.exploreGenres.chunked(2).forEach { pair ->
                                Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                                    pair.forEach { g ->
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(76.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(ChipSurface)
                                                .clickable { query = g },
                                            contentAlignment = Alignment.CenterStart
                                        ) {
                                            Text(
                                                text = g,
                                                style = MaterialTheme.typography.titleMedium,
                                                color = TextPrimary,
                                                modifier = Modifier.padding(horizontal = 18.dp)
                                            )
                                        }
                                    }
                                    if (pair.size == 1) Spacer(Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            } else {
                LazyColumn(contentPadding = PaddingValues(bottom = 220.dp)) {
                    item {
                        Text(
                            text = "Hasil untuk \"$query\"",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                        )
                    }
                    if (results.isEmpty()) {
                        item { EmptyState("Tidak ada hasil untuk \"$query\"") }
                    } else {
                        items(results.size) { idx ->
                            val song = results[idx]
                            SongRow(
                                song = song,
                                onMenu = {},
                                onClick = { MusicRepository.playSongs(results, idx) }
                            )
                        }
                    }
                }
            }
        } else {
            // Tab Perangkat
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                listOf("Semua", "Lagu", "Album", "Artis", "Playlist").forEach { f ->
                    val active = deviceFilter == f
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (active) NavSurface else ChipSurface)
                            .clickable { deviceFilter = f }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = f,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextPrimary
                        )
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            if (query.isBlank()) {
                EmptyState(
                    message = "Ketik untuk mencari lagu di perangkat",
                    modifier = Modifier.padding(top = 40.dp)
                )
            } else {
                LazyColumn(contentPadding = PaddingValues(bottom = 220.dp)) {
                    items(results.size) { idx ->
                        val song = results[idx]
                        SongRow(
                            song = song,
                            onMenu = {},
                            onClick = { MusicRepository.playSongs(results, idx) }
                        )
                    }
                }
            }
        }
    }

    // Tombol mikrofon melayang
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 20.dp, bottom = 210.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Surface(color = NavSurface, shape = CircleShape, shadowElevation = 6.dp, modifier = Modifier.size(52.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Rounded.Mic, null, tint = TextPrimary)
            }
        }
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(ChipSurface)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Rounded.Search, null, tint = TextSecondary, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(10.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            cursorBrush = SolidColor(TextPrimary),
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleMedium.copy(color = TextPrimary),
            decorationBox = { inner ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        Text(hint, style = MaterialTheme.typography.titleMedium, color = TextSecondary)
                    }
                    inner()
                }
            }
        )
    }
}

@Composable
private fun SegmentTab(label: String, active: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (active) NavSurface else androidx.compose.ui.graphics.Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary
        )
    }
}
