package com.musicplayer.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicplayer.data.model.Song
import com.musicplayer.domain.usecase.song.GetFavoriteSongsUseCase
import com.musicplayer.domain.usecase.song.UpdateFavoriteStatusUseCase
import com.musicplayer.service.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteSongsUseCase: GetFavoriteSongsUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase,
    private val musicPlayerManager: MusicPlayerManager
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val favoriteSongs: StateFlow<List<Song>> = getFavoriteSongsUseCase()
        .onEach { _isLoading.value = false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun playSong(song: Song) {
        val songList = favoriteSongs.value
        val index = songList.indexOf(song)
        if (index != -1) {
            musicPlayerManager.setPlaylist(songList, index)
            musicPlayerManager.play()
        }
    }

    fun toggleFavorite(song: Song) {
        viewModelScope.launch {
            updateFavoriteStatusUseCase(song.id, !song.isFavorite)
        }
    }
}
