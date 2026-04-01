package com.musicplayer.domain.usecase.song

import android.content.Context
import com.musicplayer.data.model.Song
import com.musicplayer.data.repository.SongRepository
import com.musicplayer.util.StorageHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LoadSongsFromDeviceUseCase @Inject constructor(
    private val songRepository: SongRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(): List<Song> {
        val songs = StorageHelper.getAllAudioFiles(context)
        if (songs.isNotEmpty()) {
            songRepository.insertSongs(songs)
        }
        return songs
    }
}
