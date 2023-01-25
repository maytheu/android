package com.maytheu.note.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maytheu.note.R
import com.maytheu.note.component.Input
import com.maytheu.note.component.NoteButton

@Preview(showBackground = true)
@Composable
fun NoteScreen() {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.padding(5.dp)) {
        TopAppBar(title = { Text(text = stringResource(R.string.app_name)) }, actions = {
            Icon(
                imageVector = Icons.Rounded.Notifications,
                contentDescription = "placeholder"
            )
        }, backgroundColor = Color(0XFFDADFE3))

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Input(
                text = title,
                modifier = Modifier.padding(vertical = 9.dp),
                label = "Title",
                onTextChange = {
                    if (it.any { char ->
                            char.isLetter() || char.isWhitespace()
                        }) title = it
                })

            Input(
                text = description,
                label = "Add a note",
                modifier = Modifier.padding(vertical = 9.dp), onTextChange = {
                    if (it.any { char ->
                            char.isLetter() || char.isWhitespace()
                        }) description = it
                })

            NoteButton(text = "Save", onClick = { /*TODO*/ })
        }
    }
}