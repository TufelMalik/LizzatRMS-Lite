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
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.tufelmalik.lizzatresturentlite.R
import com.tufelmalik.lizzatresturentlite.classes.Utilities
import com.tufelmalik.lizzatresturentlite.classes.Utilities.showToast
import com.tufelmalik.lizzatresturentlite.data.Food
import com.tufelmalik.lizzatresturentlite.databinding.EditFoodDialogBinding
import com.tufelmalik.lizzatresturentlite.databinding.EditFoodLayoutBinding

class FoodAdapter(private val context: Context, private val str: String) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    private var foodList: MutableList<Food> = arrayListOf()


    fun updateList(list: MutableList<Food>) {
        this.foodList = list
        notifyDataSetChanged()
    }


    inner class FoodViewHolder(private val binding: EditFoodLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food, position: Int) {
            binding.txtFoodNameLayout.text = food.foodName
            binding.txtFoodPriceLayout.text = "₹ " + food.foodPrice

            Glide.with(context).load(food.foodImageUri)
                .thumbnail(Glide.with(context).load(R.drawable.loading_gift))
                .into(binding.imgFoodLayout)
            if (str == "food") {
                binding.apply {
                    btnEditFoodLayout.isVisible = false
                    btnDeleteFoodLayout.isVisible = false
                }
            }

            binding.btnEditFoodLayout.setOnClickListener {
                showEditFoodDialog(food)
            }

            binding.btnDeleteFoodLayout.setOnClickListener {
                deleteFoodFromDB(food)
                notifyDataSetChanged()
            }
        }
    }

    private fun deleteFoodFromDB(food: Food) {
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection(Utilities.FirebaseFireStoreFood.ROOT_DIR)
            .document(food.foodCategory.toString())
            .collection("FoodData")
            .document(food.foodId.toString())

        try {
            collectionRef.delete()
            showToast(context, "Food Deleted..")
        } catch (e: Exception) {
            Log.d("Tufel", "Error : \n ${e.message.toString()}")
            showToast(context, "Failed to Delete food..")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layout =
            EditFoodLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(layout)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.bind(food, position)
    }

    override fun getItemCount() = foodList.size
    private fun showEditFoodDialog(food: Food) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val binding = EditFoodDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        binding.txtFoodNameDialog.text = food.foodName
        binding.txtFoodPriceDialog.text = food.foodPrice
        binding.txtFoodCategoryDialog.text = food.foodCategory
        binding.txtFoodIngredientsDialog.text = food.foodIngredients
        Glide.with(context).load(food.foodImageUri)
            .thumbnail(Glide.with(context).load(R.drawable.loading_gift))
            .into(binding.imgFoodDialog)

        binding.apply {
            txtFoodNameDialog.setOnClickListener {
                showEditableDataDialog("Name") { newValue ->
                    binding.txtFoodNameDialog.text = newValue
                }
            }
            txtFoodPriceDialog.setOnClickListener {
                showEditableDataDialog("Price") { newValue ->
                    binding.txtFoodPriceDialog.text = newValue
                }
            }
            txtFoodIngredientsDialog.setOnClickListener {
                showEditableDataDialog("Ingredients") { newValue ->
                    binding.txtFoodIngredientsDialog.text = newValue
                }
            }
        }
        binding.btnEditButtonFoodLayout.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            food.foodName = binding.txtFoodNameDialog.text.toString()
            food.foodPrice = binding.txtFoodPriceDialog.text.toString()
            food.foodIngredients = binding.txtFoodIngredientsDialog.text.toString()
            val collectionRef = db.collection(Utilities.FirebaseFireStoreFood.ROOT_DIR)
                .document(food.foodCategory.toString())
                .collection("FoodData")
                .document(food.foodId.toString())

            collectionRef.set(food)
                .addOnSuccessListener {
                    showToast(context, "Food saved successfully")
                    notifyDataSetChanged()
                    dialog.dismiss()
                }
                .addOnFailureListener {
                    showToast(context, "Saved successfully")
                    Log.d("FoodAdapter", it.message.toString())
                }
        }


        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width =
            (context.resources.displayMetrics.widthPixels * 0.8).toInt()
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.window?.attributes = layoutParams

        dialog.window?.setGravity(Gravity.CENTER)
        dialog.show()
    }

    fun showEditableDataDialog(str: String, callback: (String) -> Unit) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.edit_food_dailog2)
        val txt = dialog.findViewById<TextView>(R.id.txtTitleDialog)
        val input = dialog.findViewById<EditText>(R.id.etNewValueDialog)
        val btnSave = dialog.findViewById<Button>(R.id.buttonSaveDialog)
        txt.text = "Food $str"

        btnSave.setOnClickListener {
            val newVal = input.text.toString()
            callback(newVal)
            dialog.dismiss()
        }

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

