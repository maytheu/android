package com.example.home.widgets.login

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.home.component.InputField
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.R
import androidx.compose.material.*
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.home.component.ButtonField


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InfoArea(
    loginId: MutableState<String>,
    password: MutableState<String>,
    onLogin: () -> Unit = {}
) {
    val loginIdValid = remember(loginId.value) {
        loginId.value.trim().isNotEmpty()
    }

    val passwordValid = remember(password.value) {
        password.value.trim().isNotEmpty()
    }
    val passwordVisibility = remember {
        mutableStateOf(false)
    }
    val keyController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(70.dp))

            InputField(
                modifier = Modifier.fillMaxWidth(),
                valueOfFieldState = loginId,
                label = "Login Id",
                onAction = KeyboardActions {
                    if (!loginIdValid) return@KeyboardActions
                    keyController?.hide()
                })

            Spacer(modifier = Modifier.height(20.dp))

            InputField(
                valueOfFieldState = password,
                label = "Password",
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                        Icon(
                            imageVector = if (passwordVisibility.value) Icons.Filled.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisibility.value) "Hide password" else "Show password",
                        )
                    }
                },
                keyboardType = KeyboardType.Password,
                onAction = KeyboardActions {
                    if (!passwordValid) return@KeyboardActions
                    keyController?.hide()
                },
                visualTransformation = if (!passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
            )

            Spacer(modifier = Modifier.height(10.dp))

            ButtonField(
                enabled = loginIdValid && passwordValid,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
//                colors = ButtonColors(),
                action = {
                    onLogin()
                }) {
                Text(text = "Log in")
            }
        }
    }
}
