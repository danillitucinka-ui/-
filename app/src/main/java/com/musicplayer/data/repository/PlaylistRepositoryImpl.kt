package com.musicplayer.data.repository

import com.musicplayer.data.dao.PlaylistDao
import com.musicplayer.data.model.Playlist
import com.musicplayer.data.model.PlaylistSong
import com.musicplayer.data.model.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val playlistDao: PlaylistDao
) : PlaylistRepository {
    override fun getAllPlaylists(): Flow<List<Playlist>> = playlistDao.getAllPlaylists()

    override fun getPlaylistById(id: Long): Flow<Playlist?> = playlistDao.getPlaylistById(id)

    override fun searchPlaylists(query: String): Flow<List<Playlist>> = playlistDao.searchPlaylists(query)

    override suspend fun insertPlaylist(playlist: Playlist): Long = playlistDao.insertPlaylist(playlist)

    override suspend fun updatePlaylist(playlist: Playlist) = playlistDao.updatePlaylist(playlist)

    override suspend fun deletePlaylist(playlist: Playlist) = playlistDao.deletePlaylist(playlist)

    override suspend fun deletePlaylistById(playlistId: Long) = playlistDao.deletePlaylistById(playlistId)

    override suspend fun insertPlaylistSong(playlistSong: PlaylistSong) = playlistDao.insertPlaylistSong(playlistSong)

    override suspend fun insertPlaylistSongs(playlistSongs: List<PlaylistSong>) = playlistDao.insertPlaylistSongs(playlistSongs)

    override suspend fun removeSongFromPlaylist(playlistId: Long, songId: Long) = playlistDao.removeSongFromPlaylist(playlistId, songId)

    override suspend fun removeAllSongsFromPlaylist(playlistId: Long) = playlistDao.removeAllSongsFromPlaylist(playlistId)

    override fun getSongsInPlaylist(playlistId: Long): Flow<List<Song>> = playlistDao.getSongsInPlaylist(playlistId)

    override fun getPlaylistSongCount(playlistId: Long): Flow<Int> = playlistDao.getPlaylistSongCount(playlistId)

    override suspend fun getMaxPositionInPlaylist(playlistId: Long): Int? = playlistDao.getMaxPositionInPlaylist(playlistId)

    override suspend fun updatePlaylistTimestamp(playlistId: Long, timestamp: Long) = playlistDao.updatePlaylistTimestamp(playlistId, timestamp)
}
