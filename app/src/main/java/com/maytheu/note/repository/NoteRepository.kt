package com.maytheu.note.repository

import com.maytheu.note.data.NoteDatabaseDao
import com.maytheu.note.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

//will interact with the DAO
class NoteRepository @Inject constructor(private val noteDatabaseDao: NoteDatabaseDao) {
    suspend fun addNote(note: Note) = noteDatabaseDao.insert(note)

    suspend fun updateNote(note: Note) = noteDatabaseDao.update(note)

    fun allNotes():Flow<List<Note>> = noteDatabaseDao.getNotes().flowOn(Dispatchers.IO).conflate()

    suspend fun getNote(id: String) = noteDatabaseDao.getNote(id)

    suspend fun deleteAllNotes() = noteDatabaseDao.deleteAll()

    suspend fun deleteNote(note: Note) = noteDatabaseDao.deleteNote(note)
}