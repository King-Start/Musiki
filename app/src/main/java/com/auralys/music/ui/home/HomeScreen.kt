package com.auralys.music.ui.home

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Cast
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.auralys.music.data.Catalog
import com.auralys.music.data.MixCard
import com.auralys.music.data.MusicRepository
import com.auralys.music.data.Song
import com.auralys.music.ui.components.CardShelf
import com.auralys.music.ui.components.CoverArt
import com.auralys.music.ui.components.ListShelf
import com.auralys.music.ui.components.MixShelf
import com.auralys.music.ui.components.SectionHeader
import com.auralys.music.ui.components.SongCover
import com.auralys.music.ui.components.SongRow
import com.auralys.music.ui.theme.AccentPurple
import com.auralys.music.ui.theme.AppBackground
import com.auralys.music.ui.theme.ChipSurface
import com.auralys.music.ui.theme.NavSurface
import com.auralys.music.ui.theme.TextPrimary
import com.auralys.music.ui.theme.TextSecondary
import java.util.Calendar

@Composable
fun HomeScreen(nav: NavController) {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when {
        hour < 11 -> "Selamat pagi"
        hour < 15 -> "Selamat siang"
        hour < 19 -> "Selamat sore"
        else -> "Selamat malam"
    }

    fun playList(list: List<Song>, start: Song) {
        MusicRepository.playSongs(list, list.indexOf(start).coerceAtLeast(0))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 210.dp)
        ) {
            item {
                Column(Modifier.padding(horizontal = 20.dp)) {
                    Spacer(Modifier.height(28.dp))
                    Text(
                        text = "Beranda",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = greeting,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(28.dp)
                    ) {
                        Catalog.moodChips.forEach { chip ->
                            Text(
                                text = chip,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary,
                                modifier = Modifier.padding(vertical = 6.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                }
            }

            item { SectionHeader("Andalanmu") }
            item {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Spacer(Modifier.width(20.dp))
                        Catalog.andalanmu.forEach { song ->
                            Column(Modifier.width(150.dp)) {
                                SongCover(
                                    song = song,
                                    modifier = Modifier
                                        .size(150.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    cornerRadius = 12.dp
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = song.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary,
                                    maxLines = 1
                                )
                            }
                        }
                        Spacer(Modifier.width(20.dp))
                    }
                    Spacer(Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(3) { i ->
                            Box(
                                Modifier
                                    .padding(horizontal = 3.dp)
                                    .size(width = if (i == 0) 16.dp else 6.dp, height = 4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(if (i == 0) TextPrimary else Color(0xFFC9CCD1))
                            )
                        }
                    }
                    Spacer(Modifier.height(18.dp))
                }
            }

            item {
                MixRowHeader(
                    initial = "B",
                    label = "Mix untukmu",
                    title = "Bukan Bukan",
                    onClick = { MusicRepository.playSongs(Catalog.songs, Catalog.songs.indexOfFirst { it.id == "s43" }.coerceAtLeast(0)) }
                )
            }

            item {
                Column(Modifier.padding(horizontal = 20.dp)) {
                    Spacer(Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.linearGradient(listOf(Color(0xFF8B6CE8), Color(0xFFE858B6)))
                            )
                            .clickable { nav.navigate(com.auralys.music.ui.Routes.list("disukai")) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ThumbUp,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(72.dp)
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "Liked Music",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary
                    )
                    Text(
                        text = "Auto playlist • Auto playlist",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Spacer(Modifier.height(22.dp))
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(AccentPurple),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("B", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.width(14.dp))
                    Text(
                        text = "Quick picks",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Putar semua",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary,
                        modifier = Modifier.clickable { MusicRepository.playSongs(Catalog.quickPicks, 0) }
                    )
                }
            }
            item { ListShelf(Catalog.quickPicks) { playList(Catalog.quickPicks, it) } }

            item {
                SectionHeader(
                    "Music videos for you",
                    actionLabel = "Putar semua",
                    onAction = { MusicRepository.playSongs(Catalog.musicVideos, 0) }
                )
            }
            item { ListShelf(Catalog.musicVideos) { playList(Catalog.musicVideos, it) } }

            item {
                Column(Modifier.padding(horizontal = 20.dp)) {
                    Spacer(Modifier.height(14.dp))
                    Text(
                        text = "STATION",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Text(
                        text = "Listen together",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )
                    Spacer(Modifier.height(6.dp))
                }
            }
            item { ListShelf(Catalog.stations) { playList(Catalog.stations, it) } }

            item { SectionHeader("New releases", showArrow = true) }
            item { CardShelf(Catalog.newReleases) { playList(Catalog.newReleases, it) } }

            item {
                SectionHeader(
                    "Forgotten favorites",
                    actionLabel = "Putar semua",
                    onAction = { MusicRepository.playSongs(Catalog.forgottenFavorites, 0) }
                )
            }
            item { CardShelf(Catalog.forgottenFavorites) { playList(Catalog.forgottenFavorites, it) } }

            item {
                SectionHeader(
                    "Covers and remixes",
                    actionLabel = "Putar semua",
                    onAction = { MusicRepository.playSongs(Catalog.coversRemixes, 0) }
                )
            }
            item { ListShelf(Catalog.coversRemixes) { playList(Catalog.coversRemixes, it) } }

            item { SectionHeader("Trending community playlists") }
            item { MixShelf(mixFromCards(Catalog.trendingPlaylists)) }

            item { SectionHeader("From the community") }
            item { MixShelf(mixFromCards(Catalog.fromCommunity)) }

            item { SectionHeader("Suasana dan genre", showArrow = true) }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Spacer(Modifier.width(20.dp))
                    Catalog.genreChips.chunked(2).forEach { pair ->
                        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                            pair.forEach { g ->
                                Box(
                                    modifier = Modifier
                                        .width(180.dp)
                                        .height(64.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(ChipSurface)
                                        .clickable { nav.navigate(com.auralys.music.ui.Routes.SEARCH) },
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Text(
                                        text = g,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = TextPrimary,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.width(20.dp))
                }
            }

            item {
                MixRowHeader(
                    initial = "H",
                    label = "SIMILAR TO",
                    title = Catalog.similarArtist,
                    circleColor = Color(0xFFE9E45A),
                    onClick = { }
                )
            }
            item {
                MixShelf(
                    Catalog.similarMixes,
                    onPlay = { card ->
                        val match = Catalog.songs.firstOrNull { it.title.startsWith(card.title.take(6)) }
                        MusicRepository.playSongs(match?.let { listOf(it) } ?: Catalog.similarSongsFallback(), 0)
                    }
                )
            }

            item { SectionHeader("Albums for you") }
            item { CardShelf(Catalog.albumsForYou) { playList(Catalog.albumsForYou, it) } }

            item {
                SectionHeader(
                    "Hip-hop",
                    actionLabel = "Putar semua",
                    showArrow = true,
                    onAction = { MusicRepository.playSongs(Catalog.hipHop, 0) }
                )
            }
            item { CardShelf(Catalog.hipHop) { playList(Catalog.hipHop, it) } }

            item {
                SectionHeader(
                    "Your daily discover",
                    actionLabel = "Putar semua",
                    onAction = { MusicRepository.playSongs(Catalog.dailyDiscover, 0) }
                )
            }
            item { ListShelf(Catalog.dailyDiscover) { playList(Catalog.dailyDiscover, it) } }

            item {
                SectionHeader(
                    "Trending songs for you",
                    actionLabel = "Putar semua",
                    onAction = { MusicRepository.playSongs(Catalog.trendingSongs, 0) }
                )
            }
            item { ListShelf(Catalog.trendingSongs) { playList(Catalog.trendingSongs, it) } }

            item {
                Column(Modifier.padding(horizontal = 20.dp)) {
                    Spacer(Modifier.height(14.dp))
                    Text(
                        text = "TOP TUNES RIGHT NOW",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Text(
                        text = "Today's Indonesian Hits",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )
                    Spacer(Modifier.height(6.dp))
                }
            }
            item { CardShelf(Catalog.indoHits) { playList(Catalog.indoHits, it) } }

            item {
                SectionHeader(
                    "Long listens",
                    actionLabel = "Putar semua",
                    onAction = { MusicRepository.playSongs(Catalog.longListens, 0) }
                )
            }
            item {
                Column {
                    Catalog.longListens.forEach { song ->
                        SongRow(
                            song = song,
                            subtitle = "${song.artist} • ${song.durationLabel}",
                            onMenu = {},
                            onClick = { playList(Catalog.longListens, song) }
                        )
                    }
                }
            }

            item { SectionHeader("Mixed for you", showArrow = true) }
            item { MixShelf(Catalog.mixedForYou) }

            item { SectionHeader("Fresh finds, old favorites") }
            item { MixShelf(Catalog.freshFinds) }

            item {
                SectionHeader(
                    "Live performances",
                    actionLabel = "Putar semua",
                    onAction = { MusicRepository.playSongs(Catalog.livePerformances, 0) }
                )
            }
            item { ListShelf(Catalog.livePerformances) { playList(Catalog.livePerformances, it) } }
        }

        // Tombol melayang kanan (mikrofon & cast) seperti referensi
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 200.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            FloatingCircle { Icon(Icons.Rounded.Mic, null, tint = TextPrimary) }
            FloatingCircle { Icon(Icons.Rounded.Cast, null, tint = TextPrimary) }
        }
    }
}

private fun Catalog.similarSongsFallback(): List<Song> = Catalog.similarMixes.flatMap { mix ->
    Catalog.songs.filter { mix.title.contains(it.title.take(4), ignoreCase = true) }
}.ifEmpty { Catalog.coversRemixes }

private fun mixFromCards(cards: List<MixCard>): List<MixCard> = cards

@Composable
private fun FloatingCircle(content: @Composable () -> Unit) {
    Surface(
        color = NavSurface,
        shape = CircleShape,
        shadowElevation = 6.dp,
        modifier = Modifier.size(52.dp)
    ) {
        Box(contentAlignment = Alignment.Center) { content() }
    }
}

@Composable
private fun MixRowHeader(
    initial: String,
    label: String,
    title: String,
    circleColor: Color = AccentPurple,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(circleColor),
            contentAlignment = Alignment.Center
        ) {
            Text(initial, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(text = label, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            Text(text = title, style = MaterialTheme.typography.titleLarge, color = TextPrimary)
        }
        Icon(Icons.Rounded.ArrowForward, null, tint = TextPrimary)
    }
}
