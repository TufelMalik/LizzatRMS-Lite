package com.tufelmalik.lizzatresturentlite.ui.admin.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tufelmalik.lizzatresturentlite.data.AdminHomeItem
import com.tufelmalik.lizzatresturentlite.databinding.AdminHomeLayoutBinding
import com.tufelmalik.lizzatresturentlite.ui.AddFoodActivity
import com.tufelmalik.lizzatresturentlite.ui.EditFoodActivity
import com.tufelmalik.lizzatresturentlite.ui.StaffActivity
import com.tufelmalik.lizzatresturentlite.ui.cook.CookActivity
import com.tufelmalik.lizzatresturentlite.ui.waiter.WaiterActivity


class AdminHomeAdapter(private val itemList: List<AdminHomeItem>,val context :  Context) :
    RecyclerView.Adapter<AdminHomeAdapter.AdminHomeViewHolder>() {

    inner class AdminHomeViewHolder(private val binding: AdminHomeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AdminHomeItem) {
            binding.txtAdminHomeLayout.text = item.text
            binding.imgAdminHomeLayout.setImageResource(item.imageResourceId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminHomeViewHolder {
        val binding =
            AdminHomeLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdminHomeViewHolder(binding)
    }

    override fun getItemCount() = itemList.size


    override fun onBindViewHolder(holder: AdminHomeViewHolder, position: Int) {
        holder.bind(itemList[position])
        holder.itemView.setOnClickListener {
            when(position){
                0-> startActivityNow(StaffActivity(),"admin")
                1-> startActivityNow(WaiterActivity(),"admin")
                2-> startActivityNow(CookActivity(),"admin")
                3-> startActivityNow(AddFoodActivity(),"Add")
                4-> startActivityNow(EditFoodActivity(),"Edit")
                5-> startActivityNow(EditFoodActivity(),"Delete")

                else->{}
            }
        }
    }
    private fun startActivityNow(activity: Activity,key : String) {
        val intent = Intent(context,activity::class.java)
        intent.putExtra("key",key)
        context.startActivity(intent)

    }
}
