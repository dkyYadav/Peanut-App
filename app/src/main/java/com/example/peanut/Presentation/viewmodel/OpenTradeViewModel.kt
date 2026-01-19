package com.example.peanut.Presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peanut.UiState
import com.example.peanut.data.local.Preferences.AuthPreferences
import com.example.peanut.domain.Model.OpenTradeResponse
import com.example.peanut.domain.Repo.OpenTreadsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OpenTradeViewModel(
    private val repository: OpenTreadsRepo,
    private val preferences: AuthPreferences
): ViewModel() {

    private  val _openTreadInfo = MutableStateFlow<UiState<List<OpenTradeResponse>>>(UiState.Idle)
    val openTreadInfo: StateFlow<UiState<List<OpenTradeResponse>>> = _openTreadInfo

    fun fetchOpenTread(login: String, token: String){
        viewModelScope.launch {
            Log.d("VMOPenTreads", "API call started")
            _openTreadInfo.value = UiState.Loading

            try {
                val response = repository.getOpenTreads(login,token)
                _openTreadInfo.value = UiState.Success(response)
                Log.d("VMOPenTreads","Data From Api ${response.size}")
            }catch (e: Exception){
                Log.e("VMOPenTreads",e.message.toString())
                _openTreadInfo.value = UiState.Failure(e.message?:"Unknown Error")
            }
        }
    }
    fun refetchData(){
        val login = preferences.getLogin()
        val token = preferences.getToken()
        fetchOpenTread(login.toString(), token.toString())
    }
}