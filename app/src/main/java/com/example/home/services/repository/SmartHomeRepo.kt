package com.example.home.services.repository

import com.example.home.model.UserDb
import com.example.home.services.dao.SmartHomeDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SmartHomeRepo @Inject constructor(private val dao: SmartHomeDao) {
    fun loadUser(): Flow<List<UserDb>> = dao.loadUser()

    suspend fun getUser(userId: String): UserDb = dao.getUser(userId)

    suspend fun newUser(user: UserDb) = dao.newUser(user)

    suspend fun logout() = dao.resetUser()
}