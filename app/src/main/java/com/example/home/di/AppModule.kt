package com.example.home.di

import android.content.Context
import androidx.room.Room
import com.example.home.services.SmartHomeDatabase
import com.example.home.services.dao.AssetApi
import com.example.home.services.dao.AuthApi
import com.example.home.services.dao.SmartHomeDao
import com.example.home.services.repository.AuthRepo
import com.example.home.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSmartHomeDao(db: SmartHomeDatabase): SmartHomeDao = db.smartHomeDao()

    @Singleton
    @Provides
    fun provideSmartHomeDb(@ApplicationContext context: Context): SmartHomeDatabase =
        Room.databaseBuilder(
            context, SmartHomeDatabase::class.java, "smart_home_db"
        ).fallbackToDestructiveMigration().build()

//    @Singleton
//    @Provides
//    fun providesAuthRepo(api: AuthApi) = AuthRepo(api)

    @Singleton
    @Provides
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAssetApi(): AssetApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(AssetApi::class.java)
    }
}