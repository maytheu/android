package com.example.home.model

data class Floor(
    val devices: List<Device>,
    val floorName: String,
    val floorNumber: Int,
    val floorPlanId: String
)