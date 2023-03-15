package com.maytheu.weather.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maytheu.weather.data.DataOrException
import com.maytheu.weather.model.Weather
import com.maytheu.weather.repository.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHomeViewModel @Inject constructor(private val weatherRepo: WeatherRepo) : ViewModel() {
//    val data: MutableState<DataOrException<Weather, Boolean, Exception>> =
//        mutableStateOf(
//            DataOrException(null, true, Exception(""))
//        )
//
//    init {
//        loadWeather()
//    }
//
//    private fun loadWeather() {
//        getWeather("lagos", "imperial")
//    }
//
//    private fun getWeather(s: String, units:String) {
//        viewModelScope.launch {
//            if (s.isEmpty()) return@launch
//
//            data.value.loading = true
//            data.value = weatherRepo.getWeatherForecast(s)
//            if (data.value.data.toString().isNotEmpty()) data.value.loading = false
//        }
//        Log.d("TAG", "getWeather: ${data.value.data.toString()}")
//    }

    suspend fun getWeather(
        city: String,
        unit: String,
    ): DataOrException<Weather, Boolean, Exception> {
        return weatherRepo.getWeatherForecast(city, unit)
    }
}