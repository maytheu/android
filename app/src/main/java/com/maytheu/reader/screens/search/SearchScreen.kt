package com.maytheu.reader.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.maytheu.reader.components.InputField
import com.maytheu.reader.components.ReaderAPPBar
import com.maytheu.reader.navigation.ReaderScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController: NavController) {
    Scaffold(topBar = {
        ReaderAPPBar(
            title = "Search Books",
            icon = Icons.Default.ArrowBack,
            navController = navController,
            showProfile = false
        ) {
            navController.popBackStack()
        }
    }) {
        Surface() {
            Column {
                SearchForm(modifier = Modifier.fillMaxWidth().padding(20.dp))
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {},
) {
    Column() {
        val queryState = rememberSaveable {//used to avoid state loss when screen rotated
            mutableStateOf("")
        }
        val keybdCtrl = LocalSoftwareKeyboardController.current
        val valid = remember(queryState.value) {
            queryState.value.trim().isNotEmpty()
        }

        InputField(
            valueState = queryState,
            label = "Search",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(queryState.value.trim())
                queryState.value = ""
                keybdCtrl?.hide()
            })
    }
}