package com.maytheu.weather.repository

import android.util.Log
import com.maytheu.weather.data.DataOrException
import com.maytheu.weather.model.Weather
import com.maytheu.weather.network.WeatherApi
import javax.inject.Inject

class WeatherRepo @Inject constructor(private val weatherApi: WeatherApi) {
    suspend fun getWeatherForecast(
        city: String, unit: String,
    ): DataOrException<Weather, Boolean, Exception> {
        val resp = try {
            weatherApi.getWeatherForecast(query = city, units = unit)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(resp)
    }
}