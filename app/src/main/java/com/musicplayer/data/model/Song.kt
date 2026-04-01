package com.musicplayer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song(
    @PrimaryKey
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val genre: String?,
    val duration: Long,
    val path: String,
    val albumArtUri: String?,
    val trackNumber: Int?,
    val year: Int?,
    val dateAdded: Long,
    val isFavorite: Boolean = false
)
