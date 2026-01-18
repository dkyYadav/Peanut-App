package com.example.peanut.Presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.peanut.Repo.AuthRepository
import com.example.peanut.data.local.Preferences.AuthPreferences
import com.example.peanut.data.remote.RetrofitInstance

class ViewModelFactory(
    private val context: Context
) :ViewModelProvider.Factory{


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)){
            val repository = AuthRepository(RetrofitInstance.api)
            val preferences = AuthPreferences(context.applicationContext)
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository, preferences
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}