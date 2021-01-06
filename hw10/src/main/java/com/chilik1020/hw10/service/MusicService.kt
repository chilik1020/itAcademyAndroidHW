package com.chilik1020.hw10.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.chilik1020.hw10.utils.ACTION_NEXT
import com.chilik1020.hw10.utils.ACTION_PLAY_PAUSE
import com.chilik1020.hw10.utils.ACTION_PREVIOUS
import com.chilik1020.hw10.utils.BASE_LOG


class MusicService : Service() {

    private val binder = MusicBinder()
    lateinit var musicNotificationManager: MusicNotificationManager
    lateinit var musicPlayer: MusicPlayerImpl

    override fun onCreate() {
        super.onCreate()
        Log.d(BASE_LOG, "MusicService:onCreate")
        musicNotificationManager = MusicNotificationManagerImpl(this)
        musicPlayer = MusicPlayerImpl(applicationContext, musicNotificationManager)
        musicPlayer.init()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(BASE_LOG, "MusicService:onStartCommand")
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
        if (intent != null) {
            val action = intent.action
            Log.d(BASE_LOG, "MUSICSERVICE:handleNotificationActions: $action")
            if (action != null) {
                when (action) {
                    ACTION_PREVIOUS -> musicPlayer.previous()
                    ACTION_NEXT -> musicPlayer.next()
                    ACTION_PLAY_PAUSE -> {
                        if (musicPlayer.isPlaying()) musicPlayer.pause() else musicPlayer.play()
                    }
                }
            }
        }
    }

    inner class MusicBinder : Binder() {
        fun getMusicService() = this@MusicService
    }
}