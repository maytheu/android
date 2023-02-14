package com.maytheu.quiz

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.maytheu.quiz.screens.QuizViewModel
import com.maytheu.quiz.ui.theme.QuizTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    QuizHome()
                }
            }
        }
    }
}

@Composable
fun QuizHome(quizViewModel: QuizViewModel = hiltViewModel()) {
    Questions(quizViewModel)
}

@Composable
fun Questions(quizViewModel: QuizViewModel) {
    val questions = quizViewModel.data.value.data?.toMutableList()
    if(quizViewModel.data.value.loading == true) Log.d("TAG", "Questions: Loading")
    Log.d("TAG", "Questions: ${questions?.size}")
    Text(text = "here ${questions?.size}")

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuizTheme {

    }
}