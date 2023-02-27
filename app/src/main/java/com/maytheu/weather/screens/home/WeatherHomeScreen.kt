package com.maytheu.weather.screens

import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.navigation.NavController
import com.maytheu.weather.data.DataOrException
import com.maytheu.weather.model.Weather
import com.maytheu.weather.screens.home.WeatherHomeViewModel

@Composable
fun WeatherHomeScreen(navController: NavController, homeViewModel: WeatherHomeViewModel) {
    ShowData(homeViewModel)
}

@Composable
fun ShowData(viewModel: WeatherHomeViewModel) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = viewModel.getWeather(city = "lagos")
    }.value

    if (weatherData.loading == true) {
        Log.d("TAG", "ShowData: loading ${weatherData.toString()}")
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        Log.d("TAG", "ShowData: data ${weatherData.toString()}")
        Text(text = "View model ${weatherData.data.toString()}")

    }

}
