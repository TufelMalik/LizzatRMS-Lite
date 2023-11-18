package com.tufelmalik.lizzatresturentlite.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tufelmalik.lizzatresturentlite.classes.AppModule
import com.tufelmalik.lizzatresturentlite.classes.FirebaseModule
import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.classes.Utilities
import com.tufelmalik.lizzatresturentlite.data.Users
import com.tufelmalik.lizzatresturentlite.databinding.ActivityStaffBinding
import com.tufelmalik.lizzatresturentlite.ui.admin.adapters.UsersListAdapter
import com.tufelmalik.lizzatresturentlite.ui.viewodel.AuthViewModel
import com.tufelmalik.lizzatresturentlite.ui.viewodel.viewmodel_feectory.AuthViewModelFactory

class StaffActivity : AppCompatActivity() {
    private val binding: ActivityStaffBinding by lazy {
        ActivityStaffBinding.inflate(layoutInflater)
    }
    private var key: String? = null
    private lateinit var userData: List<Users>
    private lateinit var viewModel: AuthViewModel
    private lateinit var adapter: UsersListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        key = intent.getStringExtra("key")
        supportActionBar?.hide()

        userData = emptyList()
        binding.staffProgressBar.isVisible = false
        Log.d("StaffActivity", "OnCreate Called...")
        initViewModel()
        setupRecyclerView()
        observeUserList()
    }


    private fun setupRecyclerView() {
        adapter = UsersListAdapter(this@StaffActivity)
        binding.staffRecyclerView.layoutManager = LinearLayoutManager(this@StaffActivity)
        binding.staffRecyclerView.adapter = adapter
        Log.d("StaffActivity", "Adapter Called...")
    }

    private fun initViewModel() {
        val authRepository = AppModule.provideAuthRepository()
        val viewModelFactory =
            AuthViewModelFactory(FirebaseModule.provideFirebaseAuth(), authRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
        viewModel.getAllUsersList()
        Log.d("StaffActivity", "ViewModel Called...")

    }


    private fun observeUserList() {
        viewModel.usersList.observe(this@StaffActivity) { userListState ->
            when (userListState) {
                is MyResult.Loading -> {
                    binding.staffRecyclerView.isVisible = true
                    Log.d("StaffActivity","Observe Loading Called...")
                }

                is MyResult.Success -> {
                    userData = userListState.data
                    adapter.updateStaffList(userData)
                    Log.d("StaffActivity","Observe Success Called...")
                    Log.d("StaffActivity", "My data is : $userData")
                    binding.staffRecyclerView.isVisible = false
                }

                is MyResult.Error -> {
                    binding.staffRecyclerView.isVisible = false
                    Log.d("StaffActivity","Observe Error Called...")
                    Utilities.showToast(
                        this@StaffActivity,
                        "Failed to retrieve user list"
                    )
                }

                else -> {
                    Log.d("StaffActivity","Observe Else part Called...")
                }
            }
        }
    }
}