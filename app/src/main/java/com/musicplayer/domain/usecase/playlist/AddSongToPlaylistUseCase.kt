package com.musicplayer.domain.usecase.playlist

import com.musicplayer.data.model.PlaylistSong
import com.musicplayer.data.repository.PlaylistRepository
import javax.inject.Inject

class AddSongToPlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {
    suspend operator fun invoke(playlistId: Long, songId: Long) {
        val maxPosition = playlistRepository.getMaxPositionInPlaylist(playlistId) ?: -1
        val playlistSong = PlaylistSong(
            playlistId = playlistId,
            songId = songId,
            position = maxPosition + 1
        )
        playlistRepository.insertPlaylistSong(playlistSong)
        playlistRepository.updatePlaylistTimestamp(playlistId)
    }
}
