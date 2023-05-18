package com.maytheu.reader.di

import com.google.firebase.firestore.FirebaseFirestore
import com.maytheu.reader.network.GoogleBooksApi
import com.maytheu.reader.repository.FireRepository
import com.maytheu.reader.repository.GoogleBooksRepo
import com.maytheu.reader.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
//    @Singleton
//    @Provides
//    fun provideGoogleBookRepo(api: GoogleBooksApi) = GoogleBooksRepo(api)

    @Singleton
    @Provides
    fun provideFireRepository() = FireRepository(FirebaseFirestore.getInstance().collection("books"))

    @Singleton
    @Provides
    fun provideGoogleBooksApi(): GoogleBooksApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(GoogleBooksApi::class.java)
    }
}