package com.musicplayer.service

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.musicplayer.data.model.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicPlayerManager @Inject constructor(
    private val player: ExoPlayer
) {
    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _shuffleMode = MutableStateFlow(false)
    val shuffleMode: StateFlow<Boolean> = _shuffleMode.asStateFlow()

    private val _repeatMode = MutableStateFlow(Player.REPEAT_MODE_OFF)
    val repeatMode: StateFlow<Int> = _repeatMode.asStateFlow()

    private val _playlist = MutableStateFlow<List<Song>>(emptyList())
    val playlist: StateFlow<List<Song>> = _playlist.asStateFlow()

    private val _currentIndex = MutableStateFlow(-1)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    init {
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                updateCurrentSong()
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    _duration.value = player.duration
                }
            }
        })
    }

    fun setPlaylist(songs: List<Song>, startIndex: Int = 0) {
        _playlist.value = songs
        val mediaItems = songs.map { song ->
            MediaItem.Builder()
                .setUri(song.path)
                .setMediaId(song.id.toString())
                .build()
        }
        player.setMediaItems(mediaItems, startIndex, 0L)
        player.prepare()
        updateCurrentSong()
    }

    fun play() {
        player.play()
    }

    fun pause() {
        player.pause()
    }

    fun playPause() {
        if (player.isPlaying) {
            pause()
        } else {
            play()
        }
    }

    fun next() {
        if (player.hasNextMediaItem()) {
            player.seekToNextMediaItem()
        }
    }

    fun previous() {
        if (player.hasPreviousMediaItem()) {
            player.seekToPreviousMediaItem()
        }
    }

    fun seekTo(position: Long) {
        player.seekTo(position)
    }

    fun toggleShuffle() {
        val newMode = !player.shuffleModeEnabled
        player.shuffleModeEnabled = newMode
        _shuffleMode.value = newMode
    }

    fun toggleRepeat() {
        val newMode = when (player.repeatMode) {
            Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ONE
            Player.REPEAT_MODE_ONE -> Player.REPEAT_MODE_ALL
            Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_OFF
            else -> Player.REPEAT_MODE_OFF
        }
        player.repeatMode = newMode
        _repeatMode.value = newMode
    }

    fun updateCurrentPosition() {
        _currentPosition.value = player.currentPosition
        _duration.value = player.duration
    }

    private fun updateCurrentSong() {
        val currentIndex = player.currentMediaItemIndex
        _currentIndex.value = currentIndex
        if (currentIndex in _playlist.value.indices) {
            _currentSong.value = _playlist.value[currentIndex]
        }
    }

    fun release() {
        player.release()
    }
}
