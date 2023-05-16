package com.example.home.navigation

import android.provider.ContactsContract
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.home.screen.HomeScreen
import com.example.home.screen.LoginScreen
import com.example.home.screen.SplashScreen
import com.example.home.screen.login.LoginViewModel

@Composable
fun ParrotNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ParrotScreens.SplashScreen.name) {
        composable(ParrotScreens.SplashScreen.name) { SplashScreen(navController = navController) }
        composable(ParrotScreens.LoginScreen.name) {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(navController, viewModel)
        }
        composable(ParrotScreens.HomeScreen.name) { HomeScreen(navController = navController) }
    }
}
