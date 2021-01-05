package com.chilik1020.hw10.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.chilik1020.hw10.R
import com.chilik1020.hw10.utils.BASE_LOG

class NotificationUtil(private val musicService: MusicService) {

    private val CHANNEL_ID = "action.CHANNEL_ID"
    private val REQUEST_CODE = 100
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private var notificationManager =
        musicService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private var context: Context = musicService.baseContext

    fun createNotification(songName: String): Notification {
        Log.d(BASE_LOG, "MusicService:createNotification")

        notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        return notificationBuilder
            .setShowWhen(false)
            .setSmallIcon(R.drawable.ic_music_note)
            .setColor(ContextCompat.getColor(context, R.color.purple_500))
            .setContentTitle(songName)
            .setContentText("Artist")
//            .setContentIntent(contentIntent)
            .addAction(R.drawable.ic_skip_previous, context.getString(R.string.skip_previous), null)
            .addAction(R.drawable.ic_play, context.getString(R.string.play), null)
            .addAction(R.drawable.ic_skip_next, context.getString(R.string.skip_next), null)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }

    fun removeNotification(id: Int) {
        notificationManager.cancel(id)
    }

    @RequiresApi(26)
    private fun createNotificationChannel() {
        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_LOW
            )

            notificationChannel.description = context.getString(R.string.app_name)

            notificationChannel.enableLights(false)
            notificationChannel.enableVibration(false)
            notificationChannel.setShowBadge(false)

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}