package com.chilik1020.hw10.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.chilik1020.hw10.R
import com.chilik1020.hw10.data.Song
import com.chilik1020.hw10.presentation.MainActivity
import com.chilik1020.hw10.utils.*

class MusicNotificationManager(private val musicService: MusicService) {

    private var notificationManager =
        musicService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private var context: Context = musicService.baseContext

    fun createNotification(isPlaying: Boolean, song: Song) {
        Log.d(BASE_LOG, "MusicService:createNotification")

        val view = RemoteViews(context.packageName, R.layout.notification_player)
        view.setTextViewText(R.id.tvNotificationTrackArtist, song.artistName)
        view.setTextViewText(R.id.tvNotificationTrackTitle, song.title)

        view.setOnClickPendingIntent(
            R.id.ivNotificationNext,
            handleActions(ACTION_NEXT)
        )
        view.setOnClickPendingIntent(
            R.id.ivNotificationPrevious,
            handleActions(ACTION_PREVIOUS)
        )

        when (isPlaying) {
            false -> view.setImageViewResource(R.id.ivNotificationPlayPause, R.drawable.ic_play)
            true -> view.setImageViewResource(R.id.ivNotificationPlayPause, R.drawable.ic_pause)
        }

        view.setOnClickPendingIntent(R.id.ivNotificationPlayPause, handleActions(ACTION_PLAY_PAUSE))

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val contentPendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setShowWhen(false)
            .setSmallIcon(R.drawable.ic_music_note)
            .setContent(view)
            .setContentIntent(contentPendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()

        musicService.startForeground(PLAYER_NOTIFICATION_ID, notification)
    }

    fun removeNotification(id: Int) {
        notificationManager.cancel(id)
    }

    private fun handleActions(playerAction: String): PendingIntent? {
        val intent = Intent(context, MusicService::class.java).apply {
            action = playerAction
        }
        return PendingIntent.getService(context, 0, intent, 0)
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