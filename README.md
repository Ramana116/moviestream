# 🎬 Kotlin Movie Streaming Mobile Application

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=flat&logo=android&logoColor=white)
![API](https://img.shields.io/badge/API-21+-blue)
![Status](https://img.shields.io/badge/Status-Active-brightgreen)

## 🎥 Overview

A modern Android streaming application built with Kotlin, featuring movie browsing, user authentication, watchlist management, and video playback capabilities. Demonstrates advanced Android development patterns and third-party API integration.

### 🎯 Key Features

✅ **Movie Discovery**
- Browse trending and popular movies
- Search functionality with filters
- Category browsing and recommendations
- Movie details with ratings and reviews
- Cast and crew information

✅ **User Account Management**
- Secure user authentication
- User profiles and preferences
- Watchlist and favorites management
- View history tracking
- Personalized recommendations

✅ **Video Playback**
- Integrated video player
- Quality selection
- Offline download support
- Resume playback functionality
- Playback statistics

✅ **Modern Architecture**
- MVVM design pattern
- Repository pattern for data access
- Dependency injection with Hilt
- Coroutines for async operations
- LiveData for reactive updates

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|------------|
| **Language** | Kotlin |
| **Platform** | Android 5.0+ (API 21) |
| **Architecture** | MVVM |
| **Networking** | Retrofit + OkHttp |
| **Database** | Room |
| **Image Loading** | Glide |
| **Video Player** | ExoPlayer |
| **API** | TMDB Movie API |
| **Dependency Injection** | Hilt |

---

## 🚀 Installation & Setup

### Prerequisites
- Android Studio 4.0+
- Kotlin 1.4+
- API 21+ device or emulator
- TMDB API key (get from [themoviedb.org](https://www.themoviedb.org/settings/api))

### Configuration

```bash
# Clone repository
git clone https://github.com/Ramana116/moviestream.git
cd moviestream

# Add API key to local.properties
echo "tmdb.api.key=YOUR_API_KEY_HERE" >> local.properties
```

### Build & Run

```bash
# Build the project
./gradlew build

# Run on emulator
./gradlew installDebug

# Create release APK
./gradlew assembleRelease
```

---

## 📁 Project Structure

```
moviestream/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/ramana/moviestream/
│   │   │   │   ├── ui/
│   │   │   │   │   ├── MainActivity.kt
│   │   │   │   │   ├── screens/
│   │   │   │   │   │   ├── HomeScreen.kt
│   │   │   │   │   │   ├── MovieDetailsScreen.kt
│   │   │   │   │   │   ├── SearchScreen.kt
│   │   │   │   │   │   ├── WatchlistScreen.kt
│   │   │   │   │   │   └── PlayerScreen.kt
│   │   │   │   │   │
│   │   │   │   │   └── adapters/
│   │   │   │   │       ├── MovieAdapter.kt
│   │   │   │   │       ├── CastAdapter.kt
│   │   │   │   │       └── ReviewAdapter.kt
│   │   │   │   │
│   │   │   │   ├── viewmodel/
│   │   │   │   │   ├── MovieViewModel.kt
│   │   │   │   │   ├── SearchViewModel.kt
│   │   │   │   │   └── PlayerViewModel.kt
│   │   │   │   │
│   │   │   │   ├── repository/
│   │   │   │   │   ├── MovieRepository.kt
│   │   │   │   │   └── UserRepository.kt
│   │   │   │   │
│   │   │   │   ├── api/
│   │   │   │   │   ├── TmdbApiService.kt
│   │   │   │   │   └── ApiInterceptor.kt
│   │   │   │   │
│   │   │   │   ├── database/
│   │   │   │   │   ├── AppDatabase.kt
│   │   │   │   │   ├── MovieDao.kt
│   │   │   │   │   ├── WatchlistDao.kt
│   │   │   │   │   └── models/
│   │   │   │   │       ├── Movie.kt
│   │   │   │   │       ├── Cast.kt
│   │   │   │   │       └── Review.kt
│   │   │   │   │
│   │   │   │   ├── utils/
│   │   │   │   │   ├── Constants.kt
│   │   │   │   │   ├── DateUtils.kt
│   │   │   │   │   └── ImageUtils.kt
│   │   │   │   │
│   │   │   │   └── di/
│   │   │   │       ├── NetworkModule.kt
│   │   │   │       └── RepositoryModule.kt
│   │   │   │
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── fragment_home.xml
│   │   │   │   │   ├── fragment_movie_details.xml
│   │   │   │   │   ├── fragment_player.xml
│   │   │   │   │   └── item_movie.xml
│   │   │   │   │
│   │   │   │   ├── values/
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   └── styles.xml
│   │   │   │   │
│   │   │   │   └── drawable/
│   │   │   │
│   │   │   └── AndroidManifest.xml
│   │   │
│   │   └── test/
│   │       ├── MovieViewModelTest.kt
│   │       ├── MovieRepositoryTest.kt
│   │       └── TmdbApiServiceTest.kt
│   │
│   ├── build.gradle
│   └── proguard-rules.pro
│
├── build.gradle
├── settings.gradle
└── README.md
```

---

## 📚 API Integration

### TMDB API Endpoints Used

```kotlin
// Get trending movies
GET /trending/movie/week

// Get movie details
GET /movie/{movie_id}

// Get cast information
GET /movie/{movie_id}/credits

// Search movies
GET /search/movie?query={search_query}

// Get user ratings
GET /account/{account_id}/rated/movies
```

---

## 💻 Core Features Implementation

### 1. Movie Discovery
```kotlin
// Fetch trending movies
fun getTrendingMovies(): LiveData<List<Movie>> {
    return movieRepository.getTrendingMovies()
}

// Search movies
fun searchMovies(query: String): LiveData<List<Movie>> {
    return movieRepository.searchMovies(query)
}
```

### 2. User Authentication
```kotlin
// Secure login
fun login(email: String, password: String) {
    viewModelScope.launch {
        userRepository.authenticateUser(email, password)
    }
}
```

### 3. Watchlist Management
```kotlin
// Add to watchlist
fun addToWatchlist(movie: Movie) {
    viewModelScope.launch {
        watchlistRepository.addMovie(movie)
    }
}
```

### 4. Video Playback
```kotlin
// Initialize player
fun initializePlayer(movieUrl: String) {
    player = SimpleExoPlayer.Builder(context).build()
    player.setMediaSource(mediaSource)
    player.prepare()
}
```

---

## 🧪 Testing

```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest

# Test coverage
./gradlew testDebugUnitTestCoverage
```

---

## 🔒 Security Features

- ✅ Secure API key management
- ✅ SSL/TLS encryption
- ✅ Token-based authentication
- ✅ Local data encryption
- ✅ ProGuard code obfuscation

---

## 📈 Performance

- Network request optimization with caching
- Image loading optimization with Glide
- Video buffering and streaming quality selection
- Memory management and leak prevention
- Smooth animations and transitions

---

## 📱 Screenshots

[Add screenshots showing:]
- Home screen with trending movies
- Movie detail view
- Video player interface
- Search results
- Watchlist

---

## 🚀 Release Build

```bash
# Generate release APK
./gradlew assembleRelease

# Generate signed AAB for Play Store
./gradlew bundleRelease -PversionCode=1 -PversionName=1.0.0
```

---

## 📝 License

MIT License - see [LICENSE](LICENSE) for details.

---

## 👤 Author

**Ramana** - Mobile Developer
- GitHub: [@Ramana116](https://github.com/Ramana116)

---

## 🎓 Learning Outcomes

This project demonstrates:
- ✅ Advanced Kotlin programming
- ✅ Android Jetpack best practices
- ✅ Third-party API integration
- ✅ ExoPlayer video streaming
- ✅ MVVM architecture patterns
- ✅ Hilt dependency injection
- ✅ Material Design principles

---

## 🚀 Future Enhancements

- [ ] Offline download capability
- [ ] Offline playback
- [ ] Social sharing features
- [ ] Multi-profile support
- [ ] Parental controls
- [ ] Push notifications
- [ ] In-app purchases

---

**Last Updated:** June 2026
**Status:** Production Ready ✅
