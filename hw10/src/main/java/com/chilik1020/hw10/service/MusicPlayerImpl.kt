package com.chilik1020.hw10.service

import android.content.ContentUris
import android.content.Context
import android.media.AudioManager.OnAudioFocusChangeListener
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.os.PowerManager
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chilik1020.hw10.data.Song
import com.chilik1020.hw10.utils.BASE_LOG

class MusicPlayerImpl(
    private val context: Context,
    private val notificationManager: MusicNotificationManager
) : MusicPlayer,
    OnPreparedListener,
    MediaPlayer.OnErrorListener,
    OnCompletionListener,
    OnAudioFocusChangeListener {

    private var mediaPlayer = MediaPlayer()
    private val playlist = mutableListOf<Song>()
    private lateinit var currentSong: Song
    private var currentSongPosition = 0

    override val currentSongLiveData: MutableLiveData<Song> = MutableLiveData()

    override fun init() {
        Log.d(BASE_LOG, "MusicPlayer:init")
        mediaPlayer.apply {
            setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK)
            setOnPreparedListener(this@MusicPlayerImpl)
            setOnCompletionListener(this@MusicPlayerImpl)
            setOnErrorListener(this@MusicPlayerImpl)
            setVolume(1f, 1f)
            isLooping = false
        }
    }

    override fun destroy() {
        Log.d(BASE_LOG, "MusicPlayer:destroy")
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    override fun setSong(song: Song) {
        Log.d(BASE_LOG, "MusicPlayer:setSong")
        mediaPlayer.reset()
        currentSong = song
        currentSongPosition = playlist.indexOf(currentSong)
        val trackUri = ContentUris.withAppendedId(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            currentSong.id
        )
        currentSongLiveData.value = song
        mediaPlayer.setDataSource(context, trackUri)
        mediaPlayer.prepare()
    }

    override fun setPlayList(list: List<Song>) {
        Log.d(BASE_LOG, "MusicPlayer:setPlayList")
        playlist.apply {
            clear()
            addAll(list)
        }
        currentSong = list.first()
        currentSongPosition = 0
    }

    override fun play() {
        Log.d(BASE_LOG, "MusicPlayer:play")
        mediaPlayer.start()
        showNotification()
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    override fun pause() {
        Log.d(BASE_LOG, "MusicPlayer:pause")
        mediaPlayer.pause()
        showNotification()
    }

    override fun next() {
        Log.d(BASE_LOG, "MusicPlayer:next")
        currentSongPosition++
        if (currentSongPosition >= playlist.size) {
            currentSongPosition = 0
        }
        setSong(playlist[currentSongPosition])
    }

    override fun previous() {
        Log.d(BASE_LOG, "MusicPlayer:previous")
        currentSongPosition--
        if (currentSongPosition < 0) {
            currentSongPosition = playlist.size - 1
        }
        setSong(playlist[currentSongPosition])
    }

    override fun onPrepared(mp: MediaPlayer?) {
        Log.d(BASE_LOG, "MusicPlayer:onPrepared")
        mediaPlayer.start()
        showNotification()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        Log.d(BASE_LOG, "MusicPlayer:onCompletion")
        mediaPlayer.pause()
//        next()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Log.d(BASE_LOG, "MusicPlayer:onError")
        mediaPlayer.reset()
        return false
    }

    override fun onAudioFocusChange(focusChange: Int) {
        Log.d(BASE_LOG, "MusicPlayer:onAudioFocusChange")
        mediaPlayer.pause()
    }

    private fun showNotification() {
        notificationManager.createNotification(mediaPlayer.isPlaying, currentSong)
    }
}