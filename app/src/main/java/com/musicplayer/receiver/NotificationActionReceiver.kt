package com.musicplayer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.media3.session.MediaSession
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActionReceiver : BroadcastReceiver() {

    @Inject
    lateinit var mediaSession: MediaSession

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ACTION_PLAY_PAUSE -> {
                val player = mediaSession.player
                if (player.isPlaying) {
                    player.pause()
                } else {
                    player.play()
                }
            }
            ACTION_NEXT -> {
                mediaSession.player.seekToNextMediaItem()
            }
            ACTION_PREVIOUS -> {
                mediaSession.player.seekToPreviousMediaItem()
            }
        }
    }

    companion object {
        const val ACTION_PLAY_PAUSE = "com.musicplayer.ACTION_PLAY_PAUSE"
        const val ACTION_NEXT = "com.musicplayer.ACTION_NEXT"
        const val ACTION_PREVIOUS = "com.musicplayer.ACTION_PREVIOUS"
    }
}
