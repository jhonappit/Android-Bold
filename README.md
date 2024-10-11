 <img src="images/logo.png" alt="ArchiTecture logo"/>

[![test](https://github.com/blocoio/android-template/workflows/test/badge.svg?branch=master)](https://github.com/blocoio/android-template/actions?query=workflow%3Atest+branch%3Amaster)
[![lint](https://github.com/blocoio/android-template/workflows/lint/badge.svg?branch=master)](https://github.com/blocoio/android-template/actions?query=workflow%3Alint+branch%3Amaster)

The goal of this project is to show our skills as Android Developer, following the best development practices. It's our interpretation and adaptation of the official [architecture](https://developer.android.com/topic/architecture) guidelines provided by Google. And it's inspired by Google's [NowInAndroid](https://github.com/android/nowinandroid).

## Clean architecture with 3 main modules
- Data (for database, entities business rules, repository impl, interfaces API and preferences code)
- Domain (for business logic repositories usecases and models(if you are using mappers))
- AndroidApp (for UI logic, with MVVM)

 <img src="images/AndroidTemplate-CleanArchitecture.jpg" alt="ArchiTecture logo"/>

## Tests
- [Mockk](https://mockk.io/) library
- Unit tests
- Application tests
    - example on how to work with tests
- Activity tests (with [Compose Testing](https://developer.android.com/jetpack/compose/testing))
    - example on how to work with coroutine scopes in tests

## Other useful features
- Shared Build Logic (with [Convention plugins](https://docs.gradle.org/current/samples/sample_convention_plugins.html))
- Dependency injection (with [Hilt](http://google.github.io/hilt/))
- Network calls (with [Retrofit](https://square.github.io/retrofit/))
- Android architecture components to share ViewModels during configuration changes
- [Splash Screen](https://developer.android.com/develop/ui/views/launch/splash-screen) Support
- Google [Material Design](https://material.io/blog/android-material-theme-color) library
- Declarative UI (with [Jetpack Compose](https://developer.android.com/jetpack/compose))
    - Compose Navigation
- Edge To Edge Configuration

# Getting started

1. Download this repository or clone into Android Studio
2. Use an emulator or real device
3. Launch the app
4. Search your location into textfield
5. Clean search wiht X
6. On result click done or enter to see result

And you're ready to start working with the app.

# Notes
- Some key information is hidden in files check for apikeys.properties