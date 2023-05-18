package com.example.home.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.home.component.Layout
import com.example.home.screen.home.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel){
    Layout(headerText = "User", navController = navController, back = false) {
        Text(text = "Home sceen")
    }
}

