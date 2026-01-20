package com.example.peanut.data.local.Preferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit

class AuthPreferences (context: Context){

    private val preferences: SharedPreferences =
        context.getSharedPreferences("User_Prefs",  Context.MODE_PRIVATE)

    companion object{
        private const val  KEY_TOKEN = "Token"
        private const val  KEY_LOGIN = "Login"
        private const val  KEY_EXPIRY = "Token_Expiry"
    }

    fun saveSession(token: String,login: String){

        preferences.edit()
            .putString(KEY_TOKEN,token)
            .putString(KEY_LOGIN,login)
            .apply()
        Log.d("VMLOGIN", "Token saved: $token")
    }



    fun getLogin(): String?{
        return preferences.getString(KEY_LOGIN,null)
    }

    fun getToken(): String?{
        return preferences.getString(KEY_TOKEN,null)
    }
    fun clearSession() {
        preferences.edit {
            clear()
        }
    }
}