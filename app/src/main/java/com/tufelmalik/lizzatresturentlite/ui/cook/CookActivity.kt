package com.tufelmalik.lizzatresturentlite.ui.cook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tufelmalik.lizzatresturentlite.R
import com.tufelmalik.lizzatresturentlite.databinding.ActivityCookBinding

class CookActivity : AppCompatActivity() {
    private val binding : ActivityCookBinding by lazy { ActivityCookBinding.inflate(layoutInflater) }
    private var key : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        key = intent.getStringExtra("key")




    }


    override fun onBackPressed() {
        if(!key.equals("admin")){
            super.onBackPressed()
            finish()
            finishAffinity()
        }else{
            super.onBackPressed()
        }
    }
}