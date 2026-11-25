package com.auralys.music.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.FormatListBulleted
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.auralys.music.data.MixCard
import com.auralys.music.data.Song
import com.auralys.music.ui.theme.TextPrimary
import com.auralys.music.ui.theme.TextSecondary

/** Sampul gradien digambar native — tidak butuh aset/gambar jaringan. */
@Composable
fun CoverArt(
    colors: List<Long>,
    label: String?,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.titleMedium.copy(color = Color.White)
) {
    val brush = Brush.linearGradient(colors.map { Color(it) })
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(brush),
        contentAlignment = Alignment.Center
    ) {
        if (label != null) {
            Text(
                text = label,
                style = textStyle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SongCover(
    song: Song,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp
) {
    CoverArt(
        colors = song.coverColors,
        label = song.title.take(14),
        modifier = modifier,
        cornerRadius = cornerRadius,
        textStyle = MaterialTheme.typography.bodySmall.copy(
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    showArrow: Boolean = false,
    onAction: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )
        if (actionLabel != null) {
            Text(
                text = actionLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary,
                modifier = Modifier.clickable { onAction?.invoke() }
            )
        }
        if (showArrow) {
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = null,
                tint = TextPrimary,
                modifier = Modifier
                    .size(22.dp)
                    .clickable { onAction?.invoke() }
            )
        }
    }
}

/** Baris lagu gaya daftar (sampul kecil + judul + artis + menu titik tiga). */
@Composable
fun SongRow(
    song: Song,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onClick: () -> Unit = {},
    onMenu: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SongCover(song = song, modifier = Modifier.size(48.dp), cornerRadius = 8.dp)
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = song.title,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle ?: song.artist,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (onMenu != null) {
            IconButton(onClick = onMenu) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "Opsi",
                    tint = TextPrimary
                )
            }
        }
    }
}

/** Kartu persegi horizontal dengan tombol putar melayang (New releases, dll). */
@Composable
fun PlayCard(
    song: Song,
    modifier: Modifier = Modifier,
    size: Dp = 168.dp,
    onPlay: () -> Unit
) {
    Column(modifier = modifier.width(size)) {
        Box {
            SongCover(
                song = song,
                modifier = Modifier
                    .size(size)
                    .clip(RoundedCornerShape(12.dp)),
                cornerRadius = 12.dp
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.85f))
                    .clickable(onClick = onPlay),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "Putar",
                    tint = Color.White
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = song.title,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = song.artist,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/** Kartu mix/playlist persegi dengan label gaya sampul. */
@Composable
fun MixCardView(
    card: MixCard,
    modifier: Modifier = Modifier,
    size: Dp = 168.dp,
    onPlay: (() -> Unit)? = null
) {
    Column(modifier = modifier.width(size)) {
        Box {
            CoverArt(
                colors = card.colors,
                label = card.styleLabel,
                modifier = Modifier
                    .size(size)
                    .clip(RoundedCornerShape(12.dp)),
                cornerRadius = 12.dp,
                textStyle = MaterialTheme.typography.headlineSmall.copy(color = Color.White)
            )
            if (onPlay != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.85f))
                        .clickable(onClick = onPlay),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = "Putar",
                        tint = Color.White
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = card.title,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = card.artist,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun CardShelf(
    songs: List<Song>,
    modifier: Modifier = Modifier,
    onPlay: (Song) -> Unit
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(songs, key = { it.id }) { song ->
            PlayCard(song = song, onPlay = { onPlay(song) })
        }
    }
}

@Composable
fun MixShelf(
    cards: List<MixCard>,
    modifier: Modifier = Modifier,
    onPlay: ((MixCard) -> Unit)? = null
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(cards) { card ->
            MixCardView(card = card, onPlay = onPlay?.let { { it(card) } })
        }
    }
}

@Composable
fun ListShelf(
    songs: List<Song>,
    modifier: Modifier = Modifier,
    onSongClick: (Song) -> Unit
) {
    Column(modifier = modifier) {
        songs.forEach { song ->
            SongRow(song = song, onMenu = {}, onClick = { onSongClick(song) })
        }
    }
}

/** Kartu playlist abu-abu dengan ikon daftar (Pustaka). */
@Composable
fun PlaylistTile(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(modifier = modifier.clickable(onClick = onClick)) {
        Surface(
            color = Color(0xFFF6F7FA),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(168.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.FormatListBulleted,
                    contentDescription = null,
                    tint = Color(0xFF3C4043),
                    modifier = Modifier.size(72.dp)
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )
    }
}

@Composable
fun EmptyState(
    message: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SunsetMark(modifier = Modifier.size(64.dp))
        Spacer(Modifier.height(20.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary
        )
    }
}

/** Ikon matahari/ombak biru seperti empty state referensi. */
@Composable
fun SunsetMark(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CanvasSun()
    }
}

@Composable
private fun CanvasSun() {
    androidx.compose.foundation.Canvas(Modifier.size(64.dp)) {
        val stripe = Color(0xFF1F608C)
        val w = size.width
        val h = size.height
        // setengah lingkaran bergaris
        var y = 0f
        var i = 0
        while (y < h * 0.62f) {
            val thickness = h * 0.09f
            drawRect(
                color = stripe,
                topLeft = androidx.compose.ui.geometry.Offset(w * 0.18f + i * 1.5f, y),
                size = androidx.compose.ui.geometry.Size(w * 0.64f - i * 3f, thickness * 0.72f)
            )
            y += thickness
            i++
        }
        // ombak
        val path = androidx.compose.ui.graphics.Path()
        path.moveTo(w * 0.30f, h * 0.80f)
        path.cubicTo(w * 0.36f, h * 0.68f, w * 0.42f, h * 0.92f, w * 0.50f, h * 0.80f)
        path.cubicTo(w * 0.58f, h * 0.68f, w * 0.64f, h * 0.92f, w * 0.70f, h * 0.80f)
        drawPath(path, color = stripe, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f))
    }
}
