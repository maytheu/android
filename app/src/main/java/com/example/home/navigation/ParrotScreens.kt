package com.example.home.navigation

enum class ParrotScreens {
    LoginScreen, SplashScreen, HomeScreen, FloorScreen, PlanScreen, DeviceDetailsScreen;

//    companion object {
//        fun fromRoute(route: String?): ParrotScreens =
//            when (route?.substringBefore(delimiter = "/")) {
//                LoginScreen.name -> LoginScreen
//                HomeScreen.name -> HomeScreen
//                null -> HomeScreen
//                else -> throw IllegalArgumentException("Route $route not found")
//            }
//    }
}