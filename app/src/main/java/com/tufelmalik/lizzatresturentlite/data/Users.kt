package com.tufelmalik.lizzatresturentlite.data

data class Users(
    var userId : String? = null,
    val userName : String? = null,
    var email : String? = null,
    var password : String? = null,
    var profileUrl : String? = null,
    val userRole : String? = null
    ){
    constructor(email: String?,password: String?) : this() {
        this.email = email
        this.password = password
    }

    fun setProfileImageUri(newUri : String? = null){
            profileUrl = newUri
    }

}