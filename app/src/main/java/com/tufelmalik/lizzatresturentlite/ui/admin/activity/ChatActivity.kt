package com.tufelmalik.lizzatresturentlite.ui.admin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tufelmalik.lizzatresturentlite.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private val binding: ActivityChatBinding by lazy { ActivityChatBinding.inflate(layoutInflater) }
    private  var key: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        key = intent.getStringExtra("key")
        supportActionBar?.hide()
        setText()

    }

    private fun setText() {
        if (key == "waiter") {
            binding.txt.text = "Chat With Waiter"
        } else if (key == "cook") {
            binding.txt.text = "Chat With Cook"
        } else
            binding.txt.text = "Chat With Cashier"
    }
}
