package com.tufelmalik.lizzatresturentlite.ui.viewodel.viewmodel_feectory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.tufelmalik.lizzatresturentlite.data.repo.AuthRepository
import com.tufelmalik.lizzatresturentlite.ui.viewodel.AuthViewModel
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(
    private val auth : FirebaseAuth,
    private val repo : AuthRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(auth,repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

