# Music Player - Android музыкальный плеер

Современный Android музыкальный плеер с поддержкой воспроизведения локальных аудиофайлов и интуитивным интерфейсом на основе Jetpack Compose.

## Описание

Music Player — это полнофункциональное приложение для воспроизведения музыки на Android устройствах. Приложение поддерживает воспроизведение локальных аудиофайлов (MP3, FLAC, WAV, OGG, AAC) с расширенными возможностями управления воспроизведением, плейлистами и настройками звука.

## Функции

### Воспроизведение аудио
- Воспроизведение локальных аудиофайлов (MP3, FLAC, WAV, OGG, AAC)
- Фоновое воспроизведение с управлением через уведомления и экран блокировки
- Режимы перемешивания (shuffle) и повтора (repeat off, repeat one, repeat all)
- Отображение обложки альбома
- Отображение прогресса воспроизведения с возможностью перемотки

### Управление плейлистами
- Создание и удаление плейлистов
- Добавление и удаление треков из плейлистов
- Просмотр содержимого плейлистов

### Поиск и фильтрация
- Поиск треков по названию, исполнителю или альбому
- Сортировка по названию, исполнителю, альбому или дате добавления
- Фильтрация по жанру

### Избранное
- Добавление треков в избранное
- Просмотр списка избранных треков

### Эквалайзер
- Встроенный эквалайзер с предустановками
- Настройка параметров звука

### Таймер сна
- Автоматическая остановка воспроизведения через заданное время
- Настройка времени таймера (15, 30, 45, 60, 90, 120 минут)

### Интерфейс
- Современный UI на основе Material Design 3
- Поддержка темной и светлой темы
- Плавные анимации и переходы
- Адаптивный дизайн для разных размеров экрана

## Технические детали

### Версии Android
- **minSdk**: 28 (Android 9 Pie)
- **targetSdk**: 34 (Android 14)
- Поддержка версий Android с 9 по 14

### Архитектура
- **MVVM** (Model-View-ViewModel)
- Чистая архитектура с разделением на слои:
  - **Data**: модели данных, DAO, база данных Room, репозитории
  - **Domain**: use cases (бизнес-логика)
  - **Presentation**: ViewModels, UI компоненты на Jetpack Compose
  - **Service**: сервис воспроизведения музыки

### Используемые библиотеки

#### Язык и UI
- **Kotlin** 1.9.22 — основной язык разработки
- **Jetpack Compose** — декларативный UI фреймворк
- **Material Design 3** — компоненты интерфейса
- **Coil** — загрузка изображений

#### Воспроизведение аудио
- **ExoPlayer/Media3** — воспроизведение аудио
  - `media3-exoplayer` 1.2.1
  - `media3-session` 1.2.1
  - `media3-common` 1.2.1

#### База данных
- **Room** — локальная база данных
  - `room-runtime` 2.6.1
  - `room-ktx` 2.6.1
  - `room-compiler` 2.6.1 (KSP)

#### Навигация
- **Jetpack Navigation Compose** — навигация между экранами

#### Внедрение зависимостей
- **Hilt** — внедрение зависимостей
  - `hilt-android` 2.50
  - `hilt-navigation-compose` 1.1.0

#### Асинхронность
- **Kotlin Coroutines** — асинхронное программирование
- **Kotlin Flow** — реактивное программирование

### Структура проекта

```
app/src/main/java/com/musicplayer/
├── data/
│   ├── dao/                    # Data Access Objects (Room)
│   ├── database/               # Room база данных
│   ├── model/                  # Модели данных (Song, Playlist, EqualizerPreset)
│   └── repository/             # Репозитории (SongRepository, PlaylistRepository)
├── di/                         # Hilt модули (DatabaseModule, RepositoryModule, PlayerModule)
├── domain/
│   └── usecase/                # Use cases (бизнес-логика)
│       ├── playlist/           # Операции с плейлистами
│       └── song/               # Операции с треками
├── service/                    # Сервис воспроизведения (MusicPlaybackService, MusicPlayerManager)
├── ui/
│   ├── components/             # Переиспользуемые UI компоненты (SongItem, MiniPlayer)
│   ├── navigation/             # Навигация (MusicPlayerNavigation)
│   ├── screens/                # Экраны приложения
│   │   ├── favorites/          # Экран избранного
│   │   ├── home/               # Главный экран
│   │   ├── nowplaying/         # Экран воспроизведения
│   │   ├── playlists/          # Экран плейлистов
│   │   ├── search/             # Экран поиска
│   │   └── settings/           # Экран настроек
│   └── theme/                  # Тема оформления (Material Design 3)
├── util/                       # Утилиты (PermissionHandler, StorageHelper, ForegroundServiceHelper)
├── MusicPlayerApplication.kt   # Класс приложения (Hilt)
└── MainActivity.kt             # Главная активность
```

### Разрешения приложения

Приложение запрашивает следующие разрешения:

| Разрешение | Версии Android | Назначение |
|------------|----------------|------------|
| `READ_EXTERNAL_STORAGE` | Android 9-12 | Чтение файлов из хранилища |
| `WRITE_EXTERNAL_STORAGE` | Android 9-10 | Запись файлов в хранилище |
| `READ_MEDIA_AUDIO` | Android 13+ | Чтение аудиофайлов |
| `POST_NOTIFICATIONS` | Android 13+ | Отображение уведомлений |
| `FOREGROUND_SERVICE` | Android 9-13 | Фоновое воспроизведение |
| `FOREGROUND_SERVICE_MEDIA_PLAYBACK` | Android 14+ | Фоновое воспроизведение медиа |
| `WAKE_LOCK` | Все версии | Удержание устройства активным |
| `SCHEDULE_EXACT_ALARM` | Android 12+ | Таймер сна |

### Обратная совместимость

Приложение полностью поддерживает Android 9 (API 28) через Android 14 (API 34) с учетом особенностей каждой версии:

- **Android 9-12**: Используется `READ_EXTERNAL_STORAGE` для доступа к файлам
- **Android 10+**: Принудительное использование Scoped Storage через `MediaStore` API
- **Android 12+**: Добавлено разрешение `SCHEDULE_EXACT_ALARM` для таймера сна
- **Android 13+**: Требуются разрешения `READ_MEDIA_AUDIO` и `POST_NOTIFICATIONS`
- **Android 14+**: Требуется разрешение `FOREGROUND_SERVICE_MEDIA_PLAYBACK` для фонового воспроизведения

## Требования к устройству

- Android 9 (Pie) или выше
- Минимум 2 GB оперативной памяти
- Свободное место для хранения аудиофайлов
- Разрешение на доступ к хранилищу (запрашивается при первом запуске)

## Сборка проекта

### Предварительные требования

1. Установите [Android Studio](https://developer.android.com/studio) (последняя стабильная версия)
2. Установите JDK 17 или выше
3. Клонируйте репозиторий

### Инструкция по сборке

1. **Клонирование репозитория**
   ```bash
   git clone https://github.com/yourusername/music-player.git
   cd music-player
   ```

2. **Открытие в Android Studio**
   - Запустите Android Studio
   - Выберите "Open an existing project"
   - Перейдите в клонированную директорию и выберите её

3. **Синхронизация Gradle**
   - Android Studio автоматически синхронизирует файлы Gradle
   - Дождитесь завершения синхронизации

4. **Сборка проекта**
   ```bash
   ./gradlew build
   ```

5. **Запуск на устройстве/эмуляторе**
   - Подключите Android устройство или запустите эмулятор
   - Нажмите "Run" в Android Studio или используйте:
   ```bash
   ./gradlew installDebug
   ```

### Сборка релизной версии

```bash
./gradlew assembleRelease
```

Релизный APK будет создан по пути:
```
app/build/outputs/apk/release/app-release-unsigned.apk
```

### Сборка App Bundle (AAB)

```bash
./gradlew bundleRelease
```

AAB файл будет создан по пути:
```
app/build/outputs/bundle/release/app-release.aab
```

## CI/CD

Проект включает GitHub Actions workflow для автоматической сборки и тестирования:

- **Сборка**: Автоматическая сборка APK и AAB при каждом push и pull request
- **Lint проверки**: Запуск Android lint проверок
- **Unit тесты**: Выполнение модульных тестов
- **Загрузка артефактов**: Загрузка собранных файлов для удобного доступа
- **GitHub Releases**: Автоматическое создание релизов при push в ветку main

## Лицензия

Этот проект распространяется под лицензией MIT — подробности в файле [LICENSE](LICENSE).

## Благодарности

- [Material Design 3](https://m3.material.io/) — дизайн-система
- [ExoPlayer](https://exoplayer.dev/) — воспроизведение аудио
- [Jetpack Compose](https://developer.android.com/jetpack/compose) — UI фреймворк
- [Room](https://developer.android.com/training/data-storage/room) — база данных
- [Hilt](https://dagger.dev/hilt/) — внедрение зависимостей

---

**Примечание**: Для лучшего опыта использования рекомендуется тестировать приложение на физическом устройстве, так как воспроизведение аудио на эмуляторе может иметь ограничения.
