package com.maytheu.reader.screens.update

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.maytheu.reader.R
import com.maytheu.reader.components.*
import com.maytheu.reader.data.Progress
import com.maytheu.reader.model.Book
import com.maytheu.reader.navigation.ReaderScreens
import com.maytheu.reader.screens.home.HomeViewModel
import com.maytheu.reader.utils.formatDate

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UpdateScreen(
    navController: NavController,
    bookItemId: String,
    viewmodel: HomeViewModel,
) {
    Scaffold(topBar = {
        ReaderAPPBar(
            title = "Update Book",
            navController = navController,
            showProfile = false,
            icon = Icons.Default.ArrowBack
        ) {
            navController.popBackStack()
        }
    }) {
//        val bookInfo =
//            produceState<Progress<List<Book>, Boolean, Exception>>(
//                initialValue = Progress(
//                    emptyList(),
//                    true,
//                    Exception("")
//                )
//            ) {
//                value = viewmodel.books.value
//            }.value
        viewmodel.getBooksFromDb()

        if (!viewmodel.books.value.data.isNullOrEmpty()) {
            val bookInfo = viewmodel.books.value


            Surface(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.padding(top = 5.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Log.d("TAG", "UpdateScreen: ${bookInfo.loading} ${bookInfo.data}")
//                    if (bookInfo.loading == true) {
//                        LinearProgressIndicator()
//                        bookInfo.loading = false
//                    } else {
                    Surface(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth(),
                        shape = CircleShape,
                        elevation = 5.dp
                    ) {
                        ShowBookUpdate(bookInfo = bookInfo, bookItemId)
//                        }
                    }

                    showSimpleForm(
                        book = bookInfo.data!!.first { bk -> bk.googleBookId == bookItemId },
                        navController
                    )
                }
            }
        }
    }
}

@Composable
fun showSimpleForm(book: Book, navController: NavController) {
    val context = LocalContext.current
    val noteTextVal = remember {
        mutableStateOf("")
    }
    val isStartReading = remember {
        mutableStateOf(false)
    }
    val isFinishReading = remember {
        mutableStateOf(false)
    }
    val ratingValue = remember {
        mutableStateOf(0)
    }

    SimpleForm(
        defaultValue = if (book.notes.toString()
                .isNotEmpty()
        ) book.notes.toString() else "No Thoughts available"
    ) { note ->
        noteTextVal.value = note
    }

    Row(
        modifier = Modifier.padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextButton(
            onClick = { isStartReading.value = true }, enabled = book.startedReading == null
        ) {
            if (book.startedReading == null) { //book return null from db
                if (!isStartReading.value) {
                    Text(text = "Start Reading")
                } else {
                    Text(
                        text = "Started Reading",
                        modifier = Modifier.alpha(0.6f),
                        color = Color.Red.copy(alpha = 0.5f)
                    )
                }
            } else {
                Text(text = "Started: ${formatDate(book.startedReading!!)}")
            }
        }
        Spacer(modifier = Modifier.height(5.dp))

        TextButton(
            onClick = { isFinishReading.value = true }, enabled = book.finishedReading == null
        ) {
            if (book.finishedReading == null) {
                if (!isFinishReading.value) {
                    Text(text = "Mark as Read")
                } else {
                    Text(text = "Finished Reading")
                }
            } else {
                Text(text = "Finished on: ${formatDate(book.finishedReading!!)}")
            }
        }
    }

    Text(text = "Ratins", modifier = Modifier.padding(5.dp))
    book.rating?.toInt().let {
        RatingBar(rating = it!!) { rate ->
            ratingValue.value = rate
        }
    }

    Spacer(modifier = Modifier.padding(bottom = 15.dp))

    Row(modifier = Modifier.padding(top = 10.dp)) {
        //check if update is important from db
        val checkNote = book.notes != noteTextVal.value
        val checkRating = book.rating?.toInt() != ratingValue.value
        val isFinishedTime = if (isFinishReading.value) Timestamp.now() else book.finishedReading
        val isStartedTime = if (isStartReading.value) Timestamp.now() else book.startedReading

        val bookUpdate = checkNote || checkRating || isFinishReading.value || isStartReading.value

        val bookToUpdate = hashMapOf(
            "finished_reading" to isFinishedTime,
            "started_reading" to isStartedTime,
            "notes" to noteTextVal.value,
            "rating" to ratingValue.value
        ).toMap()

        CardButtonRounded(label = "Update") {
            if (bookUpdate) {
                FirebaseFirestore.getInstance().collection("books").document(book.id!!)
                    .update(bookToUpdate).addOnCompleteListener { task ->
                        showToast(context, "Book updated successfully")
                        navController.navigate(ReaderScreens.HomeScreen.name)
                    }.addOnFailureListener {
                        showToast(context, "Error updating book")
                    }
            }
        }

        Spacer(modifier = Modifier.width(100.dp))

        //show dialog before deleting
        val openDialog = remember { mutableStateOf(false) }
        if (openDialog.value) {
            ShowAlertDialog(
                message = "Are you sure you want to delete this book? \n" + stringResource(
                    id = R.string.action
                ), openDialog
            ) {
                FirebaseFirestore.getInstance().collection("books").document(book.id!!).delete()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            openDialog.value = false
                            showToast(context, "Book deleted successfully")
                            navController.navigate(ReaderScreens.HomeScreen.name) //to recompose the home
                        }
                    }
            }
        }
        CardButtonRounded(label = "Delete") {
            openDialog.value = true
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    defaultValue: String = "Great Book",
    onSearch: (String) -> Unit = {},
) {
    Column() {
        val textFieldVal = remember {
            mutableStateOf(defaultValue)
        }
        val keyboardCtrl = LocalSoftwareKeyboardController.current
        val valid = remember(textFieldVal.value) {
            textFieldVal.value.trim().isNotEmpty()
        }

        InputField(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(5.dp)
            .background(Color.White, CircleShape)
            .padding(horizontal = 20.dp, vertical = 15.dp),
            valueState = textFieldVal,
            label = "Your thoughts",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(textFieldVal.value.trim())
                keyboardCtrl?.hide()
            })
    }
}

@Composable
fun ShowBookUpdate(bookInfo: Progress<List<Book>, Boolean, Exception>, bookItemId: String) {
    Row() {
        Spacer(modifier = Modifier.width(40.dp))
        if (bookInfo.data != null) {
            Column(modifier = Modifier.padding(5.dp), verticalArrangement = Arrangement.Center) {
                CardListItem(book = bookInfo.data!!.first { bk -> bk.googleBookId == bookItemId },
                    onPressDetails = {})
            }
        }
    }
}

@Composable
fun CardListItem(book: Book, onPressDetails: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, top = 5.dp, bottom = 8.dp)
            .clip(
                RoundedCornerShape(20.dp)
            )
            .clickable { }, elevation = 4.dp
    ) {
        Row(horizontalArrangement = Arrangement.Start) {

            AsyncImage(
                modifier = Modifier
                    .padding(4.dp)
                    .height(100.dp)
                    .width(120.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 120.dp, topEnd = 20.dp, bottomEnd = 0.dp, bottomStart = 0.dp
                        )
                    ), model = ImageRequest.Builder(LocalContext.current).data(book.photoUrl)
                    //  .transformations(CircleCropTransformation())
                    .build(), contentDescription = "book pic"
            )

            Column {
                Text(
                    text = book.title.toString(),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = book.authors.toString(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(
                        start = 8.dp, end = 8.dp, top = 2.dp, bottom = 0.dp
                    ),
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = book.publishedDate.toString(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ShowAlertDialog(message: String, openDialog: MutableState<Boolean>, onYesPressed: () -> Unit) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Delete Book") },
            text = { Text(text = message) },
            buttons = {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(onClick = { onYesPressed.invoke() }) {
                        Text(text = "Yes")
                    }

                    TextButton(onClick = { openDialog.value = false }) {
                        Text(text = "No")
                    }
                }
            })
    }

}