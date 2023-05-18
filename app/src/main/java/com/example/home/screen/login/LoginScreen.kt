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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
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
import com.example.home.component.ButtonField
import com.example.home.component.ImageScreen
import com.example.home.component.InputField
import com.example.home.model.LoginData
import com.example.home.model.User
import com.example.home.navigation.ParrotScreens
import com.example.home.screen.login.LoginViewModel
import com.example.home.utils.Progress
import com.example.home.widgets.login.InfoArea

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    val loginId = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    val makeRequest = remember { mutableStateOf(false) }



    Surface(
        modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.background
    ) {

        Column() {
            ImageScreen(300.dp)
            val loginData = LoginData(userId = loginId.value, password = password.value)

            if (makeRequest.value) {
                val login =
                    produceState<Progress<User, Boolean, Exception>>(initialValue = Progress(loading = true)) {
                        value = viewModel.login(loginData)
                    }.value
                //                    TODO("show toast")
                if (login.loading == true) {
                    Log.d("Login", "LoginScreen: loading")
                } else {
                    if (login.data == null) {
                        Log.d("Login", "LoginScreen: ${login.e}")
                        makeRequest.value = false
                    } else {
                        //                    TODO("save user data to db")
                        navController.navigate(route = ParrotScreens.HomeScreen.name)
                    }
                }
            }

            InfoArea(loginId, password, makeRequest.value) {
                if (loginData.userId != "" && loginData.password != "") {
                    makeRequest.value = true
                }
            }

        }

    }
}



