package com.example.home.component


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.home.R
import com.example.home.screen.AssetCard

//reusable component composable
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueOfFieldState: MutableState<String>,
    label: String,
    inputEnabled: Boolean = true,
    isMultipleLine: Boolean = false,
    trailingIcon: @Composable () -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
//show this field
    TextField(
        modifier = modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
        value = valueOfFieldState.value,
        onValueChange = { valueOfFieldState.value = it },
        label = { Text(text = label) },
        singleLine = !isMultipleLine,
        trailingIcon = trailingIcon,
        textStyle = TextStyle(fontSize = 20.sp, color = MaterialTheme.colors.onBackground),
        enabled = inputEnabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
    )
}

@Composable
fun ButtonField(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E2AE3)),
    action: () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    Button(
        onClick = action, enabled = enabled, modifier = modifier,
    ) {
        content()
    }
}

@Composable
fun Layout(
    headerText: String,
    back: Boolean = true,
    navController: NavController,
    imageUrl: String,
    content: @Composable () -> Unit,
) {
    Scaffold(topBar = {
        TopAppBar(elevation = 5.dp) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (back) Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go back",
                    modifier = Modifier.clickable { navController.popBackStack() })

                Spacer(modifier = Modifier.width(10.dp))

                AsyncImage(
                    modifier = Modifier.padding(vertical = 10.dp),
                    model = ImageRequest.Builder(LocalContext.current).data(imageUrl)
                        .transformations(CircleCropTransformation()).build(),
                    contentDescription = "profile pic"
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(text = headerText)
            }
        }
    }) {
        content()
    }
}

@Composable
fun ImageScreen(height: Dp) {
    val modifier =
        if (height == 0.0.dp) Modifier.fillMaxSize() else Modifier
            .fillMaxWidth()
            .height(height)
    Surface(
        modifier = modifier, color = Color(0xFF1E2AE3)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(80.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo"
            )
            Text(
                text = "Parrot Home",
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFFFFF)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Expandable(
    expanded: Boolean = false,
    title: String = "title",
    content: @Composable () -> Unit,
) {
    var expandedState by remember { mutableStateOf(expanded) }
    val rotationState by animateFloatAsState(targetValue = if (expandedState) 180f else 0f)
    Card(modifier = Modifier
        .fillMaxWidth()
        .animateContentSize(
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        ), shape = RoundedCornerShape(4.dp), onClick = { expandedState = !expandedState }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    modifier = Modifier.weight(6f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    onClick = { expandedState = !expandedState },
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(1f)
                        .rotate(rotationState)
                ) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "open")
                }
            }
            if (expandedState) {
                content()
            }

        }
    }
}
