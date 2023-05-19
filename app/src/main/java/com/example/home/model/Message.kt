package com.example.home.model

data class Message(
    val deviceLog: List<DeviceLog>,
    val seqNumber: Int,
    val time: String
)