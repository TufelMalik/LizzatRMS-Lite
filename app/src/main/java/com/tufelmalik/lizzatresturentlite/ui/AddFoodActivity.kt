package com.tufelmalik.lizzatresturentlite.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tufelmalik.lizzatresturentlite.databinding.ActivityAddFoodBinding


class AddFoodActivity : AppCompatActivity() {
    private val binding: ActivityAddFoodBinding by lazy {
        ActivityAddFoodBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)





    }
}