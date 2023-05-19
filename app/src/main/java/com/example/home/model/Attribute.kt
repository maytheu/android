package com.example.home.model

data class Attribute(
    val attribute: String,
    val attributeType: String,
    val attributeValue: String,
    val dataGroup: Any,
    val devNetwkTime: String,
    val netwkSeqNo: Int,
    val notificationDue: String,
    val rawMessageId: String,
    val showInDashbd: String
)