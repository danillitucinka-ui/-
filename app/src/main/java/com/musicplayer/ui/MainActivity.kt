package com.musicplayer.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.musicplayer.ui.navigation.MusicPlayerNavigation
import com.musicplayer.ui.theme.MusicPlayerTheme
import com.musicplayer.util.PermissionHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (allGranted) {
            // All permissions granted, proceed with app initialization
            initializeApp()
        } else {
            // Some permissions denied, handle accordingly
            handlePermissionDenied()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check and request permissions
        if (PermissionHandler.hasAllRequiredPermissions(this)) {
            initializeApp()
        } else {
            requestPermissions()
        }
    }

    private fun initializeApp() {
        setContent {
            MusicPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MusicPlayerNavigation()
                }
            }
        }
    }

    private fun requestPermissions() {
        val permissions = PermissionHandler.getRequiredPermissions()
        permissionLauncher.launch(permissions)
    }

    private fun handlePermissionDenied() {
        // Show a message or handle denied permissions
        // For now, we'll still initialize the app but some features may not work
        initializeApp()
    }

    /**
     * Check if we have a specific permission
     */
    fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Check if we should show permission rationale
     */
    fun shouldShowRationale(permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRequestPermissionRationale(permission)
        } else {
            false
        }
    }
}
