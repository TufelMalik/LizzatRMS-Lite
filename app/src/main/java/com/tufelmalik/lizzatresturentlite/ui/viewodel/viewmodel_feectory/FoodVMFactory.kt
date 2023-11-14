package com.tufelmalik.lizzatresturentlite.ui.viewodel.viewmodel_feectory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tufelmalik.lizzatresturentlite.data.repo.FoodRepository
import com.tufelmalik.lizzatresturentlite.ui.viewodel.FoodViewModel

class FoodVMFactory(private val foodRepository: FoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodViewModel(foodRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


