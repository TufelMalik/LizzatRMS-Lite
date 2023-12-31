package com.tufelmalik.lizzatresturentlite.ui.viewodel

import android.content.Context
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tufelmalik.lizzatresturentlite.R
import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.data.Food
import com.tufelmalik.lizzatresturentlite.data.repo.FoodRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import kotlin.coroutines.coroutineContext

class FoodViewModel(private val repo: FoodRepository) : ViewModel() {
    //    -------------------------------------------
    //  this live data for observing food list...
    private val _foodList = MutableLiveData<MyResult<List<Food>>>()
    val foodList: LiveData<MyResult<List<Food>>>
        get() = _foodList

    //    -------------------------------------------
    //  this live data for adding food list...
    private val _saveFoodList = MutableLiveData<MyResult<Pair<Food, String>>>()
    val saveFoodList: LiveData<MyResult<Pair<Food, String>>>
        get() = _saveFoodList

    //    -------------------------------------------
    //  this live data for updating...
    private val _updateFoodList = MutableLiveData<MyResult<String>>()
    val updateFoodList: LiveData<MyResult<String>>
        get() = _updateFoodList

    //    -------------------------------------------
    //  this live data for deleting food list...
    private val _deleteFoodList = MutableLiveData<MyResult<String>>()
    val deleteFoodList: LiveData<MyResult<String>>
        get() = _deleteFoodList


    private val _selectedCategory = MutableLiveData<Int>()
    val selectedCategory: LiveData<Int>
        get() = _selectedCategory


    fun saveFoodData(category: String, food: Food) {
        _saveFoodList.value = MyResult.Loading
        viewModelScope.launch {
            repo.saveFoodData(category, food) {
                _saveFoodList.value = it
            }
        }
    }

    fun getFoodListByCategory(category: String) {
        _foodList.value = MyResult.Loading
        viewModelScope.launch {
            repo.getFoodListByCategory(category) { result ->
                _foodList.value = result
            }
        }
    }


    fun getFoodListByName(foodName: String, category: String) {
        _foodList.value = MyResult.Loading
        viewModelScope.launch {
            repo.getFoodListByName(foodName = foodName, category = category) {
                _foodList.value = it
            }
        }
    }




//    fun setSelectedCategory(categoryId: Int) {
//        _selectedCategory.value = categoryId
//    }

    fun changeTabBg(selectedCategory: Int, tabGroupNf: RadioGroup,context: Context) {
        for (i in 0 until tabGroupNf.childCount) {
            val cardView = tabGroupNf.getChildAt(i) as? CardView
            cardView?.let {
                val radioButton = it.getChildAt(0) as? RadioButton
                radioButton?.let {
                    if (radioButton.id != selectedCategory) {
                        radioButton.setBackgroundColor(ContextCompat.getColor(context, R.color.gold))
                    } else {
                        radioButton.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))
                    }
                }
            }
        }
    }




}