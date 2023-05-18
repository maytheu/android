package com.example.home.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.home.component.ImageScreen
import com.example.home.model.LoginData
import com.example.home.model.User
import com.example.home.model.UserDb
import com.example.home.navigation.ParrotScreens
import com.example.home.screen.home.HomeViewModel
import com.example.home.screen.login.LoginViewModel
import com.example.home.utils.Progress
import com.example.home.widgets.login.InfoArea

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: LoginViewModel,
    userViewModel: HomeViewModel,
) {
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
                        value = authViewModel.login(loginData)
                    }.value
                //                    TODO("show toast")
                if (login.loading == true) {
                    Log.d("Login", "LoginScreen: loading")
                } else {
                    if (login.data == null) {
                        Log.d("Login", "LoginScreen: ${login.e}")
                        makeRequest.value = false
                    } else {
                        val user = login.data
                        val userDbData =
                            user?.let {
                                UserDb(
                                    userId = it.userId,
                                    companyName = it.companyName,
                                    userCompanyId = it.userCompanyId,
                                    logo = it.logo
                                )
                            }
                        userViewModel.logout()
                        if (userDbData != null) {
                            userViewModel.saveUserToDb(userDbData)
                        }
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



