package com.example.peanut.domain.Repo

import com.example.peanut.data.dto.ApiRequest
import com.example.peanut.data.remote.RetrofitInstance
import com.example.peanut.domain.Model.OpenTradeResponse

class OpenTreadsRepo {
    suspend fun getOpenTreads(login: String, token: String): List<OpenTradeResponse>{
        return RetrofitInstance.api.getOpenTrades(
            ApiRequest(login,token)
        )
    }
}