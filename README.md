# Music Player - Android Application

A fully functional Android music player application with a modern, intuitive, and user-friendly interface built using Kotlin and Jetpack Compose.

## Features

### Core Features
- **Local Audio Playback**: Support for MP3, FLAC, WAV, OGG, and AAC formats
- **Playlist Management**: Create, edit, and delete playlists
- **Shuffle & Repeat Modes**: Shuffle playback and repeat (off, one, all)
- **Equalizer**: Built-in equalizer with presets
- **Album Art Display**: High-quality album art display
- **Background Playback**: Continue playback with lock screen and notification controls
- **Search & Filter**: Search songs by title, artist, or album
- **Sorting**: Sort by artist, album, genre, title, or date added
- **Sleep Timer**: Set a timer to stop playback automatically
- **Favorites/Bookmarks**: Mark songs as favorites for quick access

### UI/UX Features
- **Material Design 3**: Modern UI following Material Design 3 guidelines
- **Dark/Light Theme**: Support for both dark and light themes
- **Smooth Animations**: Fluid transitions and animations
- **Responsive Layout**: Optimized for various screen sizes
- **Intuitive Navigation**: Easy-to-use navigation with bottom navigation bar

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt
- **Audio Playback**: ExoPlayer/Media3
- **Database**: Room
- **Image Loading**: Coil
- **Navigation**: Jetpack Navigation Compose
- **Coroutines**: Kotlin Coroutines & Flow

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/musicplayer/
│   │   │   ├── data/
│   │   │   │   ├── dao/           # Room DAOs
│   │   │   │   ├── database/      # Room database
│   │   │   │   ├── model/         # Data models
│   │   │   │   └── repository/    # Repository implementations
│   │   │   ├── di/                # Hilt dependency injection
│   │   │   ├── domain/
│   │   │   │   └── usecase/       # Use cases
│   │   │   ├── service/           # Music playback service
│   │   │   ├── ui/
│   │   │   │   ├── components/    # Reusable UI components
│   │   │   │   ├── navigation/    # Navigation setup
│   │   │   │   ├── screens/       # App screens
│   │   │   │   └── theme/         # Material Design theme
│   │   │   ├── MusicPlayerApplication.kt
│   │   │   └── MainActivity.kt
│   │   └── res/                   # Resources
│   └── test/                      # Unit tests
└── build.gradle.kts
```

## Screenshots

### Home Screen
![Home Screen](screenshots/home.png)

### Now Playing
![Now Playing](screenshots/now_playing.png)

### Playlists
![Playlists](screenshots/playlists.png)

### Search
![Search](screenshots/search.png)

### Settings
![Settings](screenshots/settings.png)

## Requirements

- Android SDK 28+ (Android 9 Pie or higher)
- Target SDK 34 (Android 14)
- Kotlin 1.9.22+
- Gradle 8.2.2+
- JDK 17

## Setup Instructions

### Prerequisites

1. Install [Android Studio](https://developer.android.com/studio) (latest stable version)
2. Install JDK 17 or higher
3. Clone this repository

### Building from Source

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/music-player.git
   cd music-player
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory and select it

3. **Sync Gradle**
   - Android Studio will automatically sync Gradle files
   - Wait for the sync to complete

4. **Build the project**
   ```bash
   ./gradlew build
   ```

5. **Run on device/emulator**
   - Connect an Android device or start an emulator
   - Click "Run" in Android Studio or use:
   ```bash
   ./gradlew installDebug
   ```

### Building Release APK

```bash
./gradlew assembleRelease
```

The release APK will be generated at:
```
app/build/outputs/apk/release/app-release-unsigned.apk
```

### Building App Bundle (AAB)

```bash
./gradlew bundleRelease
```

The AAB will be generated at:
```
app/build/outputs/bundle/release/app-release.aab
```

## CI/CD

This project includes GitHub Actions workflows for automated building and testing:

- **Build Workflow**: Automatically builds APK and AAB on every push and pull request
- **Lint Checks**: Runs Android lint checks
- **Unit Tests**: Executes unit tests
- **Artifact Upload**: Uploads build artifacts for easy access
- **GitHub Releases**: Automatically creates releases on main branch pushes

### Workflow Configuration

The CI/CD workflow is located at `.github/workflows/android-build.yml` and includes:

- Lint checks
- Unit tests
- Debug APK build
- Release APK build
- Release AAB build
- Artifact upload
- GitHub Release creation (on main branch)

## Permissions

The app requires the following permissions:

- `READ_MEDIA_AUDIO`: Read audio files from device storage (Android 13+)
- `READ_EXTERNAL_STORAGE`: Read external storage (Android 9-12)
- `FOREGROUND_SERVICE`: Run music playback in foreground (Android 9-13)
- `FOREGROUND_SERVICE_MEDIA_PLAYBACK`: Media playback foreground service (Android 14+)
- `POST_NOTIFICATIONS`: Show playback notifications (Android 13+)
- `WAKE_LOCK`: Keep device awake during playback
- `SCHEDULE_EXACT_ALARM`: Schedule exact alarms for sleep timer (Android 12+)

## Backward Compatibility

This app supports Android 9 (API 28) through Android 14 (API 34) with full backward compatibility:

### Version-Specific Handling

- **Android 9 (API 28)**: Uses `READ_EXTERNAL_STORAGE` permission for file access
- **Android 10 (API 29)**: Enforces scoped storage, uses `MediaStore` API for file access
- **Android 11 (API 30)**: Continues scoped storage enforcement
- **Android 12 (API 31)**: Adds `SCHEDULE_EXACT_ALARM` permission for sleep timer
- **Android 13 (API 33)**: Requires `READ_MEDIA_AUDIO` and `POST_NOTIFICATIONS` permissions
- **Android 14 (API 34)**: Requires `FOREGROUND_SERVICE_MEDIA_PLAYBACK` permission for foreground services

### Utility Classes

- **PermissionHandler**: Manages runtime permissions across all Android versions
- **StorageHelper**: Handles scoped storage for Android 10+ with fallback for older versions
- **ForegroundServiceHelper**: Manages foreground service restrictions for Android 14+

## Architecture

This project follows the **MVVM (Model-View-ViewModel)** architecture pattern with clean architecture principles:

### Data Layer
- **Models**: Data classes representing songs, playlists, etc.
- **DAOs**: Data Access Objects for Room database operations
- **Repositories**: Abstract data access and provide clean API
- **Database**: Room database for local data persistence

### Domain Layer
- **Use Cases**: Business logic encapsulated in single-responsibility classes

### Presentation Layer
- **ViewModels**: Manage UI state and business logic
- **Screens**: Composable functions for UI
- **Components**: Reusable UI components

### Service Layer
- **MusicPlaybackService**: Media3 MediaSessionService for background playback
- **MusicPlayerManager**: Manages ExoPlayer and playback state

## Dependencies

### Core
- AndroidX Core KTX
- Lifecycle Runtime KTX
- Activity Compose

### Compose
- Compose BOM
- Material3
- Material Icons Extended
- Navigation Compose

### Dependency Injection
- Hilt Android
- Hilt Navigation Compose

### Database
- Room Runtime
- Room KTX
- Room Compiler (KSP)

### Audio Playback
- Media3 ExoPlayer
- Media3 Session
- Media3 Common

### Image Loading
- Coil Compose

### Coroutines
- Kotlin Coroutines Android

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [Material Design 3](https://m3.material.io/) for design guidelines
- [ExoPlayer](https://exoplayer.dev/) for audio playback
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for modern UI
- [Room](https://developer.android.com/training/data-storage/room) for database
- [Hilt](https://dagger.dev/hilt/) for dependency injection

## Contact

For questions or feedback, please open an issue on GitHub.

---

**Note**: This is a fully functional music player application. Make sure to test on a physical device for the best experience, as emulator audio playback may have limitations.
