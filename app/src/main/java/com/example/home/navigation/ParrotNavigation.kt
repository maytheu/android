package com.example.home.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.home.screen.FloorScreen
import com.example.home.screen.HomeScreen
import com.example.home.screen.LoginScreen
import com.example.home.screen.SplashScreen
import com.example.home.screen.floor.FloorViewModel
import com.example.home.screen.home.HomeViewModel
import com.example.home.screen.login.LoginViewModel
import com.example.home.screen.plan.PlanScreen
import com.example.home.screen.plan.PlanViewModel

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

        composable(
            "${ParrotScreens.FloorScreen.name}/assetId/{assetId}",
            arguments = listOf(navArgument("assetId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("assetId").let {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                val floorViewModel = hiltViewModel<FloorViewModel>()
                FloorScreen(
                    navController = navController,
                    assetId = it.toString(),
                    homeViewModel = homeViewModel,
                    floorViewModel = floorViewModel
                )
            }
        }

        composable(
            "${ParrotScreens.PlanScreen.name}/floorId/{floorId}",
            arguments = listOf(navArgument("floorId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("floorId").let {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                val floorViewModel = hiltViewModel<FloorViewModel>()
                val planViewModel = hiltViewModel<PlanViewModel>()
                PlanScreen(
                    navController = navController,
                    floorId = it.toString(),
                    homeViewModel = homeViewModel,
                    floorViewModel = floorViewModel,
                    planViewModel = planViewModel
                )
            }
        }

    }
}
