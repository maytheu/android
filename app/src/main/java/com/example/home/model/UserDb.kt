package com.example.home.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "smart_home_user")
data class UserDb(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "first_name")
    val firstName: String,

//    @ColumnInfo(name = "last_name")
//    val lastName: String?,

    @ColumnInfo(name = "company_name")
    val companyName: String,

    @ColumnInfo(name = "logo")
    val logo: String,

    @ColumnInfo(name = "company_id")
    val userCompanyId: String,
)
