package com.chilik1020.hw10.service

interface MusicPlayer {
    fun init()
    fun destroy()
    fun play()
    fun isPlaying():Boolean
    fun pause()
    fun next()
    fun previous()
    fun stop()
}