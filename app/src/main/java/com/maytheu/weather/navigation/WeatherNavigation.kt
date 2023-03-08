package com.maytheu.weather.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.maytheu.weather.screens.*
import com.maytheu.weather.screens.home.WeatherHomeViewModel

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()

    //define the structure of the route
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) { WeatherSplashScreen(navController = navController) }

        composable(WeatherScreens.HomeScreen.name + "/{city}", arguments = listOf(
            navArgument(name = "city") {
                type = NavType.StringType
            }
        )) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                val homeViewModel = hiltViewModel<WeatherHomeViewModel>()
                WeatherHomeScreen(navController = navController, homeViewModel, city = city)
            }

        }

        composable(WeatherScreens.SearchScreen.name) {
            WeatherSearchScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            WeatherAboutScreen(navController = navController)
        }

        composable(WeatherScreens.SettingScreen.name) {
            WeatherSettingsScreen(navController = navController)
        }

        composable(WeatherScreens.FavouriteScreen.name) {
            WeatherFavouritesScreen(navController = navController)
        }
    }
}