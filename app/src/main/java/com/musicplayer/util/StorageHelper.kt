package com.musicplayer.util

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.musicplayer.data.model.Song
import java.io.File

object StorageHelper {

    /**
     * Check if scoped storage is enforced (Android 10+)
     */
    fun isScopedStorageEnforced(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    }

    /**
     * Get the appropriate URI for accessing audio files
     */
    fun getAudioUri(): Uri {
        return if (isScopedStorageEnforced()) {
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }
    }

    /**
     * Get all audio files from the device
     */
    fun getAllAudioFiles(context: Context): List<Song> {
        val songs = mutableListOf<Song>()
        val contentResolver = context.contentResolver

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.TRACK,
            MediaStore.Audio.Media.YEAR,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.GENRE
        )

        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        val cursor: Cursor? = contentResolver.query(
            getAudioUri(),
            projection,
            selection,
            null,
            sortOrder
        )

        cursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val trackColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TRACK)
            val yearColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR)
            val dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)
            val genreColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.GENRE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn) ?: "Unknown"
                val artist = cursor.getString(artistColumn) ?: "Unknown"
                val album = cursor.getString(albumColumn) ?: "Unknown"
                val duration = cursor.getLong(durationColumn)
                val data = cursor.getString(dataColumn) ?: ""
                val albumId = cursor.getLong(albumIdColumn)
                val track = cursor.getInt(trackColumn)
                val year = cursor.getInt(yearColumn)
                val dateAdded = cursor.getLong(dateAddedColumn)
                val genre = cursor.getString(genreColumn)

                // Get album art URI
                val albumArtUri = getAlbumArtUri(albumId)

                // Check if file exists (for scoped storage, we use content URI)
                val path = if (isScopedStorageEnforced()) {
                    data
                } else {
                    if (File(data).exists()) data else ""
                }

                if (path.isNotEmpty()) {
                    songs.add(
                        Song(
                            id = id,
                            title = title,
                            artist = artist,
                            album = album,
                            genre = genre,
                            duration = duration,
                            path = path,
                            albumArtUri = albumArtUri?.toString(),
                            trackNumber = if (track > 0) track else null,
                            year = if (year > 0) year else null,
                            dateAdded = dateAdded,
                            isFavorite = false
                        )
                    )
                }
            }
        }

        return songs
    }

    /**
     * Get album art URI
     */
    private fun getAlbumArtUri(albumId: Long): Uri? {
        return try {
            ContentUris.withAppendedId(
                Uri.parse("content://media/external/audio/albumart"),
                albumId
            )
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Check if we can access external storage
     */
    fun canAccessExternalStorage(): Boolean {
        return if (isScopedStorageEnforced()) {
            Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        } else {
            Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }
    }

    /**
     * Get the music directory
     */
    fun getMusicDirectory(): File {
        return if (isScopedStorageEnforced()) {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
        } else {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
        }
    }

    /**
     * Check if a file is accessible
     */
    fun isFileAccessible(path: String): Boolean {
        return if (isScopedStorageEnforced()) {
            // For scoped storage, we rely on content resolver
            true
        } else {
            File(path).exists() && File(path).canRead()
        }
    }

    /**
     * Get content URI for a file path
     */
    fun getContentUriForPath(context: Context, path: String): Uri? {
        val projection = arrayOf(MediaStore.Audio.Media._ID)
        val selection = "${MediaStore.Audio.Media.DATA} = ?"
        val selectionArgs = arrayOf(path)

        val cursor = context.contentResolver.query(
            getAudioUri(),
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                return ContentUris.withAppendedId(getAudioUri(), id)
            }
        }

        return null
    }
}
