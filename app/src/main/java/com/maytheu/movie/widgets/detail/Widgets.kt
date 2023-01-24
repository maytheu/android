package com.maytheu.movie.widgets.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.maytheu.movie.model.Movie

@Composable
fun ImageRow(movie: Movie){
    LazyRow{
        items(movie.images){image ->
            Card(modifier = Modifier.padding(12.dp).size(240.dp), elevation = 5.dp){

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .transformations(CircleCropTransformation())
                        .build(),
                    contentDescription = "${movie.title} poster"
                )
            }

        }
    }
}