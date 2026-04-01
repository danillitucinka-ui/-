package com.musicplayer.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

object ForegroundServiceHelper {

    private const val CHANNEL_ID = "music_playback_channel"
    private const val CHANNEL_NAME = "Music Playback"
    private const val NOTIFICATION_ID = 1

    /**
     * Check if foreground service type is required (Android 14+)
     */
    fun isForegroundServiceTypeRequired(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
    }

    /**
     * Create notification channel for music playback
     */
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Music playback controls"
                setShowBadge(false)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Build a notification for music playback
     */
    fun buildNotification(
        context: Context,
        title: String,
        artist: String,
        isPlaying: Boolean,
        playPauseAction: Notification.Action,
        nextAction: Notification.Action,
        previousAction: Notification.Action
    ): Notification {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(artist)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(isPlaying)
            .setShowWhen(false)
            .addAction(previousAction)
            .addAction(playPauseAction)
            .addAction(nextAction)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
            )

        return builder.build()
    }

    /**
     * Get the notification channel ID
     */
    fun getChannelId(): String = CHANNEL_ID

    /**
     * Get the notification ID
     */
    fun getNotificationId(): Int = NOTIFICATION_ID

    /**
     * Check if we can start foreground service
     */
    fun canStartForegroundService(context: Context): Boolean {
        return if (isForegroundServiceTypeRequired()) {
            // For Android 14+, we need to check if we have the right permissions
            PermissionHandler.hasNotificationPermission(context)
        } else {
            true
        }
    }

    /**
     * Get the foreground service type for Android 14+
     */
    fun getForegroundServiceType(): Int {
        return if (isForegroundServiceTypeRequired()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            } else {
                0
            }
        } else {
            0
        }
    }
}
