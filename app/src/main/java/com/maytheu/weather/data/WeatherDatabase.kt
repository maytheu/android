package com.maytheu.weather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maytheu.weather.model.Favourites

@Database(entities = [Favourites::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}