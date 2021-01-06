package com.chilik1020.hw10.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.chilik1020.hw10.data.NotificationPlayerAction

class MusicService : Service() {

    private val binder = MusicBinder()
    lateinit var musicNotificationManager: MusicNotificationManager
    lateinit var musicPlayer: MusicPlayerImpl

    override fun onCreate() {
        super.onCreate()
        musicNotificationManager = MusicNotificationManagerImpl(this)
        musicPlayer = MusicPlayerImpl(applicationContext, musicNotificationManager)
        musicPlayer.init()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleNotificationActions(intent)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        musicPlayer.destroy()
        return false
    }

    override fun onDestroy() {
        musicNotificationManager.removeNotification()
        stopForeground(true)
        super.onDestroy()
    }

    private fun handleNotificationActions(intent: Intent?) {
        intent?.action?.let {
            when (it) {
                NotificationPlayerAction.PREVIOUS.name -> musicPlayer.previous()
                NotificationPlayerAction.NEXT.name -> musicPlayer.next()
                NotificationPlayerAction.PLAY_PAUSE.name -> {
                    if (musicPlayer.isPlaying()) musicPlayer.pause() else musicPlayer.play()
                }
            }
        }
    }

    inner class MusicBinder : Binder() {
        fun getMusicService() = this@MusicService
    }
}