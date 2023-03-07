package com.maytheu.weather.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maytheu.weather.screens.WeatherHomeScreen
import com.maytheu.weather.screens.WeatherSearchScreen
import com.maytheu.weather.screens.WeatherSplashScreen
import com.maytheu.weather.screens.home.WeatherHomeViewModel

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()

    //define the structure of the route
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) { WeatherSplashScreen(navController = navController) }

        composable(WeatherScreens.HomeScreen.name) {
            val homeViewModel = hiltViewModel<WeatherHomeViewModel>()
            WeatherHomeScreen(navController = navController, homeViewModel) }
        
        composable(WeatherScreens.SearchScreen.name){
            WeatherSearchScreen(navController = navController)
        }
    }
}