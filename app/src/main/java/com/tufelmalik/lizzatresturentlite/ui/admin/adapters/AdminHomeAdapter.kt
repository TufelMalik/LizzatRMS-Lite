package com.tufelmalik.lizzatresturentlite.ui.admin.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tufelmalik.lizzatresturentlite.classes.Utilities.showToast
import com.tufelmalik.lizzatresturentlite.data.AdminHomeItem
import com.tufelmalik.lizzatresturentlite.databinding.AdminHomeLayoutBinding
import com.tufelmalik.lizzatresturentlite.ui.EditFoodActivity
import com.tufelmalik.lizzatresturentlite.ui.StaffActivity
import com.tufelmalik.lizzatresturentlite.ui.admin.activity.ChatActivity
import com.tufelmalik.lizzatresturentlite.ui.cook.CookActivity
import com.tufelmalik.lizzatresturentlite.ui.waiter.WaiterActivity


class AdminHomeAdapter(
    private val itemList: List<AdminHomeItem>,
    val context: Context,
    private val who: String
) :
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
        if (who == "res") {
            holder.itemView.setOnClickListener {
                when (position) {
                    0 -> startActivityNow(StaffActivity(), "admin")
                    1 -> startActivityNow(EditFoodActivity(), "admin")
                    2 -> showToast(context, "on Next update..")
                    else -> {}
                }
            }
        }
        if (who == "role") {
            holder.itemView.setOnClickListener {
                when (position) {
                    0 -> startActivityNow(WaiterActivity(), "admin")
                    1 -> startActivityNow(CookActivity(), "admin")
                    2 -> showToast(context, "on Next update..")
                    else -> {}
                }
            }
        }
        if (who == "chat") {
            holder.itemView.setOnClickListener {
                when (position) {
                    0 -> startActivityNow(ChatActivity(), "cook")
                    1 -> startActivityNow(ChatActivity(), "waiter")
                    2 -> startActivityNow(ChatActivity(), "cashier")
                    else -> {}
                }
            }
        }
    }

    private fun startActivityNow(activity: Activity, key: String) {
        val intent = Intent(context, activity::class.java)
        intent.putExtra("key", key)
        context.startActivity(intent)
    }


}
