package com.maytheu.reader.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.maytheu.reader.data.Progress
import com.maytheu.reader.model.Book
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(private val query: Query) {
    suspend fun getAllBookDb(): Progress<List<Book>, Boolean, Exception> {
        val data = Progress<List<Book>, Boolean, Exception>()
        try {
            data.loading = true
            data.data = query.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(Book::class.java)!!
            }
            if (!data.data.isNullOrEmpty()) data.loading = false
        } catch (e: FirebaseFirestoreException) {
            data.e = e
        }
        return data
    }
}