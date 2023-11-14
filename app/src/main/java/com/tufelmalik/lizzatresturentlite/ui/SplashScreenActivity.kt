package com.tufelmalik.lizzatresturentlite.ui


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tufelmalik.lizzatresturentlite.classes.AppModule
import com.tufelmalik.lizzatresturentlite.classes.AppModule.provideCurrentUserID
import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.classes.Utilities
import com.tufelmalik.lizzatresturentlite.data.Users
import com.tufelmalik.lizzatresturentlite.databinding.SplashLayoutBinding
import com.tufelmalik.lizzatresturentlite.ui.admin.activity.AdminActivity
import com.tufelmalik.lizzatresturentlite.ui.auth.LoginActivity
import com.tufelmalik.lizzatresturentlite.ui.cook.CookActivity
import com.tufelmalik.lizzatresturentlite.ui.viewodel.AuthViewModel
import com.tufelmalik.lizzatresturentlite.ui.viewodel.viewmodel_feectory.AuthViewModelFactory
import com.tufelmalik.lizzatresturentlite.ui.waiter.WaiterActivity

class SplashScreenActivity : AppCompatActivity() {
    private val TAG = "SPLASHACTIVITY"
    private val binding: SplashLayoutBinding by lazy { SplashLayoutBinding.inflate(layoutInflater) }
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar!!.hide()

        initViewModel()
        if(provideCurrentUserID() != null){
            viewModel.usersList.observe(this@SplashScreenActivity) { userListState ->
                when (userListState) {
                    is MyResult.Success -> {
                        val usersList = userListState.data
                        checkUserRoleAndStartActivity(usersList)
                    }

                    is MyResult.Error -> Utilities.showToast(
                        this@SplashScreenActivity,
                        "Failed to retrieve user list"
                    )
                    else -> {}
                }
            }
        }else{
            startActivityNow(LoginActivity::class.java)
        }






    }
    private fun initViewModel() {
        val authRepository = AppModule.provideAuthRepository()
        val viewModelFactory =
            AuthViewModelFactory(AppModule.provideFirebaseAuth(), authRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
        viewModel.getAllUsersList()
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
                    }
                }
            }
        }
        Log.d(TAG, "Users list : \n\n .. $usersList \n\n..\n\n")
    }

    private fun startActivityNow(activityClass: Class<*>) {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashScreenActivity, activityClass))
            finish()
        }, 1500)


    }



}
