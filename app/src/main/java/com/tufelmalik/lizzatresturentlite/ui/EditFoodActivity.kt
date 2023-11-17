package com.tufelmalik.lizzatresturentlite.ui

import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tufelmalik.lizzatresturentlite.classes.AppModule
import com.tufelmalik.lizzatresturentlite.classes.FirebaseModule
import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.classes.Utilities
import com.tufelmalik.lizzatresturentlite.databinding.ActivityEditFoodBinding
import com.tufelmalik.lizzatresturentlite.ui.admin.adapters.FoodAdapter
import com.tufelmalik.lizzatresturentlite.ui.viewodel.FoodViewModel
import com.tufelmalik.lizzatresturentlite.ui.viewodel.viewmodel_feectory.FoodVMFactory

class EditFoodActivity : AppCompatActivity() {
    private val TAG = "EditFoodActivity"
    private val binding: ActivityEditFoodBinding by lazy {
        ActivityEditFoodBinding.inflate(layoutInflater)
    }
    private var category: String? = null
    private lateinit var viewModel: FoodViewModel
    private var key: String? = null
    private val foodAdapter = FoodAdapter(this@EditFoodActivity,"edit")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar!!.hide()
        key = intent.getStringExtra("key")

        initViewModel()
        initAdapter()
        observer()
        setCategoryLayout()
    }


    private fun observer() {
        viewModel.selectedCategory.observe(this) { checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            category = selectedRadioButton.text.toString()
            Log.d(TAG,"Selected category : \n\n $category")
            callGetFood(category!!)
        }

        viewModel.foodList.observe(this@EditFoodActivity) { state ->
            when (state) {
                MyResult.Loading -> {
                    binding.editProgressBar.isVisible = true
                    binding.editProgressBar.animate()

                }

                is MyResult.Success -> {
                    Log.d(TAG, "\n\n..Food Data : ${state.data} \n\n")
                    binding.editProgressBar.isVisible = false
                    foodAdapter.updateList(state.data.toMutableList())
                }

                is MyResult.Error -> {
                    binding.editProgressBar.isVisible = false
                    Log.d(TAG, "\n\n..Error : ${state.errorMsg} \n\n")
                    Utilities.showToast(this@EditFoodActivity, "Something went wrong !!")
                }

                else -> {}
            }
        }
    }



    private fun setCategoryLayout() {
        binding.tabGroupNf.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            category = selectedRadioButton.text.toString()
            viewModel.changeTabBg(checkedId, binding.tabGroupNf,this@EditFoodActivity)
            callGetFood(category!!)
        }
    }

    private fun initViewModel() {
        val foodRepo = AppModule.provideFoodRepository(
            FirebaseModule.provideFireStore(),
            FirebaseModule.provideFirebaseStorage()
        )
        viewModel = ViewModelProvider(this, FoodVMFactory(foodRepo))[FoodViewModel::class.java]
        viewModel.changeTabBg(binding.rbVegetarian.id, binding.tabGroupNf,this@EditFoodActivity)
        callGetFood(Utilities.FirebaseFireStoreFood.VEG)
    }

    private fun initAdapter() {
        binding.apply {
            editFoodRecyclerView.adapter = foodAdapter
            editFoodRecyclerView.layoutManager = LinearLayoutManager(this@EditFoodActivity)
        }
    }

    private fun callGetFood(newCategory :  String) {
        if (newCategory.isEmpty()) {
            viewModel.getFoodListByCategory(category.toString())
        } else {
            viewModel.getFoodListByCategory(newCategory)
        }
    }
}