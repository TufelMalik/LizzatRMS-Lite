package com.tufelmalik.lizzatresturentlite.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tufelmalik.lizzatresturentlite.R
import com.tufelmalik.lizzatresturentlite.classes.AppModule.provideAuthRepository
import com.tufelmalik.lizzatresturentlite.classes.AppModule.provideCurrentUserID
import com.tufelmalik.lizzatresturentlite.classes.AppModule.provideFirebaseAuth
import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.classes.Utilities.showToast
import com.tufelmalik.lizzatresturentlite.databinding.ActivityLoginActivityBinding
import com.tufelmalik.lizzatresturentlite.ui.admin.activity.AdminActivity
import com.tufelmalik.lizzatresturentlite.ui.cook.CookActivity
import com.tufelmalik.lizzatresturentlite.ui.viewodel.AuthViewModel
import com.tufelmalik.lizzatresturentlite.ui.viewodel.viewmodel_feectory.AuthViewModelFactory
import com.tufelmalik.lizzatresturentlite.ui.waiter.WaiterActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    private val binding: ActivityLoginActivityBinding by lazy {
        ActivityLoginActivityBinding.inflate(layoutInflater)
    }
    private var userRole : String? = null
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val authRepository = provideAuthRepository()
        val viewModelFactory = AuthViewModelFactory(provideFirebaseAuth(), authRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AuthViewModel::class.java)
        supportActionBar!!.hide()
        viewModel.getAllUsersList()


        observer()
        binding.btnLogin.setOnClickListener {
            userEmail = binding.etEmailLogin.text.toString()
            userPassword = binding.etPassLogin.text.toString()
            if (validateInput()) {
                viewModel.loginUser(userEmail, userPassword)
            }else{
                showToast(this@LoginActivity,"Email & password required!")
            }
        }

        binding.txtGotoReg.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CreateUserActivity::class.java
                )
            )
        }

    }



    private fun checkUserIsAuthed() {
        viewModel.usersList.observe(this@LoginActivity) { state ->
            when (state) {
                is MyResult.Error -> showToast(this@LoginActivity, "Something went wrong!!")

                MyResult.Loading -> TODO() // Handle Loading if needed

                is MyResult.Success -> {
                    val usersList = state.data
                    Log.d(TAG, "Users list : $usersList")

                    if (provideCurrentUserID() != null) {
                        for (user in usersList) {
                            if (user.userId == provideCurrentUserID()) {
                                when (user.userRole) {
                                    "Waiter" -> startActivityNow(WaiterActivity())
                                    "Cook" -> startActivityNow(CookActivity())
                                    "Admin" -> startActivityNow(AdminActivity())
                                    else -> {
                                        // Handle other roles or scenarios if needed
                                    }
                                }
                                return@observe // Break out of the loop once a match is found
                            }
                        }
                    }
                }

                is MyResult.Unspecified -> TODO()
            }
        }
    }


    private fun observer() {
        viewModel.loginResult.observe(this@LoginActivity) { state ->
            when (state) {
                is MyResult.Error -> showToast(this@LoginActivity, "Something went wrong!!")
                MyResult.Loading -> TODO() // Handle Loading if needed
                is MyResult.Success -> {
                    startActivity(Intent(this@LoginActivity,AdminActivity::class.java))
                }

                is MyResult.Unspecified -> TODO()
            }
        }
    }


    private fun validateInput(): Boolean {
        var isValid = true
        if (binding.etEmailLogin.text.isNullOrEmpty() && binding.etPassLogin.text.isNullOrEmpty()) {
            isValid = false
            showToast(
                this@LoginActivity,
                getString(R.string.enter_email) + getString(R.string.enter_password)
            )
        } else {
            if (binding.etPassLogin.text.toString().length < 8) {
                isValid = false
                showToast(this@LoginActivity, getString(R.string.invalid_email))
            }
        }
        return isValid
    }




    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
        finish()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllUsersList()
        checkUserIsAuthed()
    }

    private fun startActivityNow(activity: Activity) {
        startActivity(Intent(this@LoginActivity, activity::class.java))
    }

}