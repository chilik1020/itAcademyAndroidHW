package com.chilik1020.hw10.service

import androidx.lifecycle.MutableLiveData
import com.chilik1020.hw10.data.Song

interface MusicPlayer {
    val currentSongLiveData: MutableLiveData<Song>

    fun init()
    fun destroy()
    fun setSong(song: Song)
    fun setPlayList(list: List<Song>)
    fun play()
    fun isPlaying(): Boolean
    fun pause()
    fun next()
    fun previous()
}