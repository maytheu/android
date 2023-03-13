package com.maytheu.weather.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.maytheu.weather.model.Favourites
import com.maytheu.weather.navigation.WeatherScreens
import com.maytheu.weather.screens.favourites.WeatherFavouritesViewModel
import com.maytheu.weather.widgets.WeatherAppBar
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeatherFavouritesScreen(
    navController: NavController,
    weatherFavouritesViewModel: WeatherFavouritesViewModel = hiltViewModel(),
) {
    Scaffold(topBar = {
        WeatherAppBar(
            navController = navController, title = "Favourites Cities",
            icon = Icons.Default.ArrowBack,
            isMainScreen = false
        ) {
            navController.popBackStack()
        }
    }) {
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = weatherFavouritesViewModel.favouritesCity.collectAsState().value
                LazyColumn {
                    items(items = list) {
                        CityRow(
                            it,
                            navController = navController,
                            viewModel = weatherFavouritesViewModel
                        )
                    }
                }

            }

        }

    }
}

@Composable
fun CityRow(
    favourite: Favourites,
    navController: NavController,
    viewModel: WeatherFavouritesViewModel,
) {
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate(WeatherScreens.HomeScreen.name + "/" + favourite.city)
            },
        shape = CircleShape.copy(topEnd = CornerSize(10.dp)),
        color = Color(0xFFB2DFDB)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = favourite.city, modifier = Modifier.padding(start = 5.dp))

            Surface(modifier = Modifier.padding(1.dp), shape = CircleShape, color = Color.White) {
                Text(
                    text = favourite.country,
                    modifier = Modifier.padding(5.dp),
                    style = MaterialTheme.typography.caption
                )
            }

            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete",
                modifier = Modifier.clickable { viewModel.deleteFavouriteCity(favourite) },
                tint = Color.Red.copy(alpha = 0.3f)
            )
        }
    }
}
