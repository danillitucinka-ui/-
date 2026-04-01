package com.musicplayer.domain.usecase.song

import com.musicplayer.data.model.Song
import com.musicplayer.data.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchSongsUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    operator fun invoke(query: String): Flow<List<Song>> = songRepository.searchSongs(query)
}
