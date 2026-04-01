package com.musicplayer.ui.screens.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicplayer.data.model.Playlist
import com.musicplayer.domain.usecase.playlist.CreatePlaylistUseCase
import com.musicplayer.domain.usecase.playlist.DeletePlaylistUseCase
import com.musicplayer.domain.usecase.playlist.GetAllPlaylistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel @Inject constructor(
    private val getAllPlaylistsUseCase: GetAllPlaylistsUseCase,
    private val createPlaylistUseCase: CreatePlaylistUseCase,
    private val deletePlaylistUseCase: DeletePlaylistUseCase
) : ViewModel() {

    val playlists: StateFlow<List<Playlist>> = getAllPlaylistsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _showCreateDialog = MutableStateFlow(false)
    val showCreateDialog: StateFlow<Boolean> = _showCreateDialog.asStateFlow()

    private val _newPlaylistName = MutableStateFlow("")
    val newPlaylistName: StateFlow<String> = _newPlaylistName.asStateFlow()

    private val _selectedPlaylist = MutableStateFlow<Playlist?>(null)
    val selectedPlaylist: StateFlow<Playlist?> = _selectedPlaylist.asStateFlow()

    fun showCreatePlaylistDialog() {
        _showCreateDialog.value = true
        _newPlaylistName.value = ""
    }

    fun hideCreatePlaylistDialog() {
        _showCreateDialog.value = false
        _newPlaylistName.value = ""
    }

    fun setNewPlaylistName(name: String) {
        _newPlaylistName.value = name
    }

    fun createPlaylist() {
        viewModelScope.launch {
            val name = _newPlaylistName.value.trim()
            if (name.isNotEmpty()) {
                createPlaylistUseCase(name)
                hideCreatePlaylistDialog()
            }
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            deletePlaylistUseCase(playlist.id)
        }
    }

    fun selectPlaylist(playlist: Playlist) {
        _selectedPlaylist.value = playlist
    }
}
