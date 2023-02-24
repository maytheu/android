package com.maytheu.weather.repository

import com.maytheu.weather.data.DataOrException
import com.maytheu.weather.model.Weather
import com.maytheu.weather.network.WeatherApi
import javax.inject.Inject

class WeatherRepo @Inject constructor(private val weatherApi: WeatherApi) {
    suspend fun getWeatherForecast(
        city: String,
        units: String,
    ): DataOrException<Weather, Boolean, java.lang.Exception> {
        val resp = try {
            weatherApi.getWeatherForecast(query = city, units)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(resp)
    }
}