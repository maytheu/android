package com.maytheu.reader.screens.home

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.maytheu.reader.components.FABContent
import com.maytheu.reader.components.ReaderAPPBar
import com.maytheu.reader.components.TitleSection
import com.maytheu.reader.model.Book
import com.maytheu.reader.navigation.ReaderScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun HomeScreen(navController: NavController = NavController(LocalContext.current)) {
    Scaffold(topBar = { ReaderAPPBar(title = "Reader", navController = navController) },
        floatingActionButton = {
            FABContent {

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

    Column(modifier = Modifier.padding(5.dp), verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your Reading \n Reading now...")

            Spacer(modifier = Modifier.fillMaxSize(0.7f))

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

        BookCard()

    }
}


@Composable
fun ReadingArea(book: List<Book>, navController: NavController) {
}

@Preview
@Composable
fun BookCard(
    book: Book = Book(id = "1", authors = "me", title = "Potter"),
    onPressDetials: (String) -> Unit = {},
) {
    //screen size info
    val context = LocalContext.current
    val resources = context.resources
    val screen = resources.displayMetrics
    val screenWidth = screen.widthPixels / screen.density

    val spacing = 10.dp

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data("https://robohash.org/test.jpg")
            .crossfade(true).size(Size.ORIGINAL).build()
    )
    Card(
        modifier = Modifier
            .height(250.dp)
            .width(200.dp)
            .padding(20.dp)
            .clickable { onPressDetials.invoke(book.id.toString()) },
        elevation = 10.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(40.dp)
    ) {
        Column(
            modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data("https://robohash.org/test.jpg")
//                        .transformations(CircleCropTransformation()).build(),
//                    contentDescription = "Book image",
//                    modifier = Modifier
//                        .height(150.dp)
//                        .width(100.dp)
//                        .padding(5.dp)
//                )


                Image(
                    painter = painter,
                    contentDescription = "",
                    modifier = Modifier
                        .height(150.dp)
                        .width(100.dp)
                        .padding(5.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier.padding(top = 30.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = "Fav icon",
                        modifier = Modifier.padding(bottom = 2.dp)
                    )



                    BottomRating(score = 3.5)
                }
            }

            Text(
                text = book.title.toString(),
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = book.authors.toString(),
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun BottomRating(score: Double) {
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .height(70.dp),
        shape = RoundedCornerShape(60.dp),
        elevation = 5.dp,
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Icon(
                imageVector = Icons.Filled.StarBorder,
                contentDescription = "Star icon",
                modifier = Modifier.padding(2.dp)
            )

            Text(text = score.toString(), style = MaterialTheme.typography.subtitle1)
        }
    }
}

