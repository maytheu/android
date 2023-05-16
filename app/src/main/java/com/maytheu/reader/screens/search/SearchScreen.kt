package com.maytheu.reader.screens.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.maytheu.reader.components.InputField
import com.maytheu.reader.components.ReaderAPPBar
import com.maytheu.reader.model.Book
import com.maytheu.reader.model.Item

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
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
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                ) { query ->
                    viewModel.updatedSearch(query)

                }

                Spacer(modifier = Modifier.height(15.dp))

                BookList(navController, viewModel)
            }
        }
    }
}

@Composable
fun BookList(navController: NavController, viewModel: SearchViewModel) {
    val testBook = listOf<Book>(
        Book(title = "test1", notes = "testtest1"),
        Book(title = "test2", notes = "testtest2"),
        Book(title = "test3", notes = "testtest3"),
        Book(title = "test4", notes = "testtest4")
    )

    val listOfBooks = viewModel.updateSearchList


    if (viewModel.loading) {
        LinearProgressIndicator()
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
            items(items = listOfBooks) { book ->
                BookRow(book = book, navController)
            }
        }
    }
}

@Composable
fun BookRow(book: Item, navController: NavController) {
    val imageUrl = book.volumeInfo.imageLinks.smallThumbnail.ifEmpty {
        "https://robohash.org/test.jpg"
    }
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true)
            .size(Size.ORIGINAL).build()
    )

    Card(
        modifier = Modifier
            .padding(3.dp)
            .height(120.dp)
            .fillMaxWidth()
            .clickable { },
        shape = RectangleShape,
        elevation = 6.dp
    ) {
        Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.Top) {
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .height(150.dp)
                    .width(80.dp)
                    .padding(end = 4.dp)
            )

            Column() {
                Text(text = book.volumeInfo.title.toString(), overflow = TextOverflow.Ellipsis)
                Text(
                    text = "Author: ${book.volumeInfo.authors}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic
                )

                Text(
                    text = "Date: ${book.volumeInfo.publishedDate}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic
                )

                Text(
                    text = "Category: ${book.volumeInfo.categories}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic
                )

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

        InputField(valueState = queryState,
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