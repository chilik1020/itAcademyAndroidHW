package com.chilik1020.hw10.data

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import com.chilik1020.hw10.utils.BASE_LOG

object SongProvider {
    private val TITLE = 0
    private val TRACK = 1
    private val YEAR = 2
    private val DURATION = 3
    private val ALBUM = 4
    private val ARTIST_ID = 5
    private val ARTIST = 6
    private val ID = 7

    private val BASE_PROJECTION = arrayOf(
        MediaStore.Audio.AudioColumns.TITLE,
        MediaStore.Audio.AudioColumns.TRACK,
        MediaStore.Audio.AudioColumns.YEAR,
        MediaStore.Audio.AudioColumns.DURATION,
        MediaStore.Audio.AudioColumns.ALBUM,
        MediaStore.Audio.AudioColumns.ARTIST_ID,
        MediaStore.Audio.AudioColumns.ARTIST,
        MediaStore.Audio.AudioColumns._ID
    )// 7

    private val mAllDeviceSongs = ArrayList<Song>()

    fun getAllDeviceSongs(context: Context): MutableList<Song> {
        val cursor = makeSongCursor(context)
        return getSongs(cursor)
    }


    private fun getSongs(cursor: Cursor?): MutableList<Song> {
        val songs = ArrayList<Song>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val song = getSongFromCursorImpl(cursor)
                if (song.duration in 30000..499999) {
                    songs.add(song)
                    mAllDeviceSongs.add(song)
                }
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return songs
    }


    private fun getSongFromCursorImpl(cursor: Cursor): Song {
        val title = cursor.getString(TITLE)
        val trackNumber = cursor.getInt(TRACK)
        val year = cursor.getInt(YEAR)
        val duration = cursor.getInt(DURATION)
        val albumName = cursor.getString(ALBUM)
        val artistId = cursor.getInt(ARTIST_ID)
        val artistName = cursor.getString(ARTIST)
        val id = cursor.getLong(ID)

        return Song(
            id,
            title,
            trackNumber,
            year,
            duration,
            albumName,
            artistId,
            artistName,
            false
        )
    }

    private fun makeSongCursor(context: Context): Cursor? {
        return try {
            context.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                BASE_PROJECTION, null, null, null
            )
        } catch (e: SecurityException) {
            Log.d(BASE_LOG, "Error ContentResolver Query")
            null
        }

    }
}