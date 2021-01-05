package com.chilik1020.hw10.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.chilik1020.hw10.utils.BASE_LOG
import com.chilik1020.hw10.utils.SONG_NAME_KEY


class MusicService : Service(), ServiceActions {

    private val binder = MusicBinder()
    lateinit var notificationUtil: NotificationUtil
    lateinit var musicPlayer: MusicPlayerImpl
    private var songFile: String? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(BASE_LOG, "MusicService:onCreate")
        notificationUtil = NotificationUtil(this)
        musicPlayer = MusicPlayerImpl(applicationContext)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(BASE_LOG, "MusicService:onStartCommand")
        intent?.let { songFile = it.extras?.getString(SONG_NAME_KEY) }
        val notification = notificationUtil.createNotification("Song")
        startForeground(11, notification)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onDestroy() {
        notificationUtil.removeNotification(11)
        musicPlayer.destroy()
        super.onDestroy()
    }

    override fun getData(): Int {
        TODO("Not yet implemented")
    }


    inner class MusicBinder : Binder() {
        fun getMusicService() = this@MusicService
    }
}