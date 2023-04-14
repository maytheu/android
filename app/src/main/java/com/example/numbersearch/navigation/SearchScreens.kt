package com.example.numbersearch.navigation

enum class SearchScreens {
    SplashScreen, HomeScreen;

    companion object {
        fun fromRouter(route: String): SearchScreens = when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            HomeScreen.name -> HomeScreen
            null -> HomeScreen
            else -> throw java.lang.IllegalArgumentException("$route not recognized")
        }
    }
}