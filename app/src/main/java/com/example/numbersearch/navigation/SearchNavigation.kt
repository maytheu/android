package com.example.numbersearch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun SearchNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SearchScreens.HomeScreen.name ){
        composable(SearchScreens.HomeScreen.name){
            //home screen component
        }
    }
}