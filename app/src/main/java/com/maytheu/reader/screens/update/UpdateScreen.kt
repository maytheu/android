package com.maytheu.reader.screens.update

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.maytheu.reader.components.ReaderAPPBar
import com.maytheu.reader.data.Progress
import com.maytheu.reader.model.Book
import com.maytheu.reader.screens.home.HomeViewModel

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
                }
            }
        }
    }
}

@Composable
fun ShowBookUpdate(bookInfo: Progress<List<Book>, Boolean, Exception>, bookItemId: String) {
    Row() {
        Spacer(modifier = Modifier.width(40.dp))
        if (bookInfo.data != null) {
            Column(modifier = Modifier.padding(5.dp), verticalArrangement = Arrangement.Center) {
                CardListItem(
                    book = bookInfo.data!!.first { bk -> bk.googleBookId == bookItemId },
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
            .clickable { },
        elevation = 4.dp
    ) {
        Row(horizontalArrangement = Arrangement.Start) {

            AsyncImage(
                modifier = Modifier
                    .padding(4.dp)
                    .height(100.dp)
                    .width(120.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 120.dp,
                            topEnd = 20.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    ),
                model = ImageRequest.Builder(LocalContext.current).data(book.photoUrl)
                    //  .transformations(CircleCropTransformation())
                    .build(),
                contentDescription = "book pic"
            )

            Column {
                Text(
                    text = book.title.toString(),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = book.authors.toString(), style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 2.dp,
                        bottom = 0.dp
                    ),
                    fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = book.publishedDate.toString(), style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
