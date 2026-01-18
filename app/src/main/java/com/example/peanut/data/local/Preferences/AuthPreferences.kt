package com.example.peanut.data.local.Preferences

import android.content.Context
import android.content.SharedPreferences

class AuthPreferences (context: Context){

    private val preferences: SharedPreferences =
        context.getSharedPreferences("User_Prefs",  Context.MODE_PRIVATE)

    fun saveToken(token: String){
        preferences.edit()
            .putString("Token",token)
            .apply()
    }

    fun getToken(): String?{
        return preferences.getString("Token",null)
    }
    fun clearToken(){
        preferences.edit()
            .remove("Token")
            .apply()
    }
}