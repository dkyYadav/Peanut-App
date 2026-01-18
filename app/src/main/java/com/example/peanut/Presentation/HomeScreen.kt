package com.example.peanut.Presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.peanut.Presentation.viewmodel.AuthViewModel

@Composable
fun  HomeScreen(authViewModel: AuthViewModel) {


    Box(modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            authViewModel.logout()
        }) {
            Text("Logout")
        }
    }
}