package com.maytheu.reader.navigation

enum class ReaderScreens {
    SplashScreen, LoginScreen, HomeScreen, SearchScreen, UpdateScreen, StatsScreen, BookDetailsScreen, SignUpScreen;

    companion object {
        fun fromRoute(route: String): ReaderScreens = when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            HomeScreen.name -> HomeScreen
            SearchScreen.name -> SearchScreen
            UpdateScreen.name -> UpdateScreen
            StatsScreen.name -> StatsScreen
            BookDetailsScreen.name -> BookDetailsScreen
            SignUpScreen.name -> SignUpScreen
            null -> HomeScreen
            else -> throw java.lang.IllegalArgumentException("$route not recognized")
        }
    }
}