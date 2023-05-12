package com.maytheu.reader.screens.home

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseAuth
import com.maytheu.reader.components.*
import com.maytheu.reader.model.Book
import com.maytheu.reader.navigation.ReaderScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController = NavController(LocalContext.current)) {
    Scaffold(topBar = { ReaderAPPBar(title = "Reader", navController = navController) },
        floatingActionButton = {
            FABContent {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }) {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeContent(navController)
        }
    }
}

@Composable
fun HomeContent(navController: NavController = NavController(LocalContext.current)) {
    val currentUser =
        if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) FirebaseAuth.getInstance().currentUser?.email?.split(
            "@"
        )?.get(0) else "N/A"

    val testBook = listOf<Book>(
        Book(title = "test1", notes = "testtest1"),
        Book(title = "test2", notes = "testtest2"),
        Book(title = "test3", notes = "testtest3"),
        Book(title = "test4", notes = "testtest4")
    )

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
        ReadingArea(book = listOf(), navController = navController)

        TitleSection(label = "Reading list")
        BookListArea(listOfBooks = testBook, navController = navController)
    }
}

@Composable
fun BookListArea(listOfBooks: List<Book>, navController: NavController) {
    HorizontalBookScroll(listOfBooks = listOfBooks) {
        navController.navigate(ReaderScreens.BookDetailsScreen.name)
    }
}

@Composable
fun HorizontalBookScroll(listOfBooks: List<Book>, onBookPressed: (String) -> Unit) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(300.dp)
            .horizontalScroll(scrollState)
    ) {
        //show all books by looping
        for (book in listOfBooks) {
            BookCard(book = book) {
                onBookPressed(it)
            }

        }
    }
}

@Composable
fun ReadingArea(book: List<Book>, navController: NavController) {
    BookCard()

}


