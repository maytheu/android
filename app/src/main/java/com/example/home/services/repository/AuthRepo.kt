package com.example.home.services.repository

import com.example.home.model.LoginData
import com.example.home.model.User
import com.example.home.services.dao.AuthApi
import com.example.home.utils.Progress
import javax.inject.Inject

/*
This connect to the api
 */
class AuthRepo @Inject constructor(private val api: AuthApi) {
    private val _loginUserStatus = Progress<User, Boolean, Exception>()

    suspend fun loginUser(data: LoginData): Progress<User, Boolean, java.lang.Exception> {
        try {
            _loginUserStatus.loading = true
            _loginUserStatus.data = api.login(data).response
            if (_loginUserStatus.data.toString().isNotEmpty()) _loginUserStatus.loading = false
        } catch (e: java.lang.Exception) {
            _loginUserStatus.e = e
        }
        return _loginUserStatus
    }
}