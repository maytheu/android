package com.maytheu.movie.screens.detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DetailScreen(navController: NavController, movieData: String?){
    Text(text = movieData.toString())
}