package com.chilik1020.hw10.service

import com.chilik1020.hw10.data.Song

interface MusicNotificationManager {
    fun createNotification(isPlaying: Boolean, song: Song)
    fun removeNotification()
}