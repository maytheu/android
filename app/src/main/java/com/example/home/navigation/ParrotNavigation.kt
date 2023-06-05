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
import com.example.home.screen.chart.ChartViewModel
import com.example.home.screen.chart.StaticChartScreen
import com.example.home.screen.chart.SummarizedChart
import com.example.home.screen.details.DeviceDetails
import com.example.home.screen.details.DeviceDetailsViewModel
import com.example.home.screen.floor.FloorViewModel
import com.example.home.screen.home.HomeViewModel
import com.example.home.screen.login.LoginViewModel
import com.example.home.screen.logs.LogsScreen
import com.example.home.screen.logs.LogsViewModel
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

        composable("${ParrotScreens.DeviceDetailsScreen.name}/deviceId/{deviceId}",
            arguments = listOf(
                navArgument("deviceId") {
                    type = NavType.StringType
                }
            )) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("deviceId").let {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                val deviceDetailsViewModel = hiltViewModel<DeviceDetailsViewModel>()
                DeviceDetails(
                    deviceId = it.toString(),
                    homeViewModel = homeViewModel,
                    navController = navController,
                    deviceDetailsViewModel = deviceDetailsViewModel
                )
            }
        }

        composable(
            "${ParrotScreens.DeviceLogScreen.name}/deviceId/{deviceId}",
            arguments = listOf(navArgument("deviceId") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("deviceId").let {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                val logsViewModel = hiltViewModel<LogsViewModel>()
                LogsScreen(
                    homeViewModel = homeViewModel,
                    logsViewModel = logsViewModel,
                    deviceId = it.toString(),
                    navController = navController,
                )
            }
        }

        composable(
            "${ParrotScreens.StaticChartScreen.name}/deviceId/{deviceId}",
            arguments = listOf(navArgument("deviceId") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("deviceId").let {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                val chartViewModel = hiltViewModel<ChartViewModel>()
                StaticChartScreen(
                    homeViewModel = homeViewModel,
                    chartViewModel = chartViewModel,
                    deviceId = it.toString(),
                    navController = navController,
                )
            }
        }

        composable(
            "${ParrotScreens.SummarizedChartScreen.name}/deviceId/{deviceId}",
            arguments = listOf(navArgument("deviceId") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("deviceId").let {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                val chartViewModel = hiltViewModel<ChartViewModel>()
                SummarizedChart(
                    homeViewModel = homeViewModel,
                    chartViewModel = chartViewModel,
                    deviceId = it.toString(),
                    navController = navController,
                )
            }
        }

    }
}
