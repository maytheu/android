package com.maytheu.weather.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.maytheu.weather.data.DataOrException
import com.maytheu.weather.model.Weather
import com.maytheu.weather.navigation.WeatherScreens
import com.maytheu.weather.screens.home.WeatherHomeViewModel
import com.maytheu.weather.screens.settings.WeatherSettingsViewModel
import com.maytheu.weather.widgets.WeatherAppBar
import com.maytheu.weather.widgets.WeatherContent

@Composable
fun WeatherHomeScreen(
    navController: NavController,
    homeViewModel: WeatherHomeViewModel,
    settingsViewModel: WeatherSettingsViewModel = hiltViewModel(),
    city: String?,
) {
    val choicesFromDb = settingsViewModel.units.collectAsState().value
    val defaultUnit =
        if (choicesFromDb.isNullOrEmpty()) "metric" else choicesFromDb[0].unit.split("(")[0].lowercase()
    val unit by remember {
        mutableStateOf(defaultUnit)
    }
    Log.d("Home", "WeatherHomeScreen: $unit")
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = homeViewModel.getWeather(city = city.toString(), unit)
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
        WeatherAppBar(title = "${data.city.name}, ${data.city.country}",
            elevation = 5.dp,
            isMainScreen = true,
            navController = navController,
//            icon = Icons.Default.FavoriteBorder,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            })
    }) {
        WeatherContent(data = data)
    }
}


