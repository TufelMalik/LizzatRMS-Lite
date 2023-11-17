package com.tufelmalik.lizzatresturentlite.ui.auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tufelmalik.lizzatresturentlite.R
import com.tufelmalik.lizzatresturentlite.classes.AppModule.provideAuthRepository
import com.tufelmalik.lizzatresturentlite.classes.FirebaseModule.provideCurrentUserID
import com.tufelmalik.lizzatresturentlite.classes.FirebaseModule.provideFirebaseAuth
import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.classes.Utilities.showToast
import com.tufelmalik.lizzatresturentlite.data.Users
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
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar!!.hide()


        initViewModel()
        observeLoginResult()

        binding.btnLogin.setOnClickListener {
            userEmail = binding.etEmailLogin.text.toString()
            userPassword = binding.etPassLogin.text.toString()
            if (validateInput()) {
                viewModel.loginUser(userEmail, userPassword)
            } else {
                showToast(this@LoginActivity, "Email & password required!")
            }
        }

        binding.txtGotoReg.setOnClickListener {
            startActivity(Intent(this, CreateUserActivity::class.java))
        }
    }

    private fun initViewModel() {
        val authRepository = provideAuthRepository()
        val viewModelFactory = AuthViewModelFactory(provideFirebaseAuth(), authRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
        viewModel.getAllUsersList()
    }

    private fun observeLoginResult() {
        viewModel.loginResult.observe(this@LoginActivity) { state ->
            when (state) {
                is MyResult.Error -> showToast(this@LoginActivity, "Something went wrong!!")
                is MyResult.Success -> {
                    val dialog = ProgressDialog(this@LoginActivity)
                    dialog.setMessage("Pleas wait...")
                    dialog.setTitle("Login")
                    dialog.show()
                    observeUserList()
                    dialog.dismiss()
                    dialog.hide()
                }

                else -> {}
            }
        }
    }

    private fun observeUserList() {
        viewModel.usersList.observe(this@LoginActivity) { userListState ->
            when (userListState) {
                is MyResult.Success -> {
                    val usersList = userListState.data
                    checkUserRoleAndStartActivity(usersList)
                }

                is MyResult.Error -> showToast(this@LoginActivity, "Failed to retrieve user list")
                else -> {}
            }
        }

    }


    private fun checkUserRoleAndStartActivity(usersList: List<Users>) {
        val currentUserID = provideCurrentUserID()
        if (currentUserID != null) {
            for (user in usersList) {
                if (user.userId == currentUserID) {
                    when (user.userRole) {
                        "Waiter" -> startActivityNow(WaiterActivity::class.java)
                        "Cook" -> startActivityNow(CookActivity::class.java)
                        "Admin" -> startActivityNow(AdminActivity::class.java)
                        else -> {}
                    }
                }
                Log.d(TAG, "Users list : \n\n .. ${usersList} \n\n..\n\n")
            }
        }
    }

    private fun startActivityNow(activityClass: Class<*>) {
        startActivity(Intent(this@LoginActivity, activityClass))
        finish()
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
    }
}
