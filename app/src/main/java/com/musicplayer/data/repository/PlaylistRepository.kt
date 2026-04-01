package com.musicplayer.data.repository

import com.musicplayer.data.model.Playlist
import com.musicplayer.data.model.PlaylistSong
import com.musicplayer.data.model.Song
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun getAllPlaylists(): Flow<List<Playlist>>
    fun getPlaylistById(id: Long): Flow<Playlist?>
    fun searchPlaylists(query: String): Flow<List<Playlist>>
    suspend fun insertPlaylist(playlist: Playlist): Long
    suspend fun updatePlaylist(playlist: Playlist)
    suspend fun deletePlaylist(playlist: Playlist)
    suspend fun deletePlaylistById(playlistId: Long)
    suspend fun insertPlaylistSong(playlistSong: PlaylistSong)
    suspend fun insertPlaylistSongs(playlistSongs: List<PlaylistSong>)
    suspend fun removeSongFromPlaylist(playlistId: Long, songId: Long)
    suspend fun removeAllSongsFromPlaylist(playlistId: Long)
    fun getSongsInPlaylist(playlistId: Long): Flow<List<Song>>
    fun getPlaylistSongCount(playlistId: Long): Flow<Int>
    suspend fun getMaxPositionInPlaylist(playlistId: Long): Int?
    suspend fun updatePlaylistTimestamp(playlistId: Long, timestamp: Long = System.currentTimeMillis())
}
