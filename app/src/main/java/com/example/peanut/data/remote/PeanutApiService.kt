package com.example.peanut.data.remote

import com.example.peanut.UiState
import com.example.peanut.data.dto.AccountInfoRequest
import com.example.peanut.data.dto.LoginRequest
import com.example.peanut.domain.Model.AccountInfoResponse
import com.example.peanut.domain.Model.LoginResponse
import com.example.peanut.domain.Model.OpenTradeResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface PeanutApiService {

    @POST("api/ClientCabinetBasic/IsAccountCredentialsCorrect")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("api/ClientCabinetBasic/GetAccountInformation")
    suspend fun getAccountInformation(
        @Body request: AccountInfoRequest
    ): AccountInfoResponse

    @POST("api/ClientCabinetBasic/GetOpenTrades")
    suspend fun getOpenTrades(
        @Body request: AccountInfoRequest
    ): List<OpenTradeResponse>
}