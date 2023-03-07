package com.maytheu.weather.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maytheu.weather.widgets.WeatherAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeatherSearchScreen(navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            navController = navController,
            isMainScreen = false,
            icon = Icons.Default.ArrowBack,
            title = "Search..."
        ) {
            navController.popBackStack()
        }
    }) {
        Surface() {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchField()

            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchField(onSearch: (String) -> Unit = {}) {
    val searchQueryState = rememberSaveable {
        mutableStateOf<String>("")
    }
    val keyboardCtrler = LocalSoftwareKeyboardController.current
    val validState = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotBlank()
    }
    Column {
        CommonTextField(valueState = searchQueryState,
            placeholder = "Lagos",
            onAction = KeyboardActions {
//        if()
            })
    }
}

@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    placeholder: String,
    onAction: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeActions: ImeAction = ImeAction.Next,
) {
    OutlinedTextField(value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(placeholder) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeActions),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Blue, cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    )
}
