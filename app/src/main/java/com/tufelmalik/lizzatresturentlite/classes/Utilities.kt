package com.tufelmalik.lizzatresturentlite.classes

import android.app.ProgressDialog
import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

object Utilities  {


    fun showProgressDialog(context: Context,msg: String? = null,title:String?=null , status: Boolean){
        val dialog = ProgressDialog(context)
        dialog.setMessage(msg)
        dialog.setTitle(title)
        if(status){
            dialog.show()
        }else{
            dialog.dismiss()
        }
    }



    fun showToast(context : Context,mesg : String){
        Toast.makeText(context,mesg,Toast.LENGTH_SHORT).show()
    }
    fun showBottomToast(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    object FirebaseData {
        val USER = "Users"
        val PROFILE_IMAGE = "ProfileImage"
    }
    object FirestoreData{
        val USER = "Users"
    }

}