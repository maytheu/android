package com.example.home.model

data class LastStatus(
    val deviceId: String,
    val messages: List<Message>
)