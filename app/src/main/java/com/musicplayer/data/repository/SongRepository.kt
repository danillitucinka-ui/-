package com.musicplayer.data.repository

import com.musicplayer.data.model.Song
import kotlinx.coroutines.flow.Flow

interface SongRepository {
    fun getAllSongs(): Flow<List<Song>>
    fun getSongById(id: Long): Flow<Song?>
    fun searchSongs(query: String): Flow<List<Song>>
    fun getFavoriteSongs(): Flow<List<Song>>
    fun getSongsByArtist(): Flow<List<Song>>
    fun getSongsByAlbum(): Flow<List<Song>>
    fun getSongsByGenre(): Flow<List<Song>>
    fun getSongsByDateAdded(): Flow<List<Song>>
    fun getAllArtists(): Flow<List<String>>
    fun getAllAlbums(): Flow<List<String>>
    fun getAllGenres(): Flow<List<String>>
    fun getSongsByArtistName(artist: String): Flow<List<Song>>
    fun getSongsByAlbumName(album: String): Flow<List<Song>>
    fun getSongsByGenreName(genre: String): Flow<List<Song>>
    suspend fun insertSong(song: Song)
    suspend fun insertSongs(songs: List<Song>)
    suspend fun updateSong(song: Song)
    suspend fun deleteSong(song: Song)
    suspend fun deleteAllSongs()
    suspend fun updateFavoriteStatus(songId: Long, isFavorite: Boolean)
}
