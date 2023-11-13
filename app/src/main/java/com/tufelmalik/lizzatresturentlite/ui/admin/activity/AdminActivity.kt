package com.tufelmalik.lizzatresturentlite.ui.admin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tufelmalik.lizzatresturentlite.R
import com.tufelmalik.lizzatresturentlite.databinding.ActivityAdminBinding
import androidx.navigation.ui.setupWithNavController


class AdminActivity : AppCompatActivity() {
    private val binding: ActivityAdminBinding by lazy { ActivityAdminBinding.inflate(layoutInflater) }
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val actionBar = supportActionBar!!
        actionBar.hide()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.admin_frag_container) as NavHostFragment
        navController = navHostFragment.navController
        binding.adminBottomNavigation.setupWithNavController(navController)



    }
}