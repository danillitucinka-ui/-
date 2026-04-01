package com.musicplayer.domain.usecase.playlist

import com.musicplayer.data.model.Playlist
import com.musicplayer.data.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPlaylistsUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {
    operator fun invoke(): Flow<List<Playlist>> = playlistRepository.getAllPlaylists()
}
