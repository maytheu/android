package com.maytheu.movie.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maytheu.movie.model.Movie
import com.maytheu.movie.model.getMovies
import com.maytheu.movie.navigation.MovieScreens
import com.maytheu.movie.widgets.home.MovieRow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navCtrl: NavController) {
    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color.Blue, elevation = 5.dp) {
            Text(text = "Movies")
        }
    }) {
        MainContent(navController = navCtrl)
    }
}

@Composable
fun MainContent(
    navController: NavController,
    movies: List<Movie> = getMovies()
) {
    Column(modifier = Modifier.padding(12.dp)) {
        LazyColumn {
            items(items = movies) {
                MovieRow(movie = it) { movie ->
                    navController.navigate(route = "$MovieScreens.DetailsScreen.name/$movie")
                    Log.d("TAG", "MovieRow: Clicked $movie")
                }
            }
        }

    }
}

