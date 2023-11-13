package com.tufelmalik.lizzatresturentlite.ui.viewodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.data.Users
import com.tufelmalik.lizzatresturentlite.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val repo: AuthRepository
) : ViewModel() {

    private val _registerResult = MutableLiveData<MyResult<String>>()
    val registerResult: LiveData<MyResult<String>>
        get() = _registerResult

    private val _loginResult = MutableLiveData<MyResult<String>>()
    val loginResult: LiveData<MyResult<String>>
        get() = _loginResult


    fun loginUser(email: String, pass: String) {
        _loginResult.value = MyResult.Loading
        ;
        viewModelScope.launch {
            repo.loginUser(email, pass) {
                _loginResult.value = it
            }
        }
    }

    fun registerAndSaveUserData(email: String, pass: String, users: Users) {
        _registerResult.value = MyResult.Loading
        viewModelScope.launch {
            repo.registerUser(email, pass, users) {
                _registerResult.value = it
            }
        }
    }

    fun logOut(result: () -> Unit) {
        viewModelScope.launch {
            repo.logoutUser(result)
        }
    }


}
