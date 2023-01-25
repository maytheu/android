package com.maytheu.note.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maytheu.note.R
import com.maytheu.note.component.Input

@Preview(showBackground = true)
@Composable
fun NoteScreen() {
    val title by remember {
        mutableStateOf("")
    }
    val description by remember {
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
           Input(text = title, label = "Title", onTextChange = {})

            Input(text = description, label = "Add a note", onTextChange = {})
        }
    }
}