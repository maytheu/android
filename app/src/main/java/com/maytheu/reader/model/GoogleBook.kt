package com.maytheu.reader.model

data class GoogleBook(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)