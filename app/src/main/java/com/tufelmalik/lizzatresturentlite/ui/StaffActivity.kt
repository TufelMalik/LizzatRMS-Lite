package com.tufelmalik.lizzatresturentlite.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tufelmalik.lizzatresturentlite.R
import com.tufelmalik.lizzatresturentlite.databinding.ActivityEditFoodBinding
import com.tufelmalik.lizzatresturentlite.databinding.ActivityStaffBinding

class StaffActivity : AppCompatActivity() {
    private val binding: ActivityStaffBinding by lazy {
        ActivityStaffBinding.inflate(layoutInflater)
    }
    private var key: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        key = intent.getStringExtra("key")


    }
}