package com.maytheu.weather.repository

import com.maytheu.weather.data.WeatherDao
import com.maytheu.weather.model.Favourites
import com.maytheu.weather.model.Setting
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDBRepo @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavouritesCity(): Flow<List<Favourites>> = weatherDao.getFavouritesCity()

    suspend fun newFavouriteCity(favourite: Favourites) = weatherDao.newFavouriteCity(favourite)

    suspend fun removeFavouriteCity(favourite: Favourites) =
        weatherDao.removeFavouriteCity(favourite)

    suspend fun deleteAllFavouriteCity() = weatherDao.deleteAllFavouriteCity()

    suspend fun getFavoriteCity(city: String): Favourites = weatherDao.getFavoriteCity(city)


    //settings dao
    fun getUnit(): Flow<List<Setting>> = weatherDao.getUnit()

    suspend fun addUnit(unit: Setting) = weatherDao.addUnit(unit)

    suspend fun updateSettings(unit: Setting) = weatherDao.updateSettings(unit)

    suspend fun deleteAllUnit() = weatherDao.deleteAllUnit()

}