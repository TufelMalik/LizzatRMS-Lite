package com.tufelmalik.lizzatresturentlite.ui.auth

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.tufelmalik.lizzatresturentlite.classes.AppModule
import com.tufelmalik.lizzatresturentlite.classes.FirebaseModule.provideFirebaseAuth
import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.classes.PreferenceManager
import com.tufelmalik.lizzatresturentlite.classes.Utilities.showToast
import com.tufelmalik.lizzatresturentlite.data.Users
import com.tufelmalik.lizzatresturentlite.databinding.ActivityCreateUserBinding
import com.tufelmalik.lizzatresturentlite.ui.admin.activity.AdminActivity
import com.tufelmalik.lizzatresturentlite.ui.cook.CookActivity
import com.tufelmalik.lizzatresturentlite.ui.viewodel.AuthViewModel
import com.tufelmalik.lizzatresturentlite.ui.viewodel.viewmodel_feectory.AuthViewModelFactory
import com.tufelmalik.lizzatresturentlite.ui.waiter.WaiterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateUserActivity : AppCompatActivity() {
    private val TAG = "CreateUserActivity"
    private val binding: ActivityCreateUserBinding by lazy {
        ActivityCreateUserBinding.inflate(layoutInflater)
    }
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var userProfileUri: Uri
    private lateinit var userName: String
    private lateinit var userRole: String
    private lateinit var viewModel: AuthViewModel
    private val roleArray = listOf(
        "Waiter", "Cook", "Admin"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val authRepository = AppModule.provideAuthRepository()
        val viewModelFactory = AuthViewModelFactory(provideFirebaseAuth(), authRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
        supportActionBar!!.hide()

        binding.userRole.adapter = ArrayAdapter(
            this@CreateUserActivity,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            roleArray
        )


        binding.txtGotoLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.etUniqPasscode.isVisible = binding.userRole.selectedItem.equals("Admin")

        binding.selectImgBtn.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        binding.btnRegistration.setOnClickListener {
            userEmail = binding.etRegEmail.text.toString()
            userPassword = binding.etRegPass.text.toString()
            userName = binding.etRegName.text.toString()
            userRole = binding.userRole.selectedItem.toString()
            binding.userRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedCategory = parent?.getItemAtPosition(position).toString()
                    binding.etUniqPasscode.isVisible = selectedCategory == "Admin"
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }


            }

            observer()
            if (validateInput()) {
                PreferenceManager.setUserRole(this@CreateUserActivity,userRole)
                if(::userProfileUri.isInitialized && !userProfileUri.toString().isEmpty()){
                    val user = Users(FirebaseAuth.getInstance().uid, userName, userEmail, userPassword, userProfileUri.toString(), userRole)
                    viewModel.registerAndSaveUserData(userEmail, userPassword, users = user)
                } else {
                    val user = Users(FirebaseAuth.getInstance().uid, userName, userEmail, userPassword, "profile image not selected yet", userRole)
                    viewModel.registerAndSaveUserData(userEmail, userPassword, users = user)
                }
            }
        }

    }

    private fun observer() {
        val dialog = ProgressDialog(this@CreateUserActivity)
        dialog.setMessage("Pleas wait...")
        dialog.setTitle("Creating your account")
        viewModel.registerResult.observe(this@CreateUserActivity) { state ->
            when (state) {
                is MyResult.Loading -> {
                 dialog.show()
                }

                is MyResult.Error -> {
                    dialog.dismiss()
                    showToast(this@CreateUserActivity, state.errorMsg.toString())
                }

                is MyResult.Success -> {
                    dialog.dismiss()
                    showToast(this@CreateUserActivity, state.data)
                    when (userRole) {
                        "Admin" -> startActivity(Intent(this, AdminActivity::class.java))
                        "Waiter" -> startActivity(Intent(this, WaiterActivity::class.java))
                        "Cook" -> startActivity(Intent(this, CookActivity::class.java))
                        else -> {}
                    }
                }

                else -> {}
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        if(userName.isEmpty()){
            isValid = false
        }

        if(userEmail.isEmpty()){
            isValid = false
        }
        if(userPassword.isEmpty()){
            isValid = false
        }
        if(userRole.isEmpty()){
            isValid = false
        }

        return isValid



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null){
            if(data.data != null){
                userProfileUri = data.data!!
                binding.userImage.setImageURI(userProfileUri)
            }
        }
    }
}