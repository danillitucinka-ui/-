package com.musicplayer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.musicplayer.data.dao.EqualizerPresetDao
import com.musicplayer.data.dao.PlaylistDao
import com.musicplayer.data.dao.SongDao
import com.musicplayer.data.model.EqualizerPreset
import com.musicplayer.data.model.Playlist
import com.musicplayer.data.model.PlaylistSong
import com.musicplayer.data.model.Song

@Database(
    entities = [
        Song::class,
        Playlist::class,
        PlaylistSong::class,
        EqualizerPreset::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun equalizerPresetDao(): EqualizerPresetDao

    companion object {
        const val DATABASE_NAME = "music_player_db"
    }
}
