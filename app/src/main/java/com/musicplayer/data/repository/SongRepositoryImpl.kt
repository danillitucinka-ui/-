package com.musicplayer.data.repository

import com.musicplayer.data.dao.SongDao
import com.musicplayer.data.model.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val songDao: SongDao
) : SongRepository {
    override fun getAllSongs(): Flow<List<Song>> = songDao.getAllSongs()

    override fun getSongById(id: Long): Flow<Song?> = songDao.getSongById(id)

    override fun searchSongs(query: String): Flow<List<Song>> = songDao.searchSongs(query)

    override fun getFavoriteSongs(): Flow<List<Song>> = songDao.getFavoriteSongs()

    override fun getSongsByArtist(): Flow<List<Song>> = songDao.getSongsByArtist()

    override fun getSongsByAlbum(): Flow<List<Song>> = songDao.getSongsByAlbum()

    override fun getSongsByGenre(): Flow<List<Song>> = songDao.getSongsByGenre()

    override fun getSongsByDateAdded(): Flow<List<Song>> = songDao.getSongsByDateAdded()

    override fun getAllArtists(): Flow<List<String>> = songDao.getAllArtists()

    override fun getAllAlbums(): Flow<List<String>> = songDao.getAllAlbums()

    override fun getAllGenres(): Flow<List<String>> = songDao.getAllGenres()

    override fun getSongsByArtistName(artist: String): Flow<List<Song>> = songDao.getSongsByArtistName(artist)

    override fun getSongsByAlbumName(album: String): Flow<List<Song>> = songDao.getSongsByAlbumName(album)

    override fun getSongsByGenreName(genre: String): Flow<List<Song>> = songDao.getSongsByGenreName(genre)

    override suspend fun insertSong(song: Song) = songDao.insertSong(song)

    override suspend fun insertSongs(songs: List<Song>) = songDao.insertSongs(songs)

    override suspend fun updateSong(song: Song) = songDao.updateSong(song)

    override suspend fun deleteSong(song: Song) = songDao.deleteSong(song)

    override suspend fun deleteAllSongs() = songDao.deleteAllSongs()

    override suspend fun updateFavoriteStatus(songId: Long, isFavorite: Boolean) = songDao.updateFavoriteStatus(songId, isFavorite)
}
