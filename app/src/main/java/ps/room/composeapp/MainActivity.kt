package ps.room.composeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ps.room.composeapp.ui.theme.ComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CreateBizCard()
                }
            }
        }
    }
}

@Composable
fun CreateBizCard() {
    val buttonClickedState = remember {
        mutableStateOf(false)
    }
    //whole component
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        //card layut
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(390.dp)
                .padding(15.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(CornerSize(20.dp))
        ) {
            //avatar layout container
            Column(
                modifier = Modifier.height(400.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileImage()
                Divider(modifier = Modifier.padding(vertical = 5.dp))
                createInfo()
                Button(onClick = {
                    buttonClickedState.value = !buttonClickedState.value
                }) {
                    Text(text = "Portfolio", style = MaterialTheme.typography.button)
                }

                if (buttonClickedState.value) {
                    Content()
                } else {
                    Box {}
                }
            }
        }
    }
}

//@Preview
@Composable
private fun Content() {
    //prpoject container
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp),
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            border = BorderStroke(2.dp, Color.LightGray)
        ) {
            Portfolio(data = listOf("Project 1", "Project 2", "Projet 3"))
        }
    }
}

@Composable
fun Portfolio(data: List<String>) {
    LazyColumn {
        items(data) { item ->
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(), shape = RectangleShape, elevation = 6.dp
            ) {

                Row(
                    Modifier
                        .padding(10.dp)
                        .background(MaterialTheme.colors.surface)
                ) {
                    ProfileImage(modifier = Modifier.size(100.dp))
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(text = item, fontWeight = FontWeight.Bold)
                        Text(
                            text = "Building Android apps with compose",
                            style = MaterialTheme.typography.body2
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun createInfo() {
    Column() {
        Text(
            text = "Adetunji M.",
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.primaryVariant
        )

        Text(text = "Fullstack Software Developer", modifier = Modifier.padding(3.dp))

        Text(
            text = "Mobile | React | Angular | NodeJs",
            modifier = Modifier.padding(3.dp),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
private fun ProfileImage(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .size(200.dp)
            .padding(10.dp),
        border = BorderStroke(.5.dp, Color.LightGray),
        shape = CircleShape,
        elevation = 4.dp,
        color = MaterialTheme.colors.onSurface.copy(.5f)
    ) {
        //image
        Image(
            painter = painterResource(id = R.drawable.empty_img),
            contentDescription = "profile image",
            modifier = modifier.size(150.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAppTheme {
        CreateBizCard()
    }
}