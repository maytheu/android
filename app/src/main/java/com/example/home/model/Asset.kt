package com.example.home.model

data class Asset(
    val assetDesc: String,
    val assetId: String,
    val assetName: String,
    val assetType: String,
    val assetTypeId: String,
    val company: String,
    val consumption: String,
    val contentDesc: String,
    val contentId: String,
    val contentName: String,
    val contentType: String,
    val delVolume: String,
    val diameter: Int,
    val filling: String,
    val height: Int,
    val lastReading: String,
    val location: String,
    val locationId: String,
    val minUsefVolume: String,
    val status: String,
    val statusId: String,
    val totalUsefVolume: String,
    val totalVolume: String
)