package com.maytheu.movie.navigation

/**
 * defines all screens for the app and the structure our navigation
 */
enum class MovieScreens {
    HomeScreen,
    DetailsScreen;

    companion object {
        fun fromRoute(route: String?): MovieScreens =
            when (route?.substringBefore(delimiter = "/")) {
                HomeScreen.name -> HomeScreen
                DetailsScreen.name -> DetailsScreen
                null -> HomeScreen
                else -> throw IllegalArgumentException("Route $route not found")
            }
    }
}