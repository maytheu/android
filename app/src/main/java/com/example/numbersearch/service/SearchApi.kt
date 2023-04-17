package com.example.numbersearch.service

import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface SearchApi {
    @GET("")
    suspend fun gsearchPhone(
        @Query("phone") phone: String
    )
}