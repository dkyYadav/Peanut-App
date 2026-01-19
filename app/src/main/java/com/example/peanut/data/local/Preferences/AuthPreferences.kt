package com.example.peanut.data.local.Preferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class AuthPreferences (context: Context){

    private val preferences: SharedPreferences =
        context.getSharedPreferences("User_Prefs",  Context.MODE_PRIVATE)

    fun saveToken(token: String){
        preferences.edit()
            .putString("Token",token)
            .apply()
        Log.d("VMLOGIN", "Token saved: $token")
    }
    fun saveLogin(login: String){
        preferences.edit()
            .putString("Login",login)
            .apply()
        Log.d("VMLOGIN", "Token saved: $login")
    }
    fun getLogin(): String?{
        return preferences.getString("Login",null)
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