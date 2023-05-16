package com.example.home.services

import com.example.home.model.LoginData
import com.example.home.model.Response
import com.example.home.model.User
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface AuthApi {
//    @Headers("Accept: application/json")
    @POST("user/login")
    suspend fun login(@Body body: LoginData): Response<User>
}