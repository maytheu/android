package com.example.home.services

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.home.model.UserDb
import com.example.home.services.dao.SmartHomeDao

@Database(entities = [UserDb::class], version = 3, exportSchema = false)
abstract class SmartHomeDatabase : RoomDatabase() {
    abstract fun smartHomeDao(): SmartHomeDao
}