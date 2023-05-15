package com.maytheu.reader.network

import com.maytheu.reader.model.GoogleBook
import com.maytheu.reader.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface GoogleBooksApi {
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") bookQuery: String): GoogleBook

    @GET("volume/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId:String):Item
}