package com.maytheu.quiz.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maytheu.quiz.model.QuestionItem
import com.maytheu.quiz.screens.QuizViewModel
import com.maytheu.quiz.util.AppColor

@Composable
fun Questions(quizViewModel: QuizViewModel) {
    val questions = quizViewModel.data.value.data?.toMutableList()
    val questionIndex = remember {
        mutableStateOf(0)
    }

    if (quizViewModel.data.value.loading == true) {
        CircularProgressIndicator()
    } else {
        val question = try {
            questions?.get(questionIndex.value)
        } catch (e: java.lang.Exception) {
            null
        }

        if (questions !== null) {
            if (question != null) {
                QuestionDisplay(
                    questionItem = question,
                    questionIndex,
                    quizViewModel = quizViewModel
                ) {
                    questionIndex.value = questionIndex.value + 1
                }
            }
        }
    }
}

@Composable
fun QuestionDisplay(
    questionItem: QuestionItem,
    questionIndex: MutableState<Int>,
    quizViewModel: QuizViewModel,
    onNextClicked: (Int) -> Unit,
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 5f), 0f)


    //manage the choices to be display
    val choices = remember(questionItem) {
        questionItem.choices.toMutableList()
    }

    val answerState = remember(questionItem) {
        mutableStateOf<Int?>(null)
    }

    val correctAnswerState = remember(questionItem) {
        mutableStateOf<Boolean?>(null)
    }

    val updateAnswer: (Int) -> Unit = remember(questionItem) {
        {
            answerState.value = it
            correctAnswerState.value = choices[it] == questionItem.answer
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = AppColor.mDarkPurple
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            QuestionTracker(questionIndex.value, total = )
            DrawDoted(pathEffect = pathEffect)

            //question
            Column() {
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f),
                    text = questionItem.question,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    color = AppColor.mOffWhite
                )

                //options
                choices.forEachIndexed { index, answer ->
                    Row(
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .border(
                                width = 4.dp, brush = Brush.linearGradient(
                                    colors = listOf(
                                        AppColor.mOffDarkPurple, AppColor.mOffDarkPurple
                                    )
                                ), shape = RoundedCornerShape(size = 20.dp)
                            )
                            .clip(
                                RoundedCornerShape(
                                    topEndPercent = 50,
                                    topStartPercent = 50,
                                    bottomEndPercent = 50,
                                    bottomStartPercent = 50
                                )
                            )
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = answerState.value === index,
                            onClick = {
                                updateAnswer(index)
                            },
                            modifier = Modifier.padding(start = 16.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (correctAnswerState.value == true && index == answerState.value) {
                                    Color.Green.copy(alpha = 0.2f)
                                } else {
                                    Color.Red.copy(alpha = 0.2f)
                                }
                            )
                        )

                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    color = if (correctAnswerState.value == true && index == answerState.value) {
                                        Color.Green
                                    } else if (correctAnswerState.value == false && index == answerState.value) {
                                        Color.Red.copy(alpha = 0.2f)
                                    } else {
                                        AppColor.mOffWhite
                                    },
                                    fontSize = 17.sp
                                )
                            ) {
                                append(text = answer)
                            }
                        }

                        Text(text = annotatedString, modifier = Modifier.padding(6.dp))
                    }
                }

                Button(
                    onClick = {
                        onNextClicked(questionIndex.value)
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppColor.mLightBlue
                    )
                ) {
                    Text(
                        text = "Next", modifier = Modifier.padding(5.dp),
                        color = AppColor.mOffWhite,
                        fontSize = 17.sp
                    )
                }
            }
        }

    }
}


@Composable
fun QuestionTracker(counter: Int = 10, total: Int = 100) {
    //since we'll have different type text we'll use annotated text
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(
                style = SpanStyle(
                    color = AppColor.mLightGray, fontWeight = FontWeight.Bold, fontSize = 30.sp
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

@Composable
fun DrawDoted(pathEffect: PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            color = AppColor.mLightGray,
            start = Offset(0f, 0f),
            end = Offset(size.width, y = 0f),
            pathEffect = pathEffect
        )
    }
}