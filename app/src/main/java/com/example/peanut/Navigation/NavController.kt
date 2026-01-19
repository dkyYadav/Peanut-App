package com.example.peanut.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.peanut.Presentation.Home
import com.example.peanut.Presentation.viewmodel.AuthViewModel
import com.example.tezora.presentation.auth.view.Login
import androidx.compose.runtime.getValue
import com.example.peanut.Presentation.viewmodel.AccountInfoViewModel
import com.example.peanut.data.local.Preferences.AuthPreferences


@Composable
fun NavController(authViewModel: AuthViewModel,
                  accountInfoViewModel: AccountInfoViewModel,
                  preferences: AuthPreferences
) {
    val navController = rememberNavController()

    val isLoogedIn by authViewModel.isLoggedIn.collectAsState()
    val startDestination = if (isLoogedIn){
        Routes.HomeScreen
    }else{
        Routes.LoginScreen
    }
    NavHost(navController = navController, startDestination = startDestination
    ){
        composable <Routes.LoginScreen>{
            Login(
                navHostController = navController
            )
        }

        composable <Routes.HomeScreen>{
            Home(
                authViewModel = authViewModel,
                accountInfoViewModel = accountInfoViewModel ,
                preferences = preferences
            )
        }
    }
}