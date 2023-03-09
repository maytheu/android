package com.maytheu.weather.di

import android.content.Context
import androidx.room.Room
import com.maytheu.weather.data.WeatherDao
import com.maytheu.weather.data.WeatherDatabase
import com.maytheu.weather.network.WeatherApi
import com.maytheu.weather.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    //initialize the db
    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao =
        weatherDatabase.weatherDao()

    //create the actual db
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(
            context, WeatherDatabase::class.java, "weather_db"
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideOpenWeatherMapApi(): WeatherApi { //called by dagger
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(WeatherApi::class.java)
    }
}