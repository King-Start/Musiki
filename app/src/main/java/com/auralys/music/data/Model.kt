package com.auralys.music.data

/**
 * Model lagu untuk katalog demo Auralys.
 * [coverColors] adalah pasangan warna ARGB untuk sampul gradien yang digambar
 * secara native (tanpa aset gambar eksternal).
 */
data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val durationSec: Int,
    val url: String,
    val coverColors: List<Long>,
    val genre: String = "Pop"
) {
    val durationLabel: String
        get() = String.format("%d:%02d", durationSec / 60, durationSec % 60)
}

data class Playlist(
    val id: String,
    val title: String,
    val subtitle: String,
    val songIds: List<String>
)

data class MixCard(
    val title: String,
    val artist: String,
    val colors: List<Long>,
    val styleLabel: String? = null
)
