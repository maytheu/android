package com.example.home.model

data class Device(
    val attributes: List<Attribute>,
    val clientDeviceCategId: String,
    val companyId: String,
    val companyName: String,
    val deviceCategName: String,
    val deviceId: String,
    val deviceName: String,
    val floorName: String,
    val floorNumber: String,
    val lastSeenMsgId: String,
    val lastseenDate: String,
    val manufDeviceId: String,
    val network: String,
    val networkName: String,
    val notificationDue: String,
    val position: Position,
    val sensorIcon: String,
    val subscrEndDate: String,
    val subscrStartDate: String,
    val utcTimestamp: String
)