package com.maytheu.reader.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.maytheu.reader.data.Progress
import com.maytheu.reader.model.Item
import com.maytheu.reader.repository.GoogleBooksRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val bookRepo: GoogleBooksRepo) : ViewModel() {
    private val _searchBooks: MutableState<Progress<List<Item>, Boolean, Exception>> =
        mutableStateOf(Progress(null, true, Exception("")))

    init {
        searchBooks("Typescript")
    }

     fun searchBooks(search: String) {
        viewModelScope.launch {
            if (search.isEmpty()) return@launch
            _searchBooks.value.loading = true
            _searchBooks.value = bookRepo.searchBook(search)
            Log.d("TAG", "searchBooks: ${_searchBooks.value.data.toString()}")
            if (_searchBooks.value.data.toString().isNotEmpty()) _searchBooks.value.loading = false
        }
    }
}