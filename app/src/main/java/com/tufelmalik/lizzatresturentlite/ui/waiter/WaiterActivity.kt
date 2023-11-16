package com.tufelmalik.lizzatresturentlite.ui.waiter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tufelmalik.lizzatresturentlite.R
import com.tufelmalik.lizzatresturentlite.databinding.ActivityWaiterBinding

class WaiterActivity : AppCompatActivity() {
    private val binding : ActivityWaiterBinding by lazy { ActivityWaiterBinding.inflate(layoutInflater) }
    private var key : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiter)
        key = intent.getStringExtra("key")
        supportActionBar?.hide()

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