package com.tufelmalik.lizzatresturentlite.classes


sealed class MyResult<out T>{
    data class Success<out T>(val data: T) : MyResult<T>()
    data class Error<out T>(val errorMsg: String?) : MyResult<T>()
    object Loading : MyResult<Nothing>()
    class Unspecified<T> : MyResult<T>()
}
