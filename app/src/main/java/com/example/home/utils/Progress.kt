package com.example.home.utils

data class Progress<T, Boolean, E : Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null,
)