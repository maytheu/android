package com.example.home.screen.details

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.home.component.Layout
import com.example.home.screen.home.HomeViewModel
import com.example.home.screen.plan.ShowDevicesOnFloor

@Composable
fun DeviceDetails(
    deviceId: String,
    homeViewModel: HomeViewModel,
    deviceDetailsViewModel: DeviceDetailsViewModel,
    navController: NavController,
) {
    val user = homeViewModel.users.collectAsState().value

    if (user.isNotEmpty()) {
        Layout(
            headerText = user[0].firstName,
            navController = navController,
            back = true,
            imageUrl = user[0].logo
        ) {
            Text(text = deviceId)
        }
    }
}