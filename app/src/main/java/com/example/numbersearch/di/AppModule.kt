package com.example.numbersearch.di

import com.example.numbersearch.service.SearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun providesServerApi(): Any {
        return Retrofit.Builder().baseUrl("").addConverterFactory(GsonConverterFactory.create())
            .build().create(SearchApi::class.java)
    }
}