package com.example.home.component


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.home.R

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
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://tryeapayshop.s3.us-east-1.amazonaws.com/3a9127ece77d233b6ba1f7f00.jpeg")
                        .transformations(CircleCropTransformation())
                        .build(),
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
fun ImageScreen(height: Dp) { val modifier =
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