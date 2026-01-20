package com.example.peanut.Presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.peanut.domain.Repo.AuthRepository
import com.example.peanut.data.local.Preferences.AuthPreferences
import com.example.peanut.data.remote.RetrofitInstance
import com.example.peanut.domain.Repo.AccountRepository
import com.example.peanut.domain.Repo.OpenTreadsRepo
import com.example.peanut.domain.Repo.PhoneNoRepository

class ViewModelFactory(
    private val context: Context
) :ViewModelProvider.Factory{


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                val repository = AuthRepository(RetrofitInstance.api)
                val preferences = AuthPreferences(context.applicationContext)
                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(
                    repository, preferences
                ) as T
            }

            modelClass.isAssignableFrom(AccountInfoViewModel::class.java)->{
                val repository = AccountRepository(RetrofitInstance.api)
                val authrepository = AuthRepository(RetrofitInstance.api)
                val phoneRepository = PhoneNoRepository(RetrofitInstance.api)
                val preferences = AuthPreferences(context.applicationContext)
                val authViewModel = AuthViewModel(
                    authrepository,
                    preferences = preferences
                )
                @Suppress("UNCHECKED_CAST")
                AccountInfoViewModel(repository, phoneRepository, preferences,authViewModel) as T
            }

            modelClass.isAssignableFrom(OpenTradeViewModel::class.java)->{
                val repository = OpenTreadsRepo()
                val preferences = AuthPreferences(context.applicationContext)
                @Suppress("UNCHECKED_CAST")
                OpenTradeViewModel(repository,preferences) as T
            }


            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}