package com.musicplayer.domain.usecase.playlist

import com.musicplayer.data.model.Playlist
import com.musicplayer.data.repository.PlaylistRepository
import javax.inject.Inject

class CreatePlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {
    suspend operator fun invoke(name: String): Long {
        val playlist = Playlist(name = name)
        return playlistRepository.insertPlaylist(playlist)
    }
}
