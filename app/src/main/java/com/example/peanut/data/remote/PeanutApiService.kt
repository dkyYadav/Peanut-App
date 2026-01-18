package com.example.peanut.data.remote

import com.example.peanut.Model.LoginRequest
import com.example.peanut.Model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface PeanutApiService {

    @POST("api/ClientCabinetBasic/IsAccountCredentialsCorrect")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}