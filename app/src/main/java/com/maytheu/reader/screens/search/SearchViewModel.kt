package com.maytheu.reader.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.maytheu.reader.data.DataResource
import com.maytheu.reader.data.Progress
import com.maytheu.reader.model.Item
import com.maytheu.reader.repository.GoogleBooksRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val bookRepo: GoogleBooksRepo) : ViewModel() {
    val _searchBooks: MutableState<Progress<List<Item>, Boolean, Exception>> =
        mutableStateOf(Progress(null, true, Exception("")))
    var updateSearchList: List<Item> by mutableStateOf(listOf())
    var loading: Boolean by mutableStateOf(true)

    init {
//        searchBooks("Typescript")
        loadBooks()
    }

    private fun loadBooks() {
        updatedSearch("Android")
    }

    fun updatedSearch(search: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (search.isEmpty()) return@launch
            try {
                when (val response = bookRepo.updateSearchTerm(search)) {
                    is DataResource.Success -> {
                        updateSearchList = response.data!!
                        if (updateSearchList.isNotEmpty()) loading = false
                    }

                    is DataResource.Error -> {
                        loading = false
                        Log.d("TAG", "updatedSearch: Error fetching data")
                    }
                    else -> {
                        loading = false
                    }
                }
            } catch (e: Exception) {
                loading = false
                Log.d("TAG", "updatedSearch: ${e.message}")
            }
        }
    }

    fun searchBooks(search: String) {
        viewModelScope.launch {
            if (search.isEmpty()) return@launch
            _searchBooks.value.loading = true
            _searchBooks.value = bookRepo.searchBook(search)
//            Log.d("TAG", "searchBooks: ${_searchBooks.value.data.toString()}")
            if (_searchBooks.value.data.toString().isNotEmpty()) _searchBooks.value.loading = false
        }
    }
}