package com.maytheu.weather.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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

    var unit by remember {
        mutableStateOf("imperial")
    }

    var isImperial by remember {
        mutableStateOf(false)
    }

    if (!choicesFromDb.isNullOrEmpty()) {
        unit = choicesFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"
        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = homeViewModel.getWeather(city = city.toString(), unit)
        }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            WeatherData(weatherData.data!!, navController, isImperial = isImperial)
        }
    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeatherData(data: Weather, navController: NavController, isImperial: Boolean) {
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
        WeatherContent(data = data, isImperial = isImperial)
    }
}


