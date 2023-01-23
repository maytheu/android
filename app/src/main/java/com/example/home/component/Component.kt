package com.example.home.component


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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