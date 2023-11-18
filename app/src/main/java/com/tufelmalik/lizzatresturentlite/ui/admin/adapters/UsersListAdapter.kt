package com.tufelmalik.lizzatresturentlite.ui.admin.adapters

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tufelmalik.lizzatresturentlite.R
import com.tufelmalik.lizzatresturentlite.data.Users
import com.tufelmalik.lizzatresturentlite.databinding.UserDetailsViewDialogBinding
import com.tufelmalik.lizzatresturentlite.databinding.UserLayoutBinding

class UsersListAdapter(private val context: Context) :
    RecyclerView.Adapter<UsersListAdapter.UserViewHolder>() {

    private var userList: List<Users> = emptyList()

    fun updateStaffList(newList : List<Users>){
        userList = newList
        notifyDataSetChanged()
    }



    inner class UserViewHolder(var binding: UserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users: Users) {
            binding.txtPersonNameStaffLayout.text = users.userName
            binding.txxRoleLayoutStaffLay.text = users.userRole
            Log.d("Tufel","User name : \n\n ${users.userName}")
            Glide.with(context).load(users.profileUrl)
                .thumbnail(Glide.with(context).load(R.drawable.loading_gift))
                .into(binding.imgPersonLayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layout = UserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(layout)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val users = userList[position]
        holder.bind(users)
        Log.d("Tufel","\\\\\\\\ \n $users")
        holder.itemView.setOnClickListener {
            showUserDetailsDialog(userList[position])
        }
    }

    private fun showUserDetailsDialog(users: Users) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val binding = UserDetailsViewDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        binding.txtUserNameDialog.text = users.userName
        binding.txtUserEmailDialog.text = users.email
        binding.txtUserGenderDialog.text = users.userGender
        binding.txtUserRoleDialog.text = users.userRole
        binding.txtUserJoiningDateDialog.text = users.joiningDate

        Glide.with(context).load(users.profileUrl)
            .thumbnail(Glide.with(context).load(R.drawable.loading_gift))
            .into(binding.imgUserDialog)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width =
            (context.resources.displayMetrics.widthPixels * 0.8).toInt()
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.window?.attributes = layoutParams

        dialog.window?.setGravity(Gravity.CENTER)
        dialog.show()
    }


}