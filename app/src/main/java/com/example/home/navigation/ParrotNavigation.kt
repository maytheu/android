package com.example.home.navigation

import android.provider.ContactsContract
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.home.screen.HomeScreen
import com.example.home.screen.LoginScreen

@Composable
fun ParrotNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ParrotScreens.LoginScreen.name) {
        composable(ParrotScreens.LoginScreen.name) { LoginScreen(navController) }
        composable(ParrotScreens.HomeScreen.name) { HomeScreen(navController = navController) }
    }
}
