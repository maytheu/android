package com.example.home.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.home.component.Layout
import com.example.home.screen.home.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val user = viewModel.users.collectAsState().value

    if (user.isNotEmpty()) {
        Layout(
            headerText = "${user[0].firstName}",
            navController = navController,
            back = false,
            imageUrl = user[0].logo
        ) {
            Text(text = "Home sceen")
        }
    }
}

