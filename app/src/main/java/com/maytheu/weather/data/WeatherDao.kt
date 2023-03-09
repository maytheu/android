package com.maytheu.weather.data

import androidx.room.*
import com.maytheu.weather.model.Favourites
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * from favourites_city_tbl")
    fun getFavouritesCity(): Flow<List<Favourites>>

    @Query("SELECT * from favourites_city_tbl where city =:city")
    suspend fun getFavoriteCity(city: String): Favourites

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun newFavouriteCity(favourite: Favourites)

    @Delete
    suspend fun removeFavouriteCity(favourite: Favourites)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavouriteCity(favourite: Favourites)

    @Query("DELETE from favourites_city_tbl")
    suspend fun deleteAllFavouriteCity()
}