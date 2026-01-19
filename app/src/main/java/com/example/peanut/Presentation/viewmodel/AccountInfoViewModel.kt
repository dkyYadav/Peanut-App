package com.example.peanut.Presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peanut.UiState
import com.example.peanut.data.local.Preferences.AuthPreferences
import com.example.peanut.domain.Model.AccountInfoResponse
import com.example.peanut.domain.Repo.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountInfoViewModel(
    private val repository: AccountRepository,
    private val preferences: AuthPreferences
): ViewModel() {

    private val _accountInfo = MutableStateFlow<UiState<AccountInfoResponse?>>(UiState.Idle)
    val accountInfo: StateFlow<UiState<AccountInfoResponse?>> = _accountInfo

    fun fetchAccountInfo(login: String, token: String){
        viewModelScope.launch {
            _accountInfo.value = UiState.Loading
            try {
                 val response = repository.getAccountInfo(login,token)
                _accountInfo.value = UiState.Success(response)
                Log.d("AccountVM", "Account Data Fetch Successful :${response.name}")
            }catch (e: Exception){
               _accountInfo.value = UiState.Failure(e.message?: "Unknown error")
                Log.e("AccountVM", e.message.toString())
            }
        }
    }
}