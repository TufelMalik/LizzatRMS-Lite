package com.tufelmalik.lizzatresturentlite.ui.admin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tufelmalik.lizzatresturentlite.R
import com.tufelmalik.lizzatresturentlite.classes.Utilities.showToast
import com.tufelmalik.lizzatresturentlite.data.Food
import com.tufelmalik.lizzatresturentlite.databinding.FoodLayoutBinding

class FoodAdapter(private val context: Context) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    private var foodList : MutableList<Food> = arrayListOf()

    fun updateList(list: MutableList<Food>){
        this.foodList = list
        notifyDataSetChanged()
    }
    fun removeItem(position: Int){
        foodList.removeAt(position)
        notifyItemChanged(position)
    }



    inner class FoodViewHolder(private val binding : FoodLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(food: Food, position: Int) {
            binding.txtFoodNameLayout.text = food.foodName
            binding.txtFoodIsVegLayout.text = if (food.isVegetarian == true) "Vegetarian" else "Non-Vegetarian"
            Glide.with(context).load(food.foodImageUri)
                .thumbnail(Glide.with(context).load(R.drawable.ic_add_food))
                .into(binding.imgFoodLayout)
            binding.btnEditFoodLayout.setOnClickListener {
                showToast(context,"Edit Clicked..")
            }

            binding.btnDeleteFoodLayout.setOnClickListener {
                showToast(context,"Delete Clicked..")
            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layout = FoodLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FoodViewHolder(layout)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.bind(food,position)
    }

    override fun getItemCount() = foodList.size



}