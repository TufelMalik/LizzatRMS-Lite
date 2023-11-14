package com.tufelmalik.lizzatresturentlite.classes

import android.content.Context
import android.widget.Toast

object Utilities  {


    fun showToast(context : Context,mesg : String){
        Toast.makeText(context,mesg,Toast.LENGTH_SHORT).show()
    }



    object FirebaseFireStoreFood {
        val ROOT_DIR = "Food"
        val VEG = "Vegetarian"
        val NONE_VEG = "Non vegetarian"
        val DESSERTS = "Desserts"
        val DRINKS = "Drinks"
        val SPECIALS = "Specials"
    }

    object FirebaseData {
        val USER = "Users"
        val PROFILE_IMAGE = "ProfileImage"
    }
    object FirestoreData{
        val USER = "Users"
    }

}