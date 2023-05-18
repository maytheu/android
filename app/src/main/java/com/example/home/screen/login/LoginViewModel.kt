package com.example.home.screen.login

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home.model.LoginData
import com.example.home.model.User
import com.example.home.services.repository.AuthRepo
import com.example.home.utils.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: AuthRepo) : ViewModel() {
    //get user status
    val isUser: MutableState<Progress<Boolean, Boolean, Exception>> =
        mutableStateOf(Progress(false, true, Exception("")))

    fun loginUser(data: LoginData) {
        viewModelScope.launch {
            if (data.userId == "" || data.password == "") return@launch
            isUser.value.loading = true
            val userData = repo.loginUser(data = data)
            if (userData.data == null) {
                //user not authenticated
                isUser.value.loading = false
                isUser.value.data = false
            } else {
                isUser.value.loading = false
                isUser.value.data = true
                //TODO save user data to db
            }
        }
    }

    suspend fun login(data: LoginData): Progress<User, Boolean, Exception> {
        return repo.loginUser(data)
    }

}