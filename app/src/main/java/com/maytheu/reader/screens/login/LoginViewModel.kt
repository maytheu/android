package com.maytheu.reader.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    //default state
    val loadingState = MutableStateFlow(LoadingState.IDLE)

    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    val loading: LiveData<Boolean> = _loading

    fun loginUser(email: String, password: String, navigateHome: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navigateHome()
                    } else {
                        Log.d("TAG", "loginUser: Not successfull")
                    }
                }
            } catch (e: Exception) {
                Log.d("TAG", "loginUser: ${e.message}")
            }
        }

    fun signupUser(email: String, password: String) {

    }

}

data class LoadingState(val status: Status, val message: String? = null) {

    companion object {
        val IDLE = LoadingState(Status.IDLE)
        val SUCCESS = LoadingState(Status.SUCCESS)
        val LOADING = LoadingState(Status.LOADING)
        val FAILED = LoadingState(Status.FAILED)
    }

    enum class Status {
        LOADING, SUCCESS, IDLE, FAILED
    }
}
