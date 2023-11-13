package com.tufelmalik.lizzatresturentlite.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tufelmalik.lizzatresturentlite.databinding.ActivityEditFoodBinding

class EditFoodActivity : AppCompatActivity() {
    private val binding: ActivityEditFoodBinding by lazy {
        ActivityEditFoodBinding.inflate(layoutInflater)
    }
    private var key : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar!!.hide()
        key = intent.getStringExtra("key")





    }
}