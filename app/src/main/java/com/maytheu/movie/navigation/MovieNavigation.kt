package com.maytheu.movie.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maytheu.movie.screens.detail.DetailScreen
import com.maytheu.movie.screens.home.HomeScreen

/**
 * This will create the nav controller and navigation
 */

@Composable
fun MovieNavigation(){
    val navCtrl = rememberNavController()

    NavHost(navController = navCtrl, startDestination = MovieScreens.HomeScreen.name) {
        composable(MovieScreens.HomeScreen.name) { HomeScreen(navCtrl = navCtrl) }
        composable(MovieScreens.DetailsScreen.name) { DetailScreen(navController = navCtrl) }
        /*...*/
    }
}