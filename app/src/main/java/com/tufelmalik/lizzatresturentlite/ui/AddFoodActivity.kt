package com.tufelmalik.lizzatresturentlite.ui

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.tufelmalik.lizzatresturentlite.classes.AppModule
import com.tufelmalik.lizzatresturentlite.classes.FirebaseModule
import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.classes.Utilities.showToast
import com.tufelmalik.lizzatresturentlite.data.Food
import com.tufelmalik.lizzatresturentlite.data.repo.FoodRepository
import com.tufelmalik.lizzatresturentlite.databinding.ActivityAddFoodBinding
import com.tufelmalik.lizzatresturentlite.ui.viewodel.FoodViewModel
import com.tufelmalik.lizzatresturentlite.ui.viewodel.viewmodel_feectory.FoodVMFactory


class AddFoodActivity : AppCompatActivity() {
    private val binding: ActivityAddFoodBinding by lazy {
        ActivityAddFoodBinding.inflate(layoutInflater)
    }
    private var foodImageUri: Uri? = null
    private var foodName: String? = null
    private var foodPrice: String? = null
    private var foodCategory: String? = null
    private var foodIngredients: String? = null
    private lateinit var viewModel: FoodViewModel
    private val foodCategories = arrayOf(
        "Select Category", "Vegetarian", "Non Vegetarian", "Desserts", "Drinks"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar!!.hide()

        initViewModel()

        binding.spinnerFoodCategory.adapter = ArrayAdapter(
            this@AddFoodActivity,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            foodCategories
        )
        binding.spinnerFoodCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    foodCategory = parent?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        binding.editTextFoodImageUri.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }




        observer()
        binding.buttonAddFood.setOnClickListener {
            if (validateInput()) {
                val food =
                    Food(foodId = "", foodName, foodPrice, foodCategory, foodImageUri.toString())
                viewModel.saveFoodData(category = foodCategory.toString(), food = food)
            } else {
                showToast(this@AddFoodActivity, "Food details are required!!")
            }
        }
    }

    private fun observer() {
        val dialog = ProgressDialog(this@AddFoodActivity)
        dialog.setMessage("Pleas wait...")
        viewModel.saveFoodList.observe(this@AddFoodActivity) { state ->
            when (state) {
                is MyResult.Loading -> {
                    dialog.show()
                }

                is MyResult.Error -> {
                    dialog.dismiss()
                    showToast(this@AddFoodActivity, state.errorMsg.toString())
                }

                is MyResult.Success -> {
                    dialog.dismiss()
                    showToast(this@AddFoodActivity, state.data.second)
                }

                else -> {}
            }
        }

    }

    private fun initViewModel() {
        val foodRepository: FoodRepository = AppModule.provideFoodRepository(
            FirebaseFirestore.getInstance(),
            FirebaseModule.provideFirebaseStorage()
        )
        val viewModelFactory = FoodVMFactory(foodRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[FoodViewModel::class.java]
    }

    private fun takeInput() {
        foodName = binding.editTextFoodName.text.toString()
        foodPrice = binding.editTextFoodPrice.text.toString()
        foodIngredients = binding.editTextFoodIngredients.text.toString()
    }


    private fun validateInput(): Boolean {
        takeInput()
        var isValid = true

        if (foodName.isNullOrBlank()) {
            binding.editTextFoodName.error = "Required!!"
            isValid = false
        }

        if (foodPrice.isNullOrBlank()) {
            binding.editTextFoodPrice.error = "Required!!"
            isValid = false
        }

        if (foodIngredients.isNullOrBlank()) {
            binding.editTextFoodIngredients.error = "Required!!"
            isValid = false
        }
        if (foodIngredients!!.isEmpty()) {
            binding.editTextFoodIngredients.error = "Required!!"
            foodIngredients = "\nIngredients not specified by Restaurant."
        }
        if(foodCategory.equals("Select Category")){
            showToast(this@AddFoodActivity,"Pleas select any category")
            isValid = false
        }
        return isValid
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (data.data != null) {
                foodImageUri = data.data!!
                binding.editTextFoodImageUri.setImageURI(foodImageUri)
            }
        }
    }

}