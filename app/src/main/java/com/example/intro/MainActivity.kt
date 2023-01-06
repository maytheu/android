package com.example.intro

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun MyApp(){
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color(0xFF546E7A)
    ) {
    }
}
@Preview
@Composable
fun CreateCircle(){
    Card(modifier = Modifier
        .padding(3.dp)
        .size(45.dp), shape = CircleShape) {
        Box(contentAlignment = Alignment.Center){
            Text(text = "tap")
        }
    }
}


@Preview(showBackground = true,)
@Composable
fun DefaultPreview() {
    IntroTheme {
MyApp()
    }
}