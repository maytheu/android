package com.maytheu.reader.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maytheu.reader.data.Progress
import com.maytheu.reader.model.Book
import com.maytheu.reader.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val fireRepo: FireRepository) : ViewModel() {
    val books: MutableState<Progress<List<Book>, Boolean, Exception>> =
        mutableStateOf(Progress(listOf(), false, Exception("")))

    init {
        viewModelScope.launch {
            books.value.loading = true
            books.value = fireRepo.getAllBookDb()
            if (!books.value.data.isNullOrEmpty()) books.value.loading = false
        }
        Log.d("bookview model", "${books.value}: ")
    }
}