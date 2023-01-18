package com.example.home

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.home.component.InputField
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
    val loginId = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val passwordVisibility = remember {
        mutableStateOf(false)
    }
    androidx.compose.material.Surface(modifier = Modifier.padding(20.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(70.dp))

            InputField(
                modifier = Modifier.fillMaxWidth(),
                valueOfFieldState = loginId,
                label = "Login Id",
                onAction = KeyboardActions {
                    Log.d("TAG", "InfoArea: ${loginId.value}")
                })

            Spacer(modifier = Modifier.height(20.dp))

            InputField(
                valueOfFieldState = password,
                label = "Password",
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = "visibility", modifier = Modifier.clickable {
                            Log.d(
                                "TAG",
                                "InfoArea: icon clicked"
                            )
                        }
                    )
                }, keyboardType = KeyboardType.Password, onAction = KeyboardActions {
                    Log.d("TAG", "InfoArea: ${password.value}")
                }
            )
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