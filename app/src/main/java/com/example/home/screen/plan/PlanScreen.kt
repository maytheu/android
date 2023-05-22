package com.example.home.screen.plan

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.home.component.Layout
import com.example.home.screen.CompanyAssets
import com.example.home.screen.floor.FloorViewModel
import com.example.home.screen.home.HomeViewModel

@Composable
fun PlanScreen(
    navController: NavController,
    planViewModel: PlanViewModel,
    floorViewModel: FloorViewModel,
    homeViewModel: HomeViewModel,
    floorId: String,
) {
    val user = homeViewModel.users.collectAsState().value

    if (user.isNotEmpty()) {
        Layout(
            headerText = "${user[0].firstName}",
            navController = navController,
            back = true,
            imageUrl = user[0].logo
        ) {
            ShowDevicesOnFloor(floorId, floorViewModel, planViewModel, navController)
        }
    }
}

@Composable
fun ShowDevicesOnFloor(
    floorId: String,
    floorViewModel: FloorViewModel,
    planViewModel: PlanViewModel,
    navController: NavController,
) {
    Text(text = "load webview")
}
