package com.example.tezora.presentation.auth.view

import com.example.peanut.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.peanut.Navigation.Routes
import com.example.peanut.Presentation.viewmodel.AuthViewModel
import com.example.peanut.Presentation.viewmodel.ViewModelFactory
import com.example.peanut.UiState

@Composable
fun Login(
    navHostController: NavHostController
) {

    var userId by remember() { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val authViewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(
            context = LocalContext.current
        )
    )
    val loginResult by authViewModel.loginResult.collectAsState()

    LaunchedEffect(loginResult) {
        when(loginResult){
            is UiState.Failure -> {
                showError = true
                errorMessage = (loginResult as UiState.Failure).error
            }
            is UiState.Success<*> -> {
                navHostController.navigate(Routes.HomeScreen)
            }
            else -> {

            }
        }
    }

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(start = 25.dp, end = 25.dp, top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Box(modifier = Modifier.padding(bottom = 30.dp)
        ){
            Text(
                text = "Welcome Back!", fontSize = 50.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = userId,
            onValueChange = { userId = it },
            label = {
                Text("UserId")
            },
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),

            leadingIcon = {
                Icon(imageVector = Icons.Default.Person,
                    contentDescription = "UserId")
            }
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "lock"
                )
            },
            trailingIcon = {
                Icon(
                    painterResource(R.drawable.ic_eye),
                    contentDescription = "Eye"

                )
            }

        )

        Spacer(modifier = Modifier.height(20.dp))

        // show Error msg
        if (showError){
            Text(
                text =  errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Button( onClick = {
            // button on click
            if (userId.isNotBlank() && password.isNotBlank()){
               authViewModel.login(userId,password)
            }
        },
            modifier = Modifier.height(50.dp).fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults
               .buttonColors(containerColor = colorResource(R.color.App_Color) )

        ) {

            when(loginResult){
                UiState.Loading -> {

                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 1.dp
                    )
                }
                else -> {
                    Text(
                        " Login ",
                        color = Color.Black
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Text(
                text = "Create An Account",
                modifier = Modifier, fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Sign Up",
              color = colorResource(R.color.App_Color),
                modifier = Modifier.clickable {
                }, fontSize = 20.sp
            )
        }
    }
}