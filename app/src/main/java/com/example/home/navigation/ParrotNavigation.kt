package com.example.home.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.home.screen.HomeScreen
import com.example.home.screen.LoginScreen
import com.example.home.screen.SplashScreen
import com.example.home.screen.home.HomeViewModel
import com.example.home.screen.login.LoginViewModel

@Composable
fun ParrotNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ParrotScreens.SplashScreen.name) {
        composable(ParrotScreens.SplashScreen.name) {
            val userViewModel = hiltViewModel<HomeViewModel>()
            SplashScreen(navController = navController, viewModel = userViewModel)
        }


        composable(ParrotScreens.LoginScreen.name) {
            val authViewModel = hiltViewModel<LoginViewModel>()
            val userViewModel = hiltViewModel<HomeViewModel>()
            LoginScreen(navController, authViewModel = authViewModel, userViewModel = userViewModel)
        }

        composable(ParrotScreens.HomeScreen.name) {
            val userViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController = navController, viewModel = userViewModel)
        }
    }
}
