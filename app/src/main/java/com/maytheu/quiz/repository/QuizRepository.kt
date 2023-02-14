package com.maytheu.quiz.repository

import android.util.Log
import com.maytheu.quiz.data.DataOrException
import com.maytheu.quiz.model.QuestionItem
import com.maytheu.quiz.service.QuestionApi
import javax.inject.Inject

class QuizRepository @Inject constructor(private val questionApi: QuestionApi) {
    private val dataOrException =
        DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = questionApi.getAllQuestions()

            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false
        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.d("getAllQuestions: ", exception.toString())
        }
        return dataOrException
    }


}