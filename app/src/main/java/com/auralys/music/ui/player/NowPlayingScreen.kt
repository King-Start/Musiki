package com.auralys.music.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.QueueMusic
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.auralys.music.data.MusicRepository
import com.auralys.music.ui.components.SongCover
import com.auralys.music.ui.theme.AccentBlack
import com.auralys.music.ui.theme.AccentPurple
import com.auralys.music.ui.theme.AppBackground
import com.auralys.music.ui.theme.TextPrimary
import com.auralys.music.ui.theme.TextSecondary

@Composable
fun NowPlayingScreen(nav: NavController) {
    val song by MusicRepository.currentSong.collectAsStateWithLifecycle()
    val playing by MusicRepository.isPlaying.collectAsStateWithLifecycle()
    val position by MusicRepository.positionMs.collectAsStateWithLifecycle()
    val duration by MusicRepository.durationMs.collectAsStateWithLifecycle()
    val liked by MusicRepository.likedIds.collectAsStateWithLifecycle()
    val shuffle by MusicRepository.shuffleOn.collectAsStateWithLifecycle()
    val repeatAll by MusicRepository.repeatAll.collectAsStateWithLifecycle()

    val current = song ?: return
    val isLiked = liked.contains(current.id)
    val max = duration.coerceAtLeast(1L)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.KeyboardArrowDown,
                "Tutup",
                tint = TextPrimary,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { nav.popBackStack() }
            )
            Spacer(Modifier.weight(1f))
            Icon(Icons.Rounded.QueueMusic, "Antrean", tint = TextPrimary, modifier = Modifier.size(26.dp))
        }

        Spacer(Modifier.height(28.dp))
        SongCover(
            song = current,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(18.dp)),
            cornerRadius = 18.dp
        )

        Spacer(Modifier.height(28.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = current.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = current.artist,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                imageVector = if (isLiked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                contentDescription = "Suka",
                tint = TextPrimary,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { MusicRepository.toggleLike(current.id) }
            )
            Spacer(Modifier.width(18.dp))
            Icon(Icons.Rounded.MoreVert, "Lainnya", tint = TextPrimary, modifier = Modifier.size(26.dp))
        }

        Spacer(Modifier.height(18.dp))
        Slider(
            value = position.toFloat().coerceIn(0f, max.toFloat()),
            onValueChange = { MusicRepository.seekTo(it.toLong()) },
            valueRange = 0f..max.toFloat(),
            colors = SliderDefaults.colors(
                thumbColor = TextPrimary,
                activeTrackColor = TextPrimary,
                inactiveTrackColor = Color(0xFFD7D9DD)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row(Modifier.fillMaxWidth()) {
            Text(fmt(position), style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            Spacer(Modifier.weight(1f))
            Text(fmt(duration), style = MaterialTheme.typography.bodySmall, color = TextSecondary)
        }

        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.Shuffle,
                "Acak",
                tint = if (shuffle) AccentPurple else TextPrimary,
                modifier = Modifier
                    .size(26.dp)
                    .clickable { MusicRepository.toggleShuffle() }
            )
            Icon(
                Icons.Rounded.SkipPrevious,
                "Sebelumnya",
                tint = TextPrimary,
                modifier = Modifier
                    .size(38.dp)
                    .clickable { MusicRepository.previous() }
            )
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .background(AccentBlack)
                    .clickable { MusicRepository.togglePlay() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (playing) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                    contentDescription = "Putar/Jeda",
                    tint = Color.White,
                    modifier = Modifier.size(42.dp)
                )
            }
            Icon(
                Icons.Rounded.SkipNext,
                "Berikutnya",
                tint = TextPrimary,
                modifier = Modifier
                    .size(38.dp)
                    .clickable { MusicRepository.next() }
            )
            Icon(
                Icons.Rounded.Repeat,
                "Ulangi",
                tint = if (repeatAll) AccentPurple else TextPrimary,
                modifier = Modifier
                    .size(26.dp)
                    .clickable { MusicRepository.toggleRepeat() }
            )
        }
        Spacer(Modifier.height(40.dp))
    }
}

private fun fmt(ms: Long): String {
    val total = ms / 1000
    return String.format("%d:%02d", total / 60, total % 60)
}
