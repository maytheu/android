package com.maytheu.reader.screens.stats

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.sharp.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.firebase.auth.FirebaseAuth
import com.maytheu.reader.components.ReaderAPPBar
import com.maytheu.reader.model.Book
import com.maytheu.reader.model.Item
import com.maytheu.reader.navigation.ReaderScreens
import com.maytheu.reader.screens.home.HomeViewModel
import com.maytheu.reader.screens.search.BookRow
import com.maytheu.reader.utils.formatDate
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
                        modifier = Modifier.padding(4.dp)
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

                if (homeViewModel.books.value.loading == true) {
                    LinearProgressIndicator()
                } else {
                    Divider()
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentPadding = PaddingValues(20.dp)
                    ) {
                        //filter read & finish books
                        val finishedBooks = if (!homeViewModel.books.value.data.isNullOrEmpty()) {
                            homeViewModel.books.value.data!!.filter { book -> (book.userId == currentUser?.uid) && book.finishedReading != null }
                        } else {
                            emptyList()
                        }
                        items(items = finishedBooks) { book ->
                            BookRowStats(book = book)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookRowStats(book: Book) {
    val imageUrl = book.photoUrl?.ifEmpty {
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
            .fillMaxWidth(),
//            .clickable {
//                navController.navigate("${ReaderScreens.BookDetailsScreen.name}/${book.id}")
//            },
        shape = RectangleShape, elevation = 6.dp
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
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis)
                    if (book.rating!! >= 4) {
                        Spacer(modifier = Modifier.fillMaxWidth(0.8f))
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "like",
                            tint = Color.Green.copy(alpha = 0.5f)
                        )
                    }
                }
                Text(
                    text = "Author: ${book.authors}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic
                )

                Text(
                    text = "Started Reading: ${book.startedReading?.let { formatDate(it) }}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic,
                    softWrap = true
                )

                Text(
                    text = "Finish Reading: ${formatDate(book.finishedReading!!)}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic
                )

            }

        }

    }
}