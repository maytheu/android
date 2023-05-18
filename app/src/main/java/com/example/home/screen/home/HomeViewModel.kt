package com.example.home.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home.model.UserDb
import com.example.home.services.repository.SmartHomeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val db: SmartHomeRepo) : ViewModel() {
    private val _user = MutableStateFlow<List<UserDb>>(emptyList())
    val users = _user.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            db.loadUser().distinctUntilChanged().collect { user ->
                if (user.isNotEmpty()) {
                    Log.d("TAG", "$user: ")
                    _user.value = user
                }
            }
        }
    }

    fun saveUserToDb(userDb: UserDb) = viewModelScope.launch { db.newUser(userDb) }

    fun getUser(userId: String) = viewModelScope.launch { db.getUser(userId) }

    fun logout() = viewModelScope.launch { db.logout() }

}