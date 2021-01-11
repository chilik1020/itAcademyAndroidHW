package com.chilik1020.hw10.service

import android.content.ContentUris
import android.content.Context
import android.media.AudioManager.OnAudioFocusChangeListener
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.os.PowerManager
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import com.chilik1020.hw10.data.Song

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
    override val playerStatus: MutableLiveData<Boolean> = MutableLiveData()

    override fun init() {
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
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    override fun setSong(song: Song) {
        mediaPlayer.reset()
        currentSong = song
        currentSongPosition = playlist.indexOf(currentSong)
        val trackUri = ContentUris.withAppendedId(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            currentSong.id
        )
        currentSongLiveData.value = song
        mediaPlayer.setDataSource(context, trackUri)
        mediaPlayer.prepareAsync()
    }

    override fun setPlayList(list: List<Song>) {
        playlist.apply {
            clear()
            addAll(list)

        }
        currentSong = list.first()
        currentSongPosition = 0
    }

    override fun play() {
        mediaPlayer.start()
        showNotification()
        playerStatus.value = true
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    override fun pause() {
        mediaPlayer.pause()
        showNotification()
        playerStatus.value = false
    }

    override fun next() {
        currentSongPosition++
        if (currentSongPosition >= playlist.size) {
            currentSongPosition = 0
        }
        setSong(playlist[currentSongPosition])
    }

    override fun previous() {
        currentSongPosition--
        if (currentSongPosition < 0) {
            currentSongPosition = playlist.size - 1
        }
        setSong(playlist[currentSongPosition])
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer.start()
        showNotification()
        playerStatus.value = true
    }

    override fun onCompletion(mp: MediaPlayer?) {
        next()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        mediaPlayer.reset()
        return false
    }

    override fun onAudioFocusChange(focusChange: Int) {
        mediaPlayer.pause()
    }

    private fun showNotification() {
        notificationManager.createNotification(mediaPlayer.isPlaying, currentSong)
    }
}