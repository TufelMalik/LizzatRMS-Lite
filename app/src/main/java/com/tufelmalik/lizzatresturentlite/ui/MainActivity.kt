package com.tufelmalik.lizzatresturentlite.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tufelmalik.lizzatresturentlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)




    }
}