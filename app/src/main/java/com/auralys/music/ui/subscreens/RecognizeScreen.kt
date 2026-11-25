package com.auralys.music.ui.subscreens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.auralys.music.data.Catalog
import com.auralys.music.data.MusicRepository
import com.auralys.music.data.Song
import com.auralys.music.ui.components.SongRow
import com.auralys.music.ui.theme.AccentBlack
import com.auralys.music.ui.theme.TextPrimary
import com.auralys.music.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun RecognizeScreen(nav: NavController) {
    var listening by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf<Song?>(null) }

    LaunchedEffect(listening) {
        if (listening) {
            delay(2600)
            listening = false
            result = Catalog.songs.random()
        }
    }

    val transition = rememberInfiniteTransition(label = "pulse")
    val scale by transition.animateFloat(
        initialValue = 1f,
        targetValue = if (listening) 1.12f else 1f,
        animationSpec = infiniteRepeatable(tween(700), RepeatMode.Reverse),
        label = "scale"
    )

    SubScaffold(
        title = "Kenali musik",
        nav = nav,
        trailing = {
            Icon(Icons.Rounded.History, "Riwayat", tint = TextPrimary, modifier = Modifier.size(24.dp))
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(120.dp))
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEDEFF2)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .scale(if (listening) scale else 1f)
                        .clip(CircleShape)
                        .background(AccentBlack)
                        .clickable {
                            result = null
                            listening = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Rounded.Mic, null, tint = Color.White, modifier = Modifier.size(64.dp))
                }
            }
            Spacer(Modifier.height(28.dp))
            Text(
                text = if (listening) "Mendengarkan…" else "Ketuk untuk mengenali",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary
            )
            Spacer(Modifier.height(40.dp))
            result?.let { song ->
                Text(
                    "Ditemukan:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                Spacer(Modifier.height(8.dp))
                SongRow(
                    song = song,
                    onMenu = {},
                    onClick = { MusicRepository.playSongs(listOf(song), 0) }
                )
            }
        }
    }
}
