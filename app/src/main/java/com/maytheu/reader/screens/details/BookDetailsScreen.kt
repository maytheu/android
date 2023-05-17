package com.maytheu.reader.screens.details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.maytheu.reader.components.CardButtonRounded
import com.maytheu.reader.components.ReaderAPPBar
import com.maytheu.reader.data.DataResource
import com.maytheu.reader.model.Book
import com.maytheu.reader.model.Item
import com.maytheu.reader.model.VolumeInfo

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
                    Row() {
                        LinearProgressIndicator()
                    }
                } else if (bookInfo.message?.isNotEmpty() == true) {
                    Text(bookInfo.message!!)
                } else {
                    BookDetails(
                        bookInfo = bookInfo.data?.volumeInfo!!,
                        navController = navController, bookId = bookId
                    )

                }
            }
        }
    }
}

@Composable
fun BookDetails(bookInfo: VolumeInfo, navController: NavController, bookId: String) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
    BookImage(image = bookInfo.imageLinks.smallThumbnail)
    AboutBook(bookInfo = bookInfo)
    Spacer(modifier = Modifier.height(15.dp))
    BookDesc(desc = bookInfo.description)

    Row(modifier = Modifier.padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceAround) {
        CardButtonRounded(label = "Save") {

        }

        Spacer(modifier = Modifier.width(50.dp))
//save to db
        val book = Book("1", "", "", "")
        saveToFirebase(book)
        CardButtonRounded("Back") {
            navController.popBackStack()
        }
    }
}


//    }


@Composable
fun BookDesc(desc: String) {
    val cleanDesc = HtmlCompat.fromHtml(desc, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    val phoneDims = LocalContext.current.resources.displayMetrics

    Surface(
        modifier = Modifier
            .height(phoneDims.heightPixels.dp.times(0.09f))
            .padding(5.dp),
        shape = RectangleShape,
        border = BorderStroke(width = 1.dp, Color.DarkGray)
    ) {
        LazyColumn(modifier = Modifier.padding(5.dp)) {
            item {
                Text(text = cleanDesc, modifier = Modifier.padding(10.dp))
            }
        }
    }

}

@Composable
fun AboutBook(bookInfo: VolumeInfo) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = bookInfo.title,
            style = MaterialTheme.typography.h5,
            overflow = TextOverflow.Ellipsis,
            maxLines = 20
        )
        Text(text = "Authors: ${bookInfo.authors}")
        Text(text = "PageCount: ${bookInfo.pageCount}")
        Text(
            text = "Categories: ${bookInfo.categories}",
            style = MaterialTheme.typography.subtitle1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3
        )
        Text(
            text = "Published: ${bookInfo.publishedDate}",
            style = MaterialTheme.typography.subtitle1
        )

    }
}

@Composable
fun BookImage(image: String) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(image).crossfade(true)
            .size(Size.ORIGINAL).build()
    )

    Card(modifier = Modifier.padding(40.dp), shape = CircleShape, elevation = 5.dp) {
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .height(90.dp)
                .width(90.dp)
                .padding(2.dp)
        )
    }
}


fun saveToFirebase(book: Book) {
    val db = FirebaseFirestore.getInstance()

}