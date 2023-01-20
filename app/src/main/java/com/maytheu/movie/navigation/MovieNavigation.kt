package com.maytheu.movie.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.maytheu.movie.screens.detail.DetailScreen
import com.maytheu.movie.screens.home.HomeScreen

/**
 * This will create the nav controller and navigation
 * define the navigation layout
 */

@Composable
fun MovieNavigation() {
    val navCtrl = rememberNavController()

    NavHost(navController = navCtrl, startDestination = MovieScreens.HomeScreen.name) {
        composable(MovieScreens.HomeScreen.name) { HomeScreen(navCtrl = navCtrl) }

        //navagate to www.movi.com/detail/movie=harry
        composable(
            "$MovieScreens.DetailsScreen.name/{movie}",
            arguments = listOf(navArgument("movie") { type = NavType.StringType })
        ) { backStackEntry ->
            DetailScreen(navController = navCtrl, backStackEntry.arguments?.getString("movie"))
        }
    }
}