package com.maytheu.weather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maytheu.weather.model.Favourites
import com.maytheu.weather.model.Setting

@Database(entities = [Favourites::class, Setting::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}