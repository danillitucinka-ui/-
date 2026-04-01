package com.musicplayer.domain.usecase.playlist

import com.musicplayer.data.model.Song
import com.musicplayer.data.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSongsInPlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {
    operator fun invoke(playlistId: Long): Flow<List<Song>> = playlistRepository.getSongsInPlaylist(playlistId)
}
