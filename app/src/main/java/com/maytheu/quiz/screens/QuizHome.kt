package com.maytheu.quiz.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.maytheu.quiz.component.Questions

@Composable
fun QuizHome(quizViewModel: QuizViewModel = hiltViewModel()) {
    Questions(quizViewModel)
}