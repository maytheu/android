package com.maytheu.reader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maytheu.reader.screens.SplashScreen
import com.maytheu.reader.screens.details.BookDetailsScreen
import com.maytheu.reader.screens.home.HomeScreen
import com.maytheu.reader.screens.login.LoginScreen
import com.maytheu.reader.screens.search.SearchScreen
import com.maytheu.reader.screens.stats.StatsScreen
import com.maytheu.reader.screens.update.UpdateScreen

@Composable
fun ReaderNavigstion() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(ReaderScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(ReaderScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }

        composable(ReaderScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }

        composable(ReaderScreens.UpdateScreen.name) {
            UpdateScreen(navController = navController)
        }

        composable(ReaderScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(ReaderScreens.BookDetailsScreen.name) {
            BookDetailsScreen(navController = navController)
        }

        composable(ReaderScreens.SignUpScreen.name) {
            LoginScreen(navController = navController)
        }

        composable(ReaderScreens.StatsScreen.name) {
            StatsScreen(navController = navController)
        }
    }
}