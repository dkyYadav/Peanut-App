package com.example.peanut.data.remote

import com.example.peanut.data.dto.ApiRequest
import com.example.peanut.data.dto.LoginRequest
import com.example.peanut.domain.Model.AccountInfoResponse
import com.example.peanut.domain.Model.LoginResponse
import com.example.peanut.domain.Model.OpenTradeResponse
import com.example.peanut.domain.Model.PhoneResponse
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface PeanutApiService {

    @POST("api/ClientCabinetBasic/IsAccountCredentialsCorrect")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("api/ClientCabinetBasic/GetAccountInformation")
    suspend fun getAccountInformation(
        @Body request: ApiRequest
    ): AccountInfoResponse

    @POST("api/ClientCabinetBasic/GetOpenTrades")
    suspend fun getOpenTrades(
        @Body request: ApiRequest
    ): List<OpenTradeResponse>

    @POST("api/ClientCabinetBasic/GetLastFourNumbersPhone")
    suspend fun getPhoneNumbers(
        @Body request: ApiRequest
    ): ResponseBody
}