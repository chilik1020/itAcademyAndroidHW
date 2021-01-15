package com.chilik1020.hw10.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.chilik1020.hw10.data.NotificationPlayerAction
import com.chilik1020.hw10.utils.BASE_LOG
import com.chilik1020.hw10.utils.CLOSE_ACT_RESULT
import com.chilik1020.hw10.utils.PENDING_INTENT

class MusicService : Service() {

    private val binder = MusicBinder()
    lateinit var musicNotificationManager: MusicNotificationManager
    lateinit var musicPlayer: MusicPlayerImpl
    private var pendingIntentMainActivity: PendingIntent? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(BASE_LOG, "MusicSERVICE: onCreate")
        musicNotificationManager = MusicNotificationManagerImpl(this)
        musicPlayer = MusicPlayerImpl(applicationContext, musicNotificationManager)
        musicPlayer.init()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleNotificationActions(intent)
        handlePendingFromMainActivity(intent)
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(BASE_LOG, "MusicSERVICE: onBind")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(BASE_LOG, "MusicSERVICE: onUnbind")
        return true
    }

    override fun onDestroy() {
        Log.d(BASE_LOG, "MusicSERVICE: onDestroy")
        super.onDestroy()
    }

    private fun handlePendingFromMainActivity(intent: Intent?) {
        intent?.extras?.getParcelable<PendingIntent>(PENDING_INTENT)?.let {
            pendingIntentMainActivity = it
        }
    }

    private fun handleNotificationActions(intent: Intent?) {
        intent?.action?.let {
            when (it) {
                NotificationPlayerAction.PREVIOUS.name -> musicPlayer.previous()
                NotificationPlayerAction.NEXT.name -> musicPlayer.next()
                NotificationPlayerAction.PLAY_PAUSE.name -> {
                    if (musicPlayer.isPlaying()) musicPlayer.pause() else musicPlayer.play()
                }
                NotificationPlayerAction.DESTROY.name -> {
                    musicNotificationManager.removeNotification()
                    musicPlayer.destroy()
                    pendingIntentMainActivity?.send(CLOSE_ACT_RESULT)
                    this.stopForeground(true)
                    this.stopSelf()
                }
            }
        }
    }

    inner class MusicBinder : Binder() {
        fun getMusicService() = this@MusicService
    }
}