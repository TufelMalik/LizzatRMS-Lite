package com.tufelmalik.lizzatresturentlite.data.repo.interfaces

import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.data.Users

interface AuthRepoInterface {

    suspend fun registerUser(email : String ,password : String,user : Users, result : (MyResult<String>)-> Unit)
    suspend fun loginUser(userEmail : String , userPassword : String, result : (MyResult<String>)-> Unit)
    fun saveUserDataInFirebase(user : Users, result : (MyResult<String>)-> Unit)
    suspend fun logoutUser(result : () -> Unit)

}