package com.tufelmalik.lizzatresturentlite.ui


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.util.Util
import com.tufelmalik.lizzatresturentlite.classes.AppModule.provideCurrentUserID
import com.tufelmalik.lizzatresturentlite.classes.PreferenceManager
import com.tufelmalik.lizzatresturentlite.classes.Utilities
import com.tufelmalik.lizzatresturentlite.databinding.SplashLayoutBinding
import com.tufelmalik.lizzatresturentlite.ui.admin.activity.AdminActivity
import com.tufelmalik.lizzatresturentlite.ui.auth.LoginActivity
import com.tufelmalik.lizzatresturentlite.ui.cook.CookActivity
import com.tufelmalik.lizzatresturentlite.ui.waiter.WaiterActivity

class SplashScreenActivity : AppCompatActivity() {
    private val binding: SplashLayoutBinding by lazy { SplashLayoutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar!!.hide()


        Handler(Looper.getMainLooper()).postDelayed({
//            if (provideCurrentUserID() != null && PreferenceManager.getUserRole(this@SplashScreenActivity) != null) {
//                val userRole = PreferenceManager.getUserRole(this@SplashScreenActivity)
//                navigateToRoleSpecificActivity(userRole)
//                Log.d("TUFEL", "\n\n\n..\n$userRole\n\n\n\n ...")
//            } else {
////                 If user is not authenticated, go to LoginActivity
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
//            }
            finish()
        }, 1500)

    }
    private fun navigateToRoleSpecificActivity(userRole: String) {
        when (userRole) {
            "Waiter" -> startActivity(Intent(this, WaiterActivity::class.java))
            "Cook" -> startActivity(Intent(this, CookActivity::class.java))
            "Admin" -> startActivity(Intent(this, AdminActivity::class.java))
            else -> {

            }
        }
    }
}
