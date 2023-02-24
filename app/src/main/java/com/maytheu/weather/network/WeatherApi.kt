package com.maytheu.weather.network

import com.maytheu.weather.model.Weather
import com.maytheu.weather.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("q") query: String,
        @Query("appid") appid: String = Constants.API_KEY,
        @Query("units") units: String = "imperial",
    ): Weather
}