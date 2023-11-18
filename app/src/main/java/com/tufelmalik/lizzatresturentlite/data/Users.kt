package com.tufelmalik.lizzatresturentlite.data

import java.util.Date

data class Users(
    var userId : String? = null,
    val userName : String? = null,
    var email : String? = null,
    var password : String? = null,
    var profileUrl : String? = null,
    val userRole : String? = null,
    val userGender : String? = null,
    var joiningDate : String? = null
    )