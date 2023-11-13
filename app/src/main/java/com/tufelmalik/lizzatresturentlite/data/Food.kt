package com.tufelmalik.lizzatresturentlite.data

data class Food(
    val foodId : String? = null,
    val foodName : String? = null,
    val foodPrice : String? = null,
    val foodCategory: String? = null,
    val foodImageUri : String? = null,
    val foodIngredients : List<String>? = emptyList(),
    val isVegetarian : Boolean? = null,
    val isAvailable : Boolean? = null
)
