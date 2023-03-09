package com.maytheu.weather.repository

import com.maytheu.weather.data.WeatherDao
import com.maytheu.weather.model.Favourites
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDBRepo @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavouritesCity(): Flow<List<Favourites>> = weatherDao.getFavouritesCity()

    suspend fun newFavouriteCity(favourite: Favourites) = weatherDao.newFavouriteCity(favourite)

    suspend fun removeFavouriteCity(favourite: Favourites) =
        weatherDao.removeFavouriteCity(favourite)

    suspend fun getFavoriteCity(city: String): Favourites = weatherDao.getFavoriteCity(city)
}