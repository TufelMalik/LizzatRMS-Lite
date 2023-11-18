package com.tufelmalik.lizzatresturentlite.data.repo

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.tufelmalik.lizzatresturentlite.classes.FirebaseModule.provideFirebaseStorage
import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.classes.Utilities
import com.tufelmalik.lizzatresturentlite.data.Users
import com.tufelmalik.lizzatresturentlite.data.repo.interfaces.AuthRepoInterface
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private var db: FirebaseDatabase
) : AuthRepoInterface {

    private var storage: FirebaseStorage = provideFirebaseStorage()

    override suspend fun registerUser(
        email: String,
        password: String,
        user: Users,
        result: (MyResult<String>) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    user.userId = it.result.user!!.uid
                    saveUserDataInFirebase(user = user) { state ->
                        when (state) {
                            is MyResult.Success -> {
                                result.invoke(
                                    MyResult.Success("SignUp Sucssfully...")
                                )
                            }

                            is MyResult.Error -> {
                                result.invoke(
                                    MyResult.Error(state.errorMsg)
                                )
                            }

                            else -> MyResult.Loading
                        }
                    }
                } else {
                    try {
                        throw it.exception ?: java.lang.Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(MyResult.Error("Authentication failed, Password should be at least 6 characters"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(MyResult.Error("Authentication failed, Invalid email entered"))
                    } catch (e: Exception) {
                        result.invoke(MyResult.Error("Authentication failed, Email already registered."))
                    } catch (e: Exception) {
                        result.invoke(MyResult.Error(e.message))
                    }
                }
            }
            .addOnFailureListener {
                result.invoke(
                    MyResult.Error(
                        it.message
                    )
                )
            }

    }

    override suspend fun loginUser(
        userEmail: String,
        userPassword: String,
        result: (MyResult<String>) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result.invoke(MyResult.Success("Login Successful."))
                } else {
                    result.invoke(
                        MyResult.Error("Somthing went wrong!")
                    )
                }
            }
            .addOnFailureListener {
                result.invoke(
                    MyResult.Error(it.message)
                )
            }


    }

    override fun saveUserDataInFirebase(user: Users, result: (MyResult<String>) -> Unit) {
        val storageRef = storage.reference.child("UsersProfileImages")
        val imgRef = storageRef.child(user.userId.toString())
        val uploadTask = imgRef.putFile(Uri.parse(user.profileUrl))
        user.joiningDate =  System.currentTimeMillis().toString()
        uploadTask.addOnSuccessListener {
            imgRef.downloadUrl.addOnSuccessListener {
                user.profileUrl = it.toString()
                val databaseRef =
                    db.reference.child(Utilities.FirebaseData.USER).child(user.userId.toString())
                databaseRef.setValue(user)
                    .addOnSuccessListener {
                        result.invoke(
                            MyResult.Success("Data inserted succssfully...")
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(
                            MyResult.Error("Failed to insert data in database.")
                        )
                    }
            }
        }
            .addOnFailureListener {
                result.invoke(
                    MyResult.Error(it.message)
                )
            }

    }


    override suspend fun getAllUsersList(result: (MyResult<List<Users>>) -> Unit) {
        val databaseRef =  db.reference.child(Utilities.FirebaseData.USER)
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                result.invoke(
                    MyResult.Loading
                )
                val userList = arrayListOf<Users>()
                for(data in snapshot.children){
                    val users = data.getValue(Users::class.java)
                    if (users != null) {
                        userList.add(users)
                    }
                }
                result.invoke(
                    MyResult.Success(userList)
                )
            }

            override fun onCancelled(error: DatabaseError) {
                result.invoke(
                    MyResult.Error(error.message)
                )
            }
        })
    }


}