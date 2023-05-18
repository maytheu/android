package com.example.home.services.dao

import androidx.room.*
import com.example.home.model.UserDb
import kotlinx.coroutines.flow.Flow

@Dao
interface SmartHomeDao {
    /**********************User******/
    @Query("SELECT * from smart_home_user")
    fun loadUser(): Flow<List<UserDb>>

    @Query("SELECT * from smart_home_user where user_id =:userId")
    suspend fun getUser(userId: String): UserDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun newUser(user: UserDb)

    @Delete
    suspend fun delUser(user: UserDb)

    @Query("DELETE from smart_home_user")
    suspend fun resetUser()

}