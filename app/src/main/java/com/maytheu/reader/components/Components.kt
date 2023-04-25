package com.maytheu.reader.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.firebase.auth.FirebaseAuth
import com.maytheu.reader.model.Book
import com.maytheu.reader.navigation.ReaderScreens

//logo composable
@Composable
fun ReaderLogo(modifier: Modifier = Modifier) {
    Text(
        text = "ReAdEr",
        modifier = modifier.padding(bottom = 10.dp),
        style = MaterialTheme.typography.h3,
        color = Color.Red.copy(alpha = 0.5f)
    )
}


@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    label: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Email,
) {
    InputField(
        valueState = emailState,
        label = label,
        enabled = enabled,
        modifier = modifier,
        imeAction = imeAction,
        onAction = onAction,
        keyboardType = keyboardType
    )
}

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    passwordState: MutableState<String>,
    label: String = "Password",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    passwordVisibility: MutableState<Boolean>,
) {

    val visualTransformation =
        if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = label) },
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxSize(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = onAction,
        visualTransformation = visualTransformation,
        trailingIcon = { PasswordVisibility(passwordVisibility) }
    )
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val passvisibility = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !passvisibility }) {
        Icons.Default.Close
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    label: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = label) },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxSize(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction
    )
}

@Composable
fun SubmitButton(textId: String, loading: Boolean, validInput: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        enabled = !loading && validInput,
        shape = CircleShape
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp)) else Text(
            text = textId, modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun TitleSection(modifier: Modifier = Modifier, label: String) {
    Surface(modifier = modifier.padding(start = 5.dp, top = 3.dp)) {
        Column {
            Text(
                text = label,
                fontSize = 20.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left
            )
        }
    }
}



@Composable
fun BookRating(score: Double) {
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
@Composable
fun ReaderAPPBar(title: String, navController: NavController, showProfile: Boolean = true) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showProfile) {
                    Icon(
                        imageVector = Icons.Default.Favorite, contentDescription = "icon",
                        modifier = Modifier
                            .scale(0.9f)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Text(
                        text = title,
                        color = Color.Red.copy(alpha = 0.7f),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    )

                    Spacer(modifier = Modifier.width(200.dp))


                }
            }
        },
        actions = {
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut().run {
                    navController.navigate(ReaderScreens.LoginScreen.name)
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Logout",
                )
            }
        },
        elevation = 0.dp,
        backgroundColor = Color.Transparent
    )
}

@Composable
fun FABContent(onTap: () -> Unit) {
    FloatingActionButton(
        onClick = { onTap() },
        shape = RoundedCornerShape(50.dp),
        backgroundColor = Color(0xFF92CBDF)
    )
    {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add a book",
            tint = MaterialTheme.colors.primary
        )
    }

}

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
            .height(260.dp)
            .width(202.dp)
            .padding(20.dp)
            .clickable { onPressDetials.invoke(book.id.toString()) },
        elevation = 10.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(29.dp)
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
                        .height(100.dp)
                        .width(100.dp)
                        .padding(4.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier.padding(top = 25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = "Fav icon",
                        modifier = Modifier.padding(bottom = 2.dp)
                    )



                    BookRating(score = 3.5)
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

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            CardButtonRounded(radius = 70)
        }
    }


}


@Composable
fun CardButtonRounded(label: String = "button", radius: Int = 30, onClicked: () -> Unit = {}) {
    Surface(
        modifier = Modifier.clip(
            RoundedCornerShape(
                bottomEndPercent = radius, topStartPercent = radius
            )
        ),
        color = Color(0XFF92CBDF)
    ) {
        Column(
            modifier = Modifier
                .width(90.dp)
                .heightIn(40.dp)
                .clickable { onClicked.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, style = TextStyle(Color.White, fontSize = 16.sp))
        }
    }
}