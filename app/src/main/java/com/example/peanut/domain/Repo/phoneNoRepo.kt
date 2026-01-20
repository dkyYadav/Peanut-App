package com.example.peanut.domain.Repo

import com.example.peanut.data.dto.ApiRequest
import com.example.peanut.data.remote.PeanutApiService
import com.example.peanut.data.remote.RetrofitInstance
import com.example.peanut.data.remote.RetrofitInstance.api
import com.example.peanut.domain.Model.PhoneResponse

class PhoneNoRepository(api: PeanutApiService) {
    suspend fun getPhoneNo(login: String,token: String): PhoneResponse{
       val responseBody = api.getPhoneNumbers( ApiRequest(login,token))
        val rawString =responseBody.string()
        return PhoneResponse(
            lastFourDigits = rawString
        )
    }

}