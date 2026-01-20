package com.example.peanut.Presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peanut.domain.Model.LoginResponse
import com.example.peanut.domain.Repo.AuthRepository
import com.example.peanut.UiState
import com.example.peanut.data.local.Preferences.AuthPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
    private val preferences: AuthPreferences
) : ViewModel() {

    private val _loginResult = MutableStateFlow<UiState<LoginResponse?>>(UiState.Idle)
    val loginResult: StateFlow<UiState<LoginResponse?>> = _loginResult

    // session state
    private val _isloggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isloggedIn

    init {
      checkSession()
    }

    fun login(login: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = UiState.Loading

            try {
                val response = repository.login(login, password)
                if (response.result == true && response.token != null) {
                    preferences.saveSession(
                        token = response.token.toString(),
                        login = login
                    )
                    _isloggedIn.value = true
                    _loginResult.value = UiState.Success(response)

                } else {
                    _loginResult.value = UiState.Failure("Login Failed")
                }
                Log.d("VMLOGIN", "TOKEN: ${response.token}")


            } catch (e: Exception) {
                Log.e("VMLOGIN", "Error ${e.message}",)
                _loginResult.value = UiState.Failure("Login Failed")
            }
        }
    }

    fun checkSession(){
        val token = preferences.getToken()

        if (token.isNullOrEmpty()){
            _isloggedIn.value = false

        }
            _isloggedIn.value = true

        Log.d("SESShjuION", "Token from prefs on app start: $token")
        _isloggedIn.value = !token.isNullOrEmpty()
    }


    fun logout(){
        preferences.clearSession()
        _isloggedIn.value = false
    }
}