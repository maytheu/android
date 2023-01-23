package com.example.home.component


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation

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
    visualTransformation: VisualTransformation = VisualTransformation.None
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
        keyboardActions = onAction, visualTransformation = visualTransformation
    )
}

@Composable
fun ButtonField(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    action: () -> Unit = {},
    content: @Composable () -> Unit = {}
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
    content: @Composable () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(elevation = 5.dp) {
            Row(horizontalArrangement = Arrangement.Start) {
                if (back) Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go back",
                    modifier = Modifier.clickable { navController.popBackStack() })

                Spacer(modifier = Modifier.width(10.dp))

//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data("https://tryeapayshop.s3.us-east-1.amazonaws.com/2e6ba38ab31d7abf57368e801")
//                        .transformations(CircleCropTransformation())
//                        .build(),
//                    contentDescription = "profile pic"
//                )

                Spacer(modifier = Modifier.width(15.dp))

                Text(text = headerText)
            }
        }
    }) {
        content()
    }
}