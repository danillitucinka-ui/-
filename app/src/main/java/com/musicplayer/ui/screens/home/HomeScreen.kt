package com.musicplayer.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.musicplayer.ui.components.MiniPlayer
import com.musicplayer.ui.components.SongItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToNowPlaying: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToPlaylists: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val songs by viewModel.songs.collectAsState()
    val currentSong by viewModel.currentSong.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Music Player") },
                actions = {
                    IconButton(onClick = onNavigateToSearch) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        bottomBar = {
            if (currentSong != null) {
                MiniPlayer(
                    song = currentSong!!,
                    isPlaying = isPlaying,
                    onPlayPause = { viewModel.playPause() },
                    onClick = onNavigateToNowPlaying
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilterChip(
                    selected = viewModel.sortBy == SortBy.TITLE,
                    onClick = { viewModel.setSortBy(SortBy.TITLE) },
                    label = { Text("Title") }
                )
                FilterChip(
                    selected = viewModel.sortBy == SortBy.ARTIST,
                    onClick = { viewModel.setSortBy(SortBy.ARTIST) },
                    label = { Text("Artist") }
                )
                FilterChip(
                    selected = viewModel.sortBy == SortBy.ALBUM,
                    onClick = { viewModel.setSortBy(SortBy.ALBUM) },
                    label = { Text("Album") }
                )
                FilterChip(
                    selected = viewModel.sortBy == SortBy.DATE_ADDED,
                    onClick = { viewModel.setSortBy(SortBy.DATE_ADDED) },
                    label = { Text("Recent") }
                )
            }

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (songs.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No songs found",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(songs) { song ->
                        SongItem(
                            song = song,
                            onClick = {
                                viewModel.playSong(song)
                                onNavigateToNowPlaying()
                            },
                            onFavoriteClick = { viewModel.toggleFavorite(song) }
                        )
                    }
                }
            }
        }
    }
}
