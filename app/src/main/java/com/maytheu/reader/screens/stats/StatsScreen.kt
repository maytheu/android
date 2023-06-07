package com.maytheu.reader.screens.stats

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.sharp.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.maytheu.reader.components.ReaderAPPBar
import com.maytheu.reader.model.Book
import com.maytheu.reader.screens.home.HomeViewModel
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StatsScreen(navController: NavController, homeViewModel: HomeViewModel) {
    var books: List<Book>
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(topBar = {
        ReaderAPPBar(
            title = "Book Stats",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false
        ) {
            navController.popBackStack()
        }
    }) {
        Surface {
            //show only the books that have been read
            books = if (!homeViewModel.books.value.data.isNullOrEmpty()) {
                homeViewModel.books.value.data!!.filter { book ->
                    book.userId == currentUser?.uid
                }
            } else {
                emptyList()
            }
            Column {
                Row {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                    ) {
                        Icon(imageVector = Icons.Sharp.Person, contentDescription = "Icon")
                        Text(
                            text = currentUser?.email.toString()
                                .split("@")[0].uppercase(Locale.getDefault()),
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp),
                    shape = CircleShape,
                    elevation = 5.dp
                ) {
                    val readBook = if (!homeViewModel.books.value.data.isNullOrEmpty()) {
                        books.filter { book ->
                            (book.userId == currentUser?.uid) && book.finishedReading != null
                        }
                    } else {
                        emptyList()
                    }

                    //filter the books that belongs to the user
                    val userReading =
                        books.filter { book -> (book.startedReading != null) && (book.finishedReading == null) }

                    Column(
                        modifier = Modifier.padding(start = 25.dp, top = 5.dp, bottom = 5.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = "Your Stats", style = MaterialTheme.typography.h5)
                        Divider()
                        Text(text = "You're reading: ${userReading.size} books")
                        Text(text = "You've read: ${readBook.size} books")
                    }
                }
            }
        }
    }
}