package com.example.peanut.Repo

import com.example.peanut.Model.LoginRequest
import com.example.peanut.Model.LoginResponse
import com.example.peanut.data.remote.PeanutApiService

class AuthRepository (
    private val apiService: PeanutApiService
){
    suspend fun login(login: String, password: String): LoginResponse{
        val response = apiService.login(
            LoginRequest(login,password)
        )
        return  response
    }
}