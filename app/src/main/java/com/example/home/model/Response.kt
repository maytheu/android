package com.example.home.model

data class Response<T>(val response: T? = null, val statusCode: String)
