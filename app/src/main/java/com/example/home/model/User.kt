package com.example.home.model

data class User(
    val companyName: String,
    val companyTypeName: String,
    val countryId: String,
    val creditBalance: String,
    val firstName: String,
    val lastName: String,
    val logo: String,
    val numActiveDevice: Any,
    val numCorporate: Any,
    val numInactiveDevice: Any,
    val numIndividual: Any,
    val numPartner: Any,
    val overdraftLimit: Double,
    val roleId: String,
    val roleName: String,
    val userCompanyId: String,
    val userId: String
)