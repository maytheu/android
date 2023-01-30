package com.maytheu.note

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maytheu.note.data.NoteDataSource
import com.maytheu.note.model.Note
import com.maytheu.note.screens.NoteScreen
import com.maytheu.note.ui.theme.NoteTheme
import com.maytheu.note.viewModel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    val noteViewModel: NoteViewModel by viewModels()
                    NoteApp(noteViewModel)

                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteApp(noteViewModel: NoteViewModel = viewModel()) {
    val noteList = noteViewModel.getAllNote()
    var editNote by remember {
        mutableStateOf(Note(title = "", description = ""))
    }

    NoteScreen(notes = noteList, onAddNote = {
        noteViewModel.addNote(it)
    }, onRemove = {
        noteViewModel.removeNote(it)
    }, onEdit = {
        editNote = noteViewModel.editNote(it)
    }, editNote
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NoteTheme {
//        NoteScreen(notes = NoteDataSource().loadNotes(), onAddNote = {}, onRemove = {}, onEdit = {})
    }
}