package com.musicplayer.domain.usecase.song

import com.musicplayer.data.model.Song
import com.musicplayer.data.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSongsUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    operator fun invoke(): Flow<List<Song>> = songRepository.getAllSongs()
}
