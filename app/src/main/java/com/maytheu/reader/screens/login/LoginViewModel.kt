package com.maytheu.reader.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.maytheu.reader.model.ReaderUser
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

    fun signupUser(email: String, password: String, navigateHome: () -> Unit) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val displayName = task.result.user?.email?.split("@")?.get(0)
                    createUser(displayName)
                    navigateHome()
                } else {
                    Log.d("TAG", "signupUser: Not successfull")
                }
                _loading.value = false
            }
        }
    }

    fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = ReaderUser(
            userId = userId.toString(),
            displayName = displayName.toString(),
            quote = "",
            avatar = "",
            proofession = "Mobile Developer", id = null
        ).firebaseMap()

        FirebaseFirestore.getInstance().collection("users").add(user)
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
