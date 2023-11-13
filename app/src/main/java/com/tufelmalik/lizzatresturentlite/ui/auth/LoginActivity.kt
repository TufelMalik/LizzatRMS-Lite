package com.tufelmalik.lizzatresturentlite.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tufelmalik.lizzatresturentlite.R
import com.tufelmalik.lizzatresturentlite.classes.AppModule.provideAuthRepository
import com.tufelmalik.lizzatresturentlite.classes.AppModule.provideCurrentUserID
import com.tufelmalik.lizzatresturentlite.classes.AppModule.provideFirebaseAuth
import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.classes.Utilities.showBottomToast
import com.tufelmalik.lizzatresturentlite.classes.Utilities.showProgressDialog
import com.tufelmalik.lizzatresturentlite.classes.Utilities.showToast
import com.tufelmalik.lizzatresturentlite.databinding.ActivityLoginActivityBinding
import com.tufelmalik.lizzatresturentlite.ui.admin.activity.AdminActivity
import com.tufelmalik.lizzatresturentlite.ui.viewodel.AuthViewModel
import com.tufelmalik.lizzatresturentlite.ui.viewodel.viewmodel_feectory.AuthViewModelFactory
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    private val binding: ActivityLoginActivityBinding by lazy {
        ActivityLoginActivityBinding.inflate(layoutInflater)
    }
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


        observer()
        binding.btnLogin.setOnClickListener {
            userEmail = binding.etEmailLogin.text.toString()
            userPassword = binding.etPassLogin.text.toString()
            if (validateInput()) {
                viewModel.loginUser(userEmail, userPassword)
            }
        }

        binding.txtGotoReg.setOnClickListener { startActivity(Intent(this,CreateUserActivity::class.java)) }

    }

    private fun observer() {
        viewModel.loginResult.observe(this@LoginActivity, Observer { state->

            when(state){
                is MyResult.Loading -> {
                    binding.btnLogin.setText("")
                    showProgressDialog(this@LoginActivity,"Pleas wait...","Login",true)
                }
                is MyResult.Error -> {
                    binding.btnLogin.setText("Login Failed")
                    showProgressDialog(this@LoginActivity, status = false)
                    showToast(this@LoginActivity, state.errorMsg.toString())
                }
                is MyResult.Success -> {
                    binding.btnLogin.setText("Login Done")
                    showProgressDialog(this@LoginActivity, status = false)
                    showToast(this@LoginActivity,state.data)
                    startActivity(Intent(this, AdminActivity::class.java))
                    showBottomToast(binding.loginActivityLayout,"This is bottom sncaker")

                }
                else -> {}
            }
        })

    }

    private fun validateInput(): Boolean {
        var isValid = true
        if (binding.etEmailLogin.text.isNullOrEmpty() && binding.etPassLogin.text.isNullOrEmpty()) {
            isValid = false
            showToast(this@LoginActivity, getString(R.string.enter_email)+getString(R.string.enter_password))
        } else {
            if (binding.etPassLogin.text.toString().length < 8) {
                isValid = false
                showToast(this@LoginActivity, getString(R.string.invalid_email))
            }
        }
        return isValid
    }

    override fun onStart() {
        super.onStart()
        if (provideCurrentUserID() != null) {
            startActivity(Intent(this@LoginActivity, AdminActivity::class.java))
        }
    }

}