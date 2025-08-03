# MIOCard - Transport Card Manager

A modern Android application for managing transport cards built with Jetpack Compose, Room database, and clean architecture principles.

## Features

- **Card Management**: Create, view, and manage transport cards with ID, prefix, suffix, and name
- **Local Storage**: Persistent storage using Room database with Repository pattern
- **Balance Fetching**: API integration to retrieve card balance and last update date
- **Multilingual Support**: Spanish and English localization based on device locale
- **Modern UI**: Built with Jetpack Compose and Material Design 3
- **Clean Architecture**: Separation of concerns with data, domain, and presentation layers

## Architecture

The app follows clean architecture principles with three main layers:

### Domain Layer (`domain/`)
- **Models**: Core business entities (`Card`, `CardBalance`)
- **Repository Interface**: Abstract data access contracts
- **Use Cases**: Business logic encapsulation

### Data Layer (`data/`)
- **Local Storage**: Room database with entities, DAOs, and converters
- **Remote API**: Retrofit service for balance fetching
- **Repository Implementation**: Concrete implementation with offline-first approach

### Presentation Layer (`presentation/`)
- **Screens**: Jetpack Compose UI screens
- **ViewModels**: State management with Hilt dependency injection
- **Navigation**: Navigation Compose setup
- **Components**: Reusable UI components

## Technical Stack

- **UI**: Jetpack Compose + Material Design 3
- **Database**: Room + SQLite
- **Networking**: Retrofit + OkHttp + Gson
- **Dependency Injection**: Hilt
- **Architecture**: MVVM + Clean Architecture
- **Language**: Kotlin
- **Navigation**: Navigation Compose

## Project Structure

```
app/src/main/java/com/example/miocard/
├── data/
│   ├── local/              # Room database
│   │   ├── dao/           # Data Access Objects
│   │   ├── database/      # Database configuration
│   │   ├── entity/        # Room entities
│   │   └── converters/    # Type converters
│   ├── remote/            # API integration
│   │   ├── api/          # Retrofit services
│   │   └── dto/          # Data transfer objects
│   └── repository/        # Repository implementations
├── domain/
│   ├── model/            # Business models
│   ├── repository/       # Repository interfaces
│   └── usecase/          # Business use cases
├── presentation/
│   ├── screens/          # Compose screens
│   ├── components/       # Reusable UI components
│   ├── viewmodel/        # ViewModels
│   └── navigation/       # Navigation setup
├── di/                   # Dependency injection modules
└── utils/               # Utility classes
```

## Multilingual Support

The app supports Spanish and English:

- **Spanish** (`values-es/strings.xml`): Complete Spanish localization
- **English** (`values/strings.xml`): Default English strings
- **Date Formatting**: Locale-aware date formatting with 12-hour format

## API Integration

The app integrates with a REST API to fetch card balance information:

```
GET /cards/{cardId}/balance
```

Response format:
```json
{
  "balance": 25.50,
  "cardNumber": "1234567890",
  "balanceDate": "2024-01-15T10:30:00"
}
```

## Build Requirements

⚠️ **Important**: This project requires internet access to download Android dependencies from Google Maven repository.

### Dependencies
- Android Gradle Plugin 8.1.4
- Kotlin 1.9.10
- Compose BOM 2024.02.00
- Room 2.6.1
- Retrofit 2.9.0
- Hilt 2.48

### Build Commands

```bash
# Clean and build
./gradlew clean build

# Run debug build
./gradlew assembleDebug

# Run tests
./gradlew test
```

## Getting Started

1. Clone the repository
2. Ensure internet access for dependency download
3. Open in Android Studio
4. Sync project with Gradle files
5. Update the API base URL in `NetworkModule.kt`
6. Run on device or emulator

## Usage

### Creating a Card
1. Tap the "+" floating action button
2. Enter required fields (ID and Name)
3. Optionally add prefix and suffix
4. Tap "Create Card"

### Refreshing Balance
1. In the main screen, locate your card
2. Tap the "Refresh" button on the card
3. Balance and last update date will be fetched from the API

## Customization

### API Configuration
Update the base URL in `di/NetworkModule.kt`:
```kotlin
private const val BASE_URL = "https://your-api-url.com/"
```

### Database Configuration
Database settings can be modified in `di/DatabaseModule.kt`.

### Styling
Theme colors and typography can be customized in `ui/theme/`.

## Testing

The project includes unit tests for:
- Repository layer
- Use cases
- ViewModels

Run tests with:
```bash
./gradlew test
```

## License

This project is open source and available under the MIT License.