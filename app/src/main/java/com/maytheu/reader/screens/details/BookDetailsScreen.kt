package com.maytheu.reader.screens.details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.maytheu.reader.components.ReaderAPPBar
import com.maytheu.reader.data.DataResource
import com.maytheu.reader.model.Item

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: BookDetailsViewModel,
) {
    Scaffold(topBar = {
        ReaderAPPBar(
            title = "Details",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false
        ) {
            navController.popBackStack()
        }
    }) {
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(top = 15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //instead of using coroutine scope in vm
                val bookInfo =
                    produceState<DataResource<Item>>(initialValue = DataResource.Loading()) {
                        value = viewModel.getBookInfo(bookId)
                    }.value

                if (bookInfo.data == null && bookInfo.message == null) {
                    LinearProgressIndicator()
                } else if (bookInfo.message?.isNotEmpty() == true) {
                    Text(bookInfo.message!!)
                } else {
                    Text(bookInfo.data?.volumeInfo?.title!!)

                }
            }
        }
    }
}