package com.maytheu.quiz.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maytheu.quiz.screens.QuizViewModel
import com.maytheu.quiz.util.AppColor

@Composable
fun Questions(quizViewModel: QuizViewModel) {
    val questions = quizViewModel.data.value.data?.toMutableList()
    if (quizViewModel.data.value.loading == true) Log.d("TAG", "Questions: Loading")
    Log.d("TAG", "Questions: ${questions?.size}")
    Text(text = "here ${questions?.size}")

}

@Preview
@Composable
fun QuestionDisplay() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding((4.dp)), color = AppColor.mDarkPurple
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            QuestionTracker()
        }

    }
}


//@Preview
@Composable
fun QuestionTracker(counter: Int = 10, total: Int = 100) {
    //since we'll have different type text we'll use annotated text
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(
                style = SpanStyle(
                    color = AppColor.mLightGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            ) {
                append("Question $counter/")
                withStyle(style = SpanStyle(color = AppColor.mBlue, fontSize = 14.sp)) {
                    append("$total")
                }
            }
        }
    }, modifier = Modifier.padding(20.dp))

}