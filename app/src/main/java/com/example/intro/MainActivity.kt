package com.example.intro

import android.graphics.Paint.Align
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intro.ui.theme.IntroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntroTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val counter = remember { mutableStateOf(0) }
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xFF546E7A)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$${counter.value}",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 45.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )

            Spacer(modifier = Modifier.height(100.dp))

            CreateCircle(counter.value, updateCounter = { newVal ->
                counter.value = newVal
            })
        }
    }
}

@Composable
fun CreateCircle(counter: Int, updateCounter: (Int) -> Unit) {
    //inform composable t update
//    var counter  by remember { mutableStateOf(0) }
    Card(
        modifier = Modifier
            .padding(3.dp)
            .size(105.dp)
            .clickable {

                updateCounter(counter + 1)
//                counter += 1, only valid suing by remember
            },
        shape = CircleShape,
        elevation = 5.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = "Tap")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IntroTheme {
        MyApp()
    }
}