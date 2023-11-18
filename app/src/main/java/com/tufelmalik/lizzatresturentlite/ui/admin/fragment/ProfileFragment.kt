package com.tufelmalik.lizzatresturentlite.ui.admin.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.common.io.Resources
import com.tufelmalik.lizzatresturentlite.R
import com.tufelmalik.lizzatresturentlite.classes.AppModule
import com.tufelmalik.lizzatresturentlite.classes.FirebaseModule
import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.classes.Utilities
import com.tufelmalik.lizzatresturentlite.data.Users
import com.tufelmalik.lizzatresturentlite.databinding.FragmentProfileBinding
import com.tufelmalik.lizzatresturentlite.databinding.UserDetailsViewDialogBinding
import com.tufelmalik.lizzatresturentlite.ui.viewodel.AuthViewModel
import com.tufelmalik.lizzatresturentlite.ui.viewodel.viewmodel_feectory.AuthViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileFragment : Fragment() {
    private val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: AuthViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initViewModel()
        viewModel.getAllUsersList()
        observeUserList()
        return binding.root
    }

    private fun formatJoiningDate(joiningDate: String?): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val joiningDateMillis: Long? = joiningDate?.toLongOrNull()
        return if (joiningDateMillis != null) {
            val date = Date(joiningDateMillis)
            dateFormat.format(date)
        } else {
            ""
        }
    }

    private fun initViewModel() {
        val authRepository = AppModule.provideAuthRepository()
        val viewModelFactory =
            AuthViewModelFactory(FirebaseModule.provideFirebaseAuth(), authRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
    }

    private fun observeUserList() {
        viewModel.usersList.observe(viewLifecycleOwner) { userListState ->
            when (userListState) {
                is MyResult.Success -> {
                    val currentUserData =
                        userListState.data.filter { it.userId == FirebaseModule.provideCurrentUserID() }
                    setProfileDataOnUI(currentUserData)
                }

                is MyResult.Error -> Utilities.showToast(
                    requireContext(),
                    "Failed to retrieve user list"
                )

                else -> {}
            }
        }
    }

    private fun setProfileDataOnUI(users: List<Users>) {
        binding.apply {
            val user = users.firstOrNull()
            btnEditProfile.setOnClickListener { showEditDialog(user!!) }
            txtNameProfile.text = user?.userName ?: ""
            txtEmailProfile.text = user?.email
            if (user?.userRole.equals("Admin")) {
                txtRoleProfile.text = "an " + user?.userRole
            }
            txtRoleProfile.text = user?.userRole
            txtGenderProfile.text = user?.userGender ?: "Male"
            txtJoiningDateProfile.text = formatJoiningDate(user?.joiningDate ?: "")
            Glide.with(requireContext()).load(user?.profileUrl)
                .thumbnail(Glide.with(requireContext()).load(R.drawable.loading_gift))
                .into(binding.imgUserProfile)
        }
    }

    private fun showEditDialog(users: Users) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val binding = UserDetailsViewDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        binding.txtUserNameDialog.text = users.userName
        binding.txtUserEmailDialog.text = users.email
        binding.txtUserGenderDialog.text = users.userGender ?: "Male"
        binding.txtUserRoleDialog.text = users.userRole
        binding.txtUserJoiningDateDialog.text = users.joiningDate
        binding.btnUserDialog.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_green))
        Glide.with(requireContext()).load(users.profileUrl)
            .thumbnail(Glide.with(requireContext()).load(R.drawable.loading_gift))
            .into(binding.imgUserDialog)

        binding.btnUserDialog.setOnClickListener {
            saveDataInDB()
        }
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width =
            (requireContext().resources.displayMetrics.widthPixels * 0.8).toInt()
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.window?.attributes = layoutParams

        dialog.window?.setGravity(Gravity.CENTER)
        dialog.show()
    }

    private fun saveDataInDB() {



    }


}