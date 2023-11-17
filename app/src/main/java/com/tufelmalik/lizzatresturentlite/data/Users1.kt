package com.tufelmalik.lizzatresturentlite.data

data class Users1(

//    https://www.geeksforgeeks.org/expandable-recyclerview-in-android-with-kotlin/


    var userId: String? = null,
    val userName: String? = null,
    val userRole: String? = null,
    var profileUrl: String? = null,
    var subList: MutableList<ChileUser1> = ArrayList()
)

data class ChileUser1(
    var email : String? = null,
    var gender : String? = null,
    val joiningDate: String? = System.currentTimeMillis().toString()
)

object Constants {
    const val PARENT = 0
    const val CHILD = 1
}