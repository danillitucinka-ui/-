package com.musicplayer.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object PermissionHandler {

    /**
     * Check if we have permission to read audio files
     * Android 13+ (API 33+): READ_MEDIA_AUDIO
     * Android 9-12 (API 28-32): READ_EXTERNAL_STORAGE
     */
    fun hasAudioPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_MEDIA_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * Get the required audio permission based on API level
     */
    fun getAudioPermission(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }

    /**
     * Check if we have permission to post notifications
     * Android 13+ (API 33+): POST_NOTIFICATIONS
     * Older versions: No runtime permission needed
     */
    fun hasNotificationPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    /**
     * Get the required notification permission based on API level
     * Returns null if no runtime permission is needed
     */
    fun getNotificationPermission(): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.POST_NOTIFICATIONS
        } else {
            null
        }
    }

    /**
     * Check if we have permission to schedule exact alarms
     * Android 12+ (API 31+): SCHEDULE_EXACT_ALARM
     * Older versions: No permission needed
     */
    fun hasExactAlarmPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.SCHEDULE_EXACT_ALARM
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    /**
     * Get the required exact alarm permission based on API level
     * Returns null if no permission is needed
     */
    fun getExactAlarmPermission(): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Manifest.permission.SCHEDULE_EXACT_ALARM
        } else {
            null
        }
    }

    /**
     * Check if we have all required permissions for the app
     */
    fun hasAllRequiredPermissions(context: Context): Boolean {
        return hasAudioPermission(context) &&
               hasNotificationPermission(context) &&
               hasExactAlarmPermission(context)
    }

    /**
     * Get all required permissions based on API level
     */
    fun getRequiredPermissions(): Array<String> {
        val permissions = mutableListOf<String>()

        // Audio permission
        permissions.add(getAudioPermission())

        // Notification permission (Android 13+)
        getNotificationPermission()?.let { permissions.add(it) }

        // Exact alarm permission (Android 12+)
        getExactAlarmPermission()?.let { permissions.add(it) }

        return permissions.toTypedArray()
    }

    /**
     * Check if we need to request notification permission
     */
    fun shouldRequestNotificationPermission(context: Context): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
               !hasNotificationPermission(context)
    }

    /**
     * Check if we need to request exact alarm permission
     */
    fun shouldRequestExactAlarmPermission(context: Context): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
               !hasExactAlarmPermission(context)
    }
}
