package com.musicplayer.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicplayer.data.model.Song
import com.musicplayer.domain.usecase.song.SearchSongsUseCase
import com.musicplayer.domain.usecase.song.UpdateFavoriteStatusUseCase
import com.musicplayer.service.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchSongsUseCase: SearchSongsUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase,
    private val musicPlayerManager: MusicPlayerManager
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    val searchResults: StateFlow<List<Song>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(emptyList())
            } else {
                _isSearching.value = true
                searchSongsUseCase(query)
                    .catch { _isSearching.value = false }
                    .onEach { _isSearching.value = false }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun clearSearch() {
        _searchQuery.value = ""
    }

    fun playSong(song: Song) {
        val songList = searchResults.value
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
