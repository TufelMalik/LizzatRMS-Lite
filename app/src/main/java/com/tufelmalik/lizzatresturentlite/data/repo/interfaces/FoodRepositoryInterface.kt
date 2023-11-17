package com.tufelmalik.lizzatresturentlite.data.repo.interfaces

import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.data.Food

interface FoodRepositoryInterface {

    suspend fun saveFoodData(category: String,food: Food, result: (MyResult<Pair<Food,String>>) -> Unit)
    fun saveFoodInFireStore(uniqueKey: String,category: String,newImageUri : String,food: Food, result: (MyResult<String>) -> Unit)
    suspend fun getFoodListByName(foodName :String,category: String, result: (MyResult<List<Food>>) -> Unit)
    suspend fun getFoodListByCategory(category: String, result: (MyResult<List<Food>>) -> Unit)

}