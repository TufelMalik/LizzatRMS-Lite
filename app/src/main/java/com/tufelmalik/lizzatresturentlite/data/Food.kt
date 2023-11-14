package com.tufelmalik.lizzatresturentlite.data

data class Food(
    var foodId : String? = null,
    val foodName : String? = null,
    val foodPrice : String? = null,
    val foodCategory: String? = null,
    var foodImageUri : String? = null,
    val foodIngredients : String? = null,
    val isVegetarian : Boolean? = null,
    val isAvailable : Boolean? = null
)
