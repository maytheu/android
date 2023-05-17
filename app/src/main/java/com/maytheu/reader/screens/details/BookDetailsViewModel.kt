package com.maytheu.reader.screens.details

import androidx.lifecycle.ViewModel
import com.maytheu.reader.data.DataResource
import com.maytheu.reader.model.Item
import com.maytheu.reader.repository.GoogleBooksRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(private val bookRepo: GoogleBooksRepo) :
    ViewModel() {
    suspend fun getBookInfo(bookId: String): DataResource<Item> {
        return bookRepo.updateBookInfo(bookId)
    }
}