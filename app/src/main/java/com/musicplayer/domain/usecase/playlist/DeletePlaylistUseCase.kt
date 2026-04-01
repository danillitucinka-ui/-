package com.musicplayer.domain.usecase.playlist

import com.musicplayer.data.repository.PlaylistRepository
import javax.inject.Inject

class DeletePlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {
    suspend operator fun invoke(playlistId: Long) {
        playlistRepository.deletePlaylistById(playlistId)
    }
}
