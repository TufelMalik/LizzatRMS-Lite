package com.tufelmalik.lizzatresturentlite.ui.viewodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.data.Food
import com.tufelmalik.lizzatresturentlite.data.repo.FoodRepository
import kotlinx.coroutines.launch

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


    fun saveFoodData(category: String,food: Food) {
        _saveFoodList.value = MyResult.Loading
        viewModelScope.launch {
            repo.saveFoodData(category,food) {
                _saveFoodList.value = it
            }
        }
    }

    fun getFoodList(category: String){
        _foodList.value = MyResult.Loading
        viewModelScope.launch {
            repo.getFoodList(category) {
                _foodList.value = it
            }
        }
    }

    fun updateFoodData(foodId: String,category: String,newFoodData: Food) {
        _updateFoodList.value = MyResult.Loading
        viewModelScope.launch {
            repo.updateFoodData(foodId = foodId, category = category,food = newFoodData) {
                _updateFoodList.value = MyResult.Success("Updating Successful...")
            }
        }
    }

    fun deleteFoodData(foodId : String,category: String){
        _deleteFoodList.value = MyResult.Loading
        viewModelScope.launch {
            repo.deleteFoodData(foodId,category) {
                _deleteFoodList.value = MyResult.Success("Deleting Successful...")
            }
        }
    }

}