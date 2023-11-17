package com.tufelmalik.lizzatresturentlite.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tufelmalik.lizzatresturentlite.classes.AppModule
import com.tufelmalik.lizzatresturentlite.classes.FirebaseModule
import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.classes.Utilities
import com.tufelmalik.lizzatresturentlite.databinding.ActivityStaffBinding
import com.tufelmalik.lizzatresturentlite.ui.admin.adapters.UsersListAdapter
import com.tufelmalik.lizzatresturentlite.ui.viewodel.AuthViewModel
import com.tufelmalik.lizzatresturentlite.ui.viewodel.viewmodel_feectory.AuthViewModelFactory

class StaffActivity : AppCompatActivity() {
    private val binding: ActivityStaffBinding by lazy {
        ActivityStaffBinding.inflate(layoutInflater)
    }
    private var key: String? = null
    private lateinit var viewModel: AuthViewModel
    private val adapter = UsersListAdapter(this@StaffActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        key = intent.getStringExtra("key")
        supportActionBar?.hide()

        initViewModel()
        observeUserList()
        setLayoutAdapter()
    }

    private fun setLayoutAdapter() {
        binding.apply {
            staffRecyclerView.layoutManager = LinearLayoutManager(this@StaffActivity)
            staffRecyclerView.adapter = adapter
        }
    }

    private fun initViewModel() {
        val authRepository = AppModule.provideAuthRepository()
        val viewModelFactory =
            AuthViewModelFactory(FirebaseModule.provideFirebaseAuth(), authRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
        viewModel.getAllUsersList()
    }


    private fun observeUserList() {
        viewModel.usersList.observe(this@StaffActivity) { userListState ->
            when (userListState) {
                is MyResult.Success -> {
                    adapter.updateUserList(userListState.data)
                }

                is MyResult.Error -> Utilities.showToast(
                    this@StaffActivity,
                    "Failed to retrieve user list"
                )

                else -> {}
            }
        }

    }

}