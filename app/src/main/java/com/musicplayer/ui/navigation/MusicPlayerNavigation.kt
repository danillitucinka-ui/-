package com.musicplayer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.musicplayer.ui.screens.favorites.FavoritesScreen
import com.musicplayer.ui.screens.home.HomeScreen
import com.musicplayer.ui.screens.nowplaying.NowPlayingScreen
import com.musicplayer.ui.screens.playlists.PlaylistsScreen
import com.musicplayer.ui.screens.search.SearchScreen
import com.musicplayer.ui.screens.settings.SettingsScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Playlists : Screen("playlists")
    object Favorites : Screen("favorites")
    object NowPlaying : Screen("now_playing")
    object Settings : Screen("settings")
}

@Composable
fun MusicPlayerNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToNowPlaying = { navController.navigate(Screen.NowPlaying.route) },
                onNavigateToSearch = { navController.navigate(Screen.Search.route) },
                onNavigateToPlaylists = { navController.navigate(Screen.Playlists.route) },
                onNavigateToFavorites = { navController.navigate(Screen.Favorites.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToNowPlaying = { navController.navigate(Screen.NowPlaying.route) }
            )
        }

        composable(Screen.Playlists.route) {
            PlaylistsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToNowPlaying = { navController.navigate(Screen.NowPlaying.route) }
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToNowPlaying = { navController.navigate(Screen.NowPlaying.route) }
            )
        }

        composable(Screen.NowPlaying.route) {
            NowPlayingScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
