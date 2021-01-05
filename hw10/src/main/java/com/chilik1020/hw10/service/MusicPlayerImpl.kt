package com.chilik1020.hw10.service

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.util.Log
import com.chilik1020.hw10.utils.BASE_LOG

class MusicPlayerImpl(private val context: Context) : MusicPlayer {

    private var mediaPlayer = MediaPlayer()

    init {
        init()
    }

    override fun init() {
        Log.d(BASE_LOG, "MusicPlayer:init")
        try {
            mediaPlayer = MediaPlayer()
            val descriptor: AssetFileDescriptor =
                context.getAssets().openFd("Rammstein - Mein Herz Brennt.mp3")
            mediaPlayer.setDataSource(
                descriptor.fileDescriptor,
                descriptor.startOffset,
                descriptor.length
            )
            descriptor.close()
            mediaPlayer.prepare()
            mediaPlayer.setVolume(1f, 1f)
            mediaPlayer.isLooping = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun destroy() {
        Log.d(BASE_LOG, "MusicPlayer:destroy")
    }

    override fun play() {
        Log.d(BASE_LOG, "MusicPlayer:play")
        mediaPlayer.start()
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    override fun pause() {
        Log.d(BASE_LOG, "MusicPlayer:pause")
        mediaPlayer.pause()
    }

    override fun next() {
        Log.d(BASE_LOG, "MusicPlayer:next")
    }

    override fun previous() {
        Log.d(BASE_LOG, "MusicPlayer:previous")
    }

    override fun stop() {
        Log.d(BASE_LOG, "MusicPlayer:stop")
    }

    private fun playAssetSound(context: Context, soundFileName: String) {
        try {
            mediaPlayer = MediaPlayer()
            val descriptor: AssetFileDescriptor = context.getAssets().openFd(soundFileName)
            mediaPlayer.setDataSource(
                descriptor.fileDescriptor,
                descriptor.startOffset,
                descriptor.length
            )
            descriptor.close()
            mediaPlayer.prepare()
            mediaPlayer.setVolume(1f, 1f)
            mediaPlayer.isLooping = false
            mediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}