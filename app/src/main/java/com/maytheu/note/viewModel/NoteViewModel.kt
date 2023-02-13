package com.maytheu.note.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maytheu.note.data.NoteDataSource
import com.maytheu.note.model.Note
import com.maytheu.note.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {
    //    var noteList = mutableStateListOf<Note>()
    private var _noteList = MutableStateFlow<List<Note>>(emptyList())
    var noteList = _noteList.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.allNotes().distinctUntilChanged().collect { listOfNotes ->
                if (listOfNotes.isNullOrEmpty()) {
                    Log.d("Empty", "No data")
                } else {
                    _noteList.value = listOfNotes
                }
            }
        }
//        noteList.addAll(NoteDataSource().loadNotes())
    }

     fun addNote(note: Note) = viewModelScope.launch { noteRepository.addNote(note) }

     fun editNote(note: Note) = viewModelScope.launch { noteRepository.updateNote(note) }

     fun removeNote(note: Note) = viewModelScope.launch { noteRepository.deleteNote(note) }


//    fun removeNote(note: Note) {
//        noteList.remove(note)
//    }
//
//    fun editNote(note: Note): Note {
//        Log.d("TAG", "editNote: $note")
//        return note
//    }
//
//    fun getAllNote(): List<Note> {
//        return noteList
//    }
}