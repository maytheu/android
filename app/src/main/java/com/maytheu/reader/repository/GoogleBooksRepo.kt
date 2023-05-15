package com.maytheu.reader.repository

import com.maytheu.reader.data.DataResource
import com.maytheu.reader.data.Progress
import com.maytheu.reader.model.Item
import com.maytheu.reader.network.GoogleBooksApi
import javax.inject.Inject

class GoogleBooksRepo @Inject constructor(private val bookApi: GoogleBooksApi) {
    private val _apiBooksState = Progress<List<Item>, Boolean, Exception>()
    private val _apiBookState = Progress<Item, Boolean, Exception>()

    suspend fun searchBook(searchTerm: String): Progress<List<Item>, Boolean, Exception> {
        try {
            _apiBooksState.loading = true
            _apiBooksState.data = bookApi.getAllBooks(searchTerm).items
            if (_apiBooksState.data!!.isNotEmpty()) _apiBooksState.loading = false
        } catch (e: Exception) {
            _apiBooksState.e = e
        }
        return _apiBooksState
    }

    suspend fun bookInfo(bookId: String): Progress<Item, Boolean, Exception> {
        try {
            _apiBookState.loading = true
            _apiBookState.data = bookApi.getBookInfo(bookId)
            if (_apiBookState.data.toString().isNotEmpty()) _apiBookState.loading = false
        } catch (e: Exception) {
            _apiBookState.e = e
        }
        return _apiBookState
    }

    suspend fun updateSearchTerm(searchTerm: String): DataResource<List<Item>> {
        return try {
            DataResource.Loading(data = true)
            val bookList = bookApi.getAllBooks(searchTerm).items
            if (bookList.isEmpty()) DataResource.Loading(false)
            DataResource.Success(bookList)
        } catch (e: Exception) {
            DataResource.Error(message = e.message.toString())
        }
    }

    suspend fun updateBookInfo(bookId: String): DataResource<Item> {
        val resp = try {
            DataResource.Loading(data = true)
            bookApi.getBookInfo((bookId))
        } catch (e: Exception) {
            return DataResource.Error(message = e.message.toString())
        }
        DataResource.Loading(false)
        return DataResource.Success(data = resp)
    }
}