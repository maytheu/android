package com.maytheu.weather.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maytheu.weather.data.DataOrException
import com.maytheu.weather.model.Weather
import com.maytheu.weather.screens.home.WeatherHomeViewModel
import com.maytheu.weather.widgets.WeatherAppBar

@Composable
fun WeatherHomeScreen(navController: NavController, homeViewModel: WeatherHomeViewModel) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = homeViewModel.getWeather(city = "lagos")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        WeatherData(weatherData.data!!, navController)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeatherData(data: Weather, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "${data.city.name}, ${data.city.country }",
            elevation = 5.dp,
            isMainScreen = false,
            navController = navController,
//            icon = Icons.Default.ArrowBack
        )
    }) {
        WeatherContent(data = data)
    }
}

@Composable
fun WeatherContent(data: Weather) {
    Text(text = data.city.name)
}



