package com.maytheu.note.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.maytheu.note.data.NoteDataSource
import com.maytheu.note.model.Note

@RequiresApi(Build.VERSION_CODES.O)
class NoteViewModel : ViewModel() {
    var noteList = mutableStateListOf<Note>()

    init {
        noteList.addAll(NoteDataSource().loadNotes())
    }

    fun addNote(note: Note) {
        noteList.add(note)
    }

    fun removeNote(note: Note) {
        noteList.remove(note)
    }

    fun editNote(note: Note): Note {
        Log.d("TAG", "editNote: $note")
        return note
    }

    fun getAllNote():List<Note>{
        return  noteList
    }
}