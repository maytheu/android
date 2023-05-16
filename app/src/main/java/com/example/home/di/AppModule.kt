package com.example.home.di

import com.example.home.services.AuthApi
import com.example.home.services.repository.AuthRepo
import com.example.home.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesAuthRepo(api: AuthApi) = AuthRepo(api)

    @Singleton
    @Provides
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(AuthApi::class.java)
    }
}