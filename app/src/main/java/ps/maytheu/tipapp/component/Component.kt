package ps.maytheu.tipapp.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//reusable component composable
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueOfFieldState: MutableState<String>,
    label: String,
//    leadingIcon:Icon=
    inputEnabled: Boolean,
    isMultipleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
//show this field
    OutlinedTextField(
        modifier = modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
        value = valueOfFieldState.value,
        onValueChange = { valueOfFieldState.value = it },
        label = { Text(text = label) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.AttachMoney,
                contentDescription = "Monet Icon"
            )
        },
        singleLine = !isMultipleLine,
        textStyle = TextStyle(fontSize = 20.sp, color = MaterialTheme.colors.onBackground),
        enabled = inputEnabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction
    )
}