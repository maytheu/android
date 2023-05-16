package com.example.home.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.home.component.ImageScreen
import com.example.home.component.InputField
import com.example.home.model.LoginData
import com.example.home.navigation.ParrotScreens
import com.example.home.screen.login.LoginViewModel
import com.example.home.widgets.login.InfoArea

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    Surface(
        modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.background
    ) {
        val loginId = remember {
            mutableStateOf("")
        }
        val password = remember {
            mutableStateOf("")
        }
        Column() {
            ImageScreen(300.dp)

            InfoArea(loginId, password) {
                //TODO make network request
                val loginData = LoginData(userId = loginId.value, password = password.value)
                viewModel.loginUser(loginData).run {
                    Log.d("Login", "LoginScreen: ${viewModel.isUser.value}")
                    if (viewModel.isUser.value.data == true) {
                        navController.navigate(route = ParrotScreens.HomeScreen.name)
                    } else {//TODO show toast
                        Log.d("Invalid", "LoginScreen: cannot log in")
                    }
                }

            }
        }

    }
}



