package com.auralys.music.data

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.core.content.ContextCompat
import com.auralys.music.playback.PlaybackService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Repositori pusat: menghubungkan UI dengan MediaController (Media3),
 * serta menyimpan lagu disukai & riwayat lewat Room.
 */
object MusicRepository {

    private val scope = CoroutineScope(Dispatchers.Main.immediate)

    private var controller: MediaController? = null
    private lateinit var db: AppDatabase
    private lateinit var appContext: Context

    private val _ready = MutableStateFlow(false)
    val ready: StateFlow<Boolean> = _ready.asStateFlow()

    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _positionMs = MutableStateFlow(0L)
    val positionMs: StateFlow<Long> = _positionMs.asStateFlow()

    private val _durationMs = MutableStateFlow(0L)
    val durationMs: StateFlow<Long> = _durationMs.asStateFlow()

    private val _shuffleOn = MutableStateFlow(false)
    val shuffleOn: StateFlow<Boolean> = _shuffleOn.asStateFlow()

    private val _repeatAll = MutableStateFlow(true)
    val repeatAll: StateFlow<Boolean> = _repeatAll.asStateFlow()

    private val _likedIds = MutableStateFlow<Set<String>>(emptySet())
    val likedIds: StateFlow<Set<String>> = _likedIds.asStateFlow()

    private val _queue = MutableStateFlow<List<Song>>(emptyList())
    val queue: StateFlow<List<Song>> = _queue.asStateFlow()

    private var pending: Pair<List<Song>, Int>? = null
    private var progressJob: Job? = null

    private val listener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.value = isPlaying
            manageProgressLoop(isPlaying)
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            val song = mediaItem?.mediaId?.let { Catalog.byId(it) }
            _currentSong.value = song
            _durationMs.value = controller?.duration?.coerceAtLeast(0) ?: 0L
            if (controller?.playWhenReady == true) {
                song?.let { recordHistory(it) }
            }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            _durationMs.value = controller?.duration?.coerceAtLeast(0) ?: 0L
        }
    }

    fun init(context: Context) {
        if (_ready.value) return
        appContext = context.applicationContext
        db = AppDatabase.get(appContext)

        scope.launch {
            db.likedDao().observeAll().collect { list ->
                _likedIds.value = list.map { it.songId }.toSet()
            }
        }

        val token = SessionToken(appContext, ComponentName(appContext, PlaybackService::class.java))
        val future = MediaController.Builder(appContext, token).buildAsync()
        future.addListener({
            runCatching {
                controller = future.get()
                controller?.addListener(listener)
                _ready.value = true
                pending?.let { (songs, index) -> playSongs(songs, index) }
                pending = null
                // Pra-muat antrean demo agar bilah pemutar langsung tampil.
                controller?.let { c ->
                    if (c.mediaItemCount == 0) {
                        val songs = Catalog.songs
                        val idx = songs.indexOfFirst { it.id == "s10" }.coerceAtLeast(0)
                        _queue.value = songs
                        c.setMediaItems(buildItems(songs), idx, 0L)
                    }
                }
            }
        }, ContextCompat.getMainExecutor(appContext))
    }

    private fun manageProgressLoop(playing: Boolean) {
        progressJob?.cancel()
        if (playing) {
            progressJob = scope.launch {
                while (true) {
                    controller?.let { _positionMs.value = it.currentPosition.coerceAtLeast(0) }
                    delay(500)
                }
            }
        } else {
            controller?.let { _positionMs.value = it.currentPosition.coerceAtLeast(0) }
        }
    }

    private fun recordHistory(song: Song) {
        scope.launch(Dispatchers.IO) {
            db.historyDao().insert(HistoryEntity(songId = song.id, playedAt = System.currentTimeMillis()))
        }
    }

    private fun buildItems(songs: List<Song>): List<MediaItem> = songs.map { song ->
        MediaItem.Builder()
            .setMediaId(song.id)
            .setMediaMetadata(
                androidx.media3.common.MediaMetadata.Builder()
                    .setTitle(song.title)
                    .setArtist(song.artist)
                    .setAlbumTitle(song.album)
                    .build()
            )
            .setUri(song.url)
            .build()
    }

    fun playSongs(songs: List<Song>, startIndex: Int = 0) {
        if (songs.isEmpty()) return
        _queue.value = songs
        val c = controller
        if (c == null) {
            pending = songs to startIndex
            return
        }
        val items = buildItems(songs)
        c.setMediaItems(items, startIndex.coerceIn(0, items.size - 1), 0L)
        c.prepare()
        c.playWhenReady = true
    }

    fun togglePlay() {
        val c = controller ?: return
        if (c.playWhenReady) c.pause() else {
            if (c.mediaItemCount == 0) {
                playSongs(Catalog.quickPicks, 0)
            } else {
                c.play()
            }
        }
    }

    fun next() = controller?.seekToNextMediaItem()

    fun previous() = controller?.seekToPreviousMediaItem()

    fun seekTo(ms: Long) {
        controller?.seekTo(ms)
        _positionMs.value = ms
    }

    fun toggleShuffle() {
        val newValue = !_shuffleOn.value
        _shuffleOn.value = newValue
        controller?.shuffleModeEnabled = newValue
    }

    fun toggleRepeat() {
        val newValue = !_repeatAll.value
        _repeatAll.value = newValue
        controller?.repeatMode = if (newValue) Player.REPEAT_MODE_ALL else Player.REPEAT_MODE_ONE
    }

    fun toggleLike(songId: String) {
        scope.launch(Dispatchers.IO) {
            if (_likedIds.value.contains(songId)) {
                db.likedDao().delete(songId)
            } else {
                db.likedDao().insert(LikedSongEntity(songId, System.currentTimeMillis()))
            }
        }
    }

    fun likedDao() = db.likedDao()
    fun historyDao() = db.historyDao()
}
