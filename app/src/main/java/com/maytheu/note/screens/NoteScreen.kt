package com.maytheu.note.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maytheu.note.R
import com.maytheu.note.component.Input
import com.maytheu.note.component.NoteButton
import com.maytheu.note.model.Note
import com.maytheu.note.utils.formatDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemove: (Note) -> Unit,
//    onEdit: (Note) -> Unit,
//    note: Note,
) {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val keyController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.padding(5.dp)) {
        TopAppBar(title = { Text(text = stringResource(R.string.app_name)) }, actions = {
            Icon(
                imageVector = Icons.Rounded.Notifications, contentDescription = "placeholder"
            )
        }, backgroundColor = Color(0XFFDADFE3))

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()
        ) {
            Input(text = title,
                modifier = Modifier.padding(vertical = 9.dp),
                label = "Title",
                onTextChange = {
                    if (it.any { char ->
                            char.isLetter() || char.isWhitespace()
                        }) title = it
                })

            Input(text = description,
                label = "Add a note",
                modifier = Modifier.padding(vertical = 9.dp),
                onTextChange = {
                    if (it.any { char ->
                            char.isLetter() || char.isWhitespace()
                        }) description = it
                })

            NoteButton(text = "Save", onClick = {
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    onAddNote(Note(title = title, description = description))
                    title = ""
                    description = ""
                    keyController?.hide()
                    Toast.makeText(context, "Note saved", Toast.LENGTH_SHORT).show()
                }
            })

            Divider(modifier = Modifier.padding(10.dp))

            LazyColumn {
                items(notes) {
                    NoteRow(note = it, onNoteDelete = { onRemove(it) },
//                        onNoteEdit = { onEdit(it) }
                    )
                }
            }
        }
    }
}

//all action implemented in the main form
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteRow(
    note: Note,
    modifier: Modifier = Modifier,
    onNoteDelete: (Note) -> Unit,
    onNoteEdit: (Note) -> Unit={},
) {
    Surface(
        modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(topEnd = 40.dp, bottomStart = 40.dp))
            .fillMaxWidth(), color = Color(0xFFDFE6E8), elevation = 5.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .width(280.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = note.title, style = MaterialTheme.typography.subtitle2)
                Text(text = note.description, style = MaterialTheme.typography.subtitle1)
                Text(
                    text = formatDate(note.entryDate.time) ,style = MaterialTheme.typography.caption
                )
            }

//            Icon(imageVector = Icons.Outlined.Edit,
//                contentDescription = "Edit note",
//                modifier.clickable { onNoteEdit(note) })


            Spacer(modifier = modifier.width(10.dp))

            Icon(imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete note",
                modifier.clickable { onNoteDelete(note) })
        }

    }
}