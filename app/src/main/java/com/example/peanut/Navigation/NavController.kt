package com.example.peanut.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.peanut.Presentation.HomeScreen
import com.example.peanut.Presentation.viewmodel.AuthViewModel
import com.example.tezora.presentation.auth.view.Login
import androidx.compose.runtime.getValue


@Composable
fun NavController(authViewModel: AuthViewModel) {
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
            HomeScreen(
                authViewModel = authViewModel
            )
        }
    }
}