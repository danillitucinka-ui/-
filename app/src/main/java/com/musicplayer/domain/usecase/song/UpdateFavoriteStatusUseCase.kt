package com.musicplayer.domain.usecase.song

import com.musicplayer.data.repository.SongRepository
import javax.inject.Inject

class UpdateFavoriteStatusUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    suspend operator fun invoke(songId: Long, isFavorite: Boolean) {
        songRepository.updateFavoriteStatus(songId, isFavorite)
    }
}
