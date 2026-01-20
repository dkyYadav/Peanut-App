package com.example.peanut.Presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peanut.UiState
import com.example.peanut.data.local.Preferences.AuthPreferences
import com.example.peanut.domain.Model.AccountInfoResponse
import com.example.peanut.domain.Model.PhoneResponse
import com.example.peanut.domain.Repo.AccountRepository
import com.example.peanut.domain.Repo.PhoneNoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AccountInfoViewModel(
    private val repository: AccountRepository,
    private val phoneRepository: PhoneNoRepository,
    private val preferences: AuthPreferences,
   private val authViewModel: AuthViewModel
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

    private val _phoneNoInfo = MutableStateFlow<UiState<PhoneResponse?>>(UiState.Idle)
    val phoneNoInfo: StateFlow<UiState<PhoneResponse?>> = _phoneNoInfo

    fun fetchPhoneNo(login: String,token: String){
        viewModelScope.launch {
            _phoneNoInfo.value = UiState.Loading

            try {
                val Response = phoneRepository.getPhoneNo(login, token)
                _phoneNoInfo.value = UiState.Success(Response)
                Log.d("AccountVM", "Account Data Fetch Successful :${Response}")
            }catch (e: HttpException){
                if (e.code() == 500 || e.code() == 400) {
                     authViewModel.logout()
                    _accountInfo.value = UiState.Failure("SESSION_EXPIRED")
                } else {
                    _accountInfo.value = UiState.Failure("Something went wrong")
                }
            }catch (e: Exception){
                _accountInfo.value = UiState.Failure(e.message?: "Unknown error")
                Log.e("AccountVM", e.message.toString())
            }
        }
    }

    fun reloadData(){
       val login = preferences.getLogin()
       val token = preferences.getToken()
        fetchAccountInfo(login.toString(), token.toString())
        fetchPhoneNo(login.toString(),token.toString())
    }
}