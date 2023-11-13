package com.tufelmalik.lizzatresturentlite.classes


import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    private const val PREF_NAME = "MyAppPrefs"
    private const val KEY_AUTH_STATUS = "authStatus"
    private const val KEY_USER_ROLE = "userRole"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setUserAuthenticated(context: Context, isAuthenticated: Boolean) {
        getSharedPreferences(context).edit().putBoolean(KEY_AUTH_STATUS, isAuthenticated).apply()
    }

    fun isUserAuthenticated(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_AUTH_STATUS, false)
    }

    fun setUserRole(context: Context, userRole: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(KEY_USER_ROLE, userRole).apply()
    }

    fun getUserRole(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_USER_ROLE, "") ?: ""
    }
}
