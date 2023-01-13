package com.example.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.home.ui.theme.HomeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeTheme {
                App {
                    Column() {
                        TopHeader()
                        InfoArea()
                    }
                }
            }
        }
    }
}

@Composable
fun App(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.background
    ) {
        content()
    }
}

@Composable
fun TopHeader() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp), color = Color(0xFF1E2AE3)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(80.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo"
            )
            Text(
                text = "Parrot Home",
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFFFFF)
            )
        }
    }
}

@Composable
fun InfoArea() {
    androidx.compose.material.Surface(modifier = Modifier.padding(20.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Welcome, A&T")
            Text(text = "Welcome, A&T")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeTheme {
        App {
            Column() {
                TopHeader()
                InfoArea()
            }
        }
    }
}