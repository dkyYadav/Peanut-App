package com.example.peanut

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels

import com.example.peanut.Navigation.NavController
import com.example.peanut.Presentation.viewmodel.AccountInfoViewModel
import com.example.peanut.Presentation.viewmodel.AuthViewModel
import com.example.peanut.Presentation.viewmodel.ViewModelFactory
import com.example.peanut.data.local.Preferences.AuthPreferences


class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory(application)
    }
    private val accountInfoViewModel: AccountInfoViewModel by viewModels {
        ViewModelFactory(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val preferences = AuthPreferences(applicationContext)
        setContent {
            NavController(
                authViewModel = authViewModel,
                accountInfoViewModel = accountInfoViewModel,
                preferences = preferences
            )
        }
    }
}

