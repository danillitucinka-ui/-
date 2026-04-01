package com.musicplayer.domain.usecase.playlist

import com.musicplayer.data.repository.PlaylistRepository
import javax.inject.Inject

class RemoveSongFromPlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {
    suspend operator fun invoke(playlistId: Long, songId: Long) {
        playlistRepository.removeSongFromPlaylist(playlistId, songId)
        playlistRepository.updatePlaylistTimestamp(playlistId)
    }
}
