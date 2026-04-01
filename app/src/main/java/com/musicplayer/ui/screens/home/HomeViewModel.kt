package com.musicplayer.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicplayer.data.model.Song
import com.musicplayer.domain.usecase.song.GetAllSongsUseCase
import com.musicplayer.domain.usecase.song.GetFavoriteSongsUseCase
import com.musicplayer.domain.usecase.song.UpdateFavoriteStatusUseCase
import com.musicplayer.service.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SortBy {
    TITLE, ARTIST, ALBUM, DATE_ADDED
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllSongsUseCase: GetAllSongsUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase,
    private val musicPlayerManager: MusicPlayerManager
) : ViewModel() {

    private val _sortBy = MutableStateFlow(SortBy.TITLE)
    val sortBy: SortBy get() = _sortBy.value

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val songs: StateFlow<List<Song>> = combine(
        getAllSongsUseCase(),
        _sortBy
    ) { songs, sortBy ->
        _isLoading.value = false
        when (sortBy) {
            SortBy.TITLE -> songs.sortedBy { it.title }
            SortBy.ARTIST -> songs.sortedBy { it.artist }
            SortBy.ALBUM -> songs.sortedBy { it.album }
            SortBy.DATE_ADDED -> songs.sortedByDescending { it.dateAdded }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val currentSong: StateFlow<Song?> = musicPlayerManager.currentSong
    val isPlaying: StateFlow<Boolean> = musicPlayerManager.isPlaying

    fun setSortBy(sortBy: SortBy) {
        _sortBy.value = sortBy
    }

    fun playSong(song: Song) {
        val songList = songs.value
        val index = songList.indexOf(song)
        if (index != -1) {
            musicPlayerManager.setPlaylist(songList, index)
            musicPlayerManager.play()
        }
    }

    fun playPause() {
        musicPlayerManager.playPause()
    }

    fun toggleFavorite(song: Song) {
        viewModelScope.launch {
            updateFavoriteStatusUseCase(song.id, !song.isFavorite)
        }
    }
}
