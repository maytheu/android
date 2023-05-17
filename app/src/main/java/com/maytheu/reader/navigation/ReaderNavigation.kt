package com.maytheu.reader.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.maytheu.reader.screens.SplashScreen
import com.maytheu.reader.screens.details.BookDetailsScreen
import com.maytheu.reader.screens.home.HomeScreen
import com.maytheu.reader.screens.login.LoginScreen
import com.maytheu.reader.screens.login.LoginViewModel
import com.maytheu.reader.screens.search.SearchScreen
import com.maytheu.reader.screens.search.SearchViewModel
import com.maytheu.reader.screens.stats.StatsScreen
import com.maytheu.reader.screens.update.UpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(ReaderScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(ReaderScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }

        composable(ReaderScreens.LoginScreen.name) {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(navController = navController, loginViewModel = viewModel)
        }

        composable(ReaderScreens.UpdateScreen.name) {
            UpdateScreen(navController = navController)
        }

        composable(ReaderScreens.SearchScreen.name) {
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(navController = navController, viewModel)
        }

        composable(
            "${ReaderScreens.BookDetailsScreen.name}/bookId",
            arguments = listOf(navArgument("bookId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {
                BookDetailsScreen(navController = navController, bookId = it.toString())
            }
        }

//        composable(ReaderScreens.SignUpScreen.name) {
//            LoginScreen(navController = navController)
//        }

        composable(ReaderScreens.StatsScreen.name) {
            StatsScreen(navController = navController)
        }
    }
}