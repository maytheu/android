package com.maytheu.quiz.service

import com.maytheu.quiz.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

//perform retorfit action here
@Singleton
interface QuestionApi {
    @GET("world.json")
    suspend fun getAllQuestions(): Question
}