package com.maytheu.movie.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.maytheu.movie.model.getMovie
import com.maytheu.movie.widgets.detail.ImageRow
import com.maytheu.movie.widgets.home.MovieRow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(navController: NavController, movieId: String?) {
    val movie = getMovie(movieId.toString())
    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color.Blue, elevation = 5.dp) {
            Row(horizontalArrangement = Arrangement.Start) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go back",
                    modifier = Modifier.clickable { navController.popBackStack() })

                Spacer(modifier = Modifier.width(10.dp))

                Text(text = "Movies")

            }
        }
    }) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                MovieRow(movie = movie)

                Spacer(modifier = Modifier.height(10.dp))
                Divider()

                Text(text = "Movie images")
                ImageRow(movie = movie)
            }
        }
    }

}