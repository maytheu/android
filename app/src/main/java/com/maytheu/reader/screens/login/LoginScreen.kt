package com.maytheu.reader.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maytheu.reader.components.EmailInput
import com.maytheu.reader.components.PasswordInput
import com.maytheu.reader.components.ReaderLogo
import com.maytheu.reader.components.SubmitButton

@Preview
@Composable
fun LoginScreen(navController: NavController = NavController(context = LocalContext.current)) {
    val showUserForm = rememberSaveable {
        mutableStateOf(true)
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ReaderLogo()
            if (showUserForm.value) UserForm(
                isLoading = false,
                isCreateAcc = false
            ) { email, password ->
                Log.d("TAG", "LoginScreen: $email $password")
            } else UserForm(isCreateAcc = true) { email, password ->
                //create account
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.padding(20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val link = if (showUserForm.value) "Sign up" else "Login"
                val text = if (showUserForm.value) "Mew User?" else "Returning User?"

                Text(text = text)
                Text(
                    text = link,
                    modifier = Modifier
                        .clickable { showUserForm.value = !showUserForm.value }
                        .padding(start = 5.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondaryVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    isLoading: Boolean = false,
    isCreateAcc: Boolean = false,
    onDone: (email: String, password: String) -> Unit = { email: String, password: String -> },
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    Column(
        modifier = Modifier
            .height(250.dp)
            .background(MaterialTheme.colors.background)
            .verticalScroll(
                rememberScrollState()
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isCreateAcc) Text(
            text = "Please enter a valid email address and password that is six characters",
            modifier = Modifier.padding(5.dp)
        )

        EmailInput(
            emailState = email,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            },
            enabled = !isLoading,
        )

        PasswordInput(passwordState = password,
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordVisibility = passwordVisibility,
            enabled = !isLoading,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
                keyboardController?.hide()
            })

        SubmitButton(
            textId = if (isCreateAcc) "Create Account" else "Login",
            loading = isLoading,
            validInput = valid
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

