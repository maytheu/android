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
import com.maytheu.movie.navigation.MovieScreens

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
    movies: List<String> = listOf(
        "Avatar", "Merlin", "Harry Potter", "God not dead", "Tom and Jerry"
    )
) {
    Column(modifier = Modifier.padding(12.dp)) {
        LazyColumn {
            items(items = movies) {
                MovieRow(movie = it) { movie ->
                    navController.navigate(route = MovieScreens.DetailsScreen.name)
                    Log.d("TAG", "MovieRow: Clicked $movie")
                }
            }
        }

    }
}

@Composable
fun MovieRow(movie: String, onMovieClicked: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(150.dp)
            .clickable { onMovieClicked(movie) },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 8.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp),
                shape = RectangleShape,
                elevation = 5.dp
            ) {
                Icon(imageVector = Icons.Default.AccountBox, contentDescription = "placeholder")

            }
            Text(text = movie)
        }
    }
}
