package com.musicplayer.ui.screens.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicplayer.data.model.Song
import com.musicplayer.service.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val musicPlayerManager: MusicPlayerManager
) : ViewModel() {

    val currentSong: StateFlow<Song?> = musicPlayerManager.currentSong
    val isPlaying: StateFlow<Boolean> = musicPlayerManager.isPlaying
    val currentPosition: StateFlow<Long> = musicPlayerManager.currentPosition
    val duration: StateFlow<Long> = musicPlayerManager.duration
    val shuffleMode: StateFlow<Boolean> = musicPlayerManager.shuffleMode
    val repeatMode: StateFlow<Int> = musicPlayerManager.repeatMode

    init {
        viewModelScope.launch {
            while (isActive) {
                musicPlayerManager.updateCurrentPosition()
                delay(100)
            }
        }
    }

    fun playPause() {
        musicPlayerManager.playPause()
    }

    fun next() {
        musicPlayerManager.next()
    }

    fun previous() {
        musicPlayerManager.previous()
    }

    fun seekTo(position: Long) {
        musicPlayerManager.seekTo(position)
    }

    fun toggleShuffle() {
        musicPlayerManager.toggleShuffle()
    }

    fun toggleRepeat() {
        musicPlayerManager.toggleRepeat()
    }
}
