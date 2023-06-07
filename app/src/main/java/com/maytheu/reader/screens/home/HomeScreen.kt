package com.maytheu.reader.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.maytheu.reader.components.*
import com.maytheu.reader.model.Book
import com.maytheu.reader.navigation.ReaderScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController = NavController(LocalContext.current),
    viewModel: HomeViewModel,
) {
    Scaffold(topBar = { ReaderAPPBar(title = "Reader", navController = navController) },
        floatingActionButton = {
            FABContent {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }) {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeContent(navController, viewModel = viewModel)
        }
    }
}

@Composable
fun HomeContent(
    navController: NavController = NavController(LocalContext.current),
    viewModel: HomeViewModel,
) {
    val currentUser =
        if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) FirebaseAuth.getInstance().currentUser?.email?.split(
            "@"
        )?.get(0) else "N/A"

    val user = FirebaseAuth.getInstance().currentUser

    var books = emptyList<Book>()

    val testBook = listOf<Book>(
        Book(title = "test1", notes = "testtest1"),
        Book(title = "test2", notes = "testtest2"),
        Book(title = "test3", notes = "testtest3"),
        Book(title = "test4", notes = "testtest4")
    )

    if (!viewModel.books.value.data.isNullOrEmpty()) {
        books = viewModel.books.value.data!!.toList().filter { book ->
            //filter all books from db based on user
            book.userId == user?.uid.toString()
        }
    }

    Column(modifier = Modifier.padding(5.dp), verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your Reading \n Reading now...")

            Spacer(modifier = Modifier.fillMaxWidth(0.7f))

            Column {
                Icon(imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.StatsScreen.name)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colors.secondaryVariant)
                Text(
                    text = currentUser!!,
                    modifier = Modifier.padding(3.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.Red.copy(alpha = 0.5f),
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Divider()


            }
        }
        ReadingArea(book =books, navController = navController, viewModel)

        TitleSection(label = "Reading list")
        BookListArea(listOfBooks = books, navController = navController, viewModel)
    }
}

@Composable
fun BookListArea(listOfBooks: List<Book>, navController: NavController, viewModel: HomeViewModel) {
    val addedBooks =
        listOfBooks.filter { book -> book.startedReading == null && book.finishedReading == null }

    HorizontalBookScroll(listOfBooks = addedBooks, viewModel) {
        navController.navigate("${ReaderScreens.UpdateScreen.name}/$it")
    }
}

@Composable
fun HorizontalBookScroll(
    listOfBooks: List<Book>,
    viewModel: HomeViewModel,
    onBookPressed: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(300.dp)
            .horizontalScroll(scrollState)
    ) {
        if (viewModel.books.value.loading == true) {
            LinearProgressIndicator()
        } else {
            if (listOfBooks.isNullOrEmpty()) {
                Surface(modifier = Modifier.padding(25.dp)) {
                    Text(
                        text = "No books found", style = TextStyle(
                            color = Color.Red.copy(alpha = 0.4f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }
            } else {
                //show all books by looping
                for (book in listOfBooks) {
                    BookCard(book = book) {
                        onBookPressed(book.googleBookId.toString())
                    }

                }
            }
        }
    }
}

@Composable
fun ReadingArea(book: List<Book>, navController: NavController, viewModel: HomeViewModel) {
    val readingNow = book.filter {
        it.startedReading != null && it.finishedReading == null
    }
    HorizontalBookScroll(listOfBooks = readingNow, viewModel = viewModel) {
        navController.navigate("${ReaderScreens.StatsScreen.name}")
    }
}


