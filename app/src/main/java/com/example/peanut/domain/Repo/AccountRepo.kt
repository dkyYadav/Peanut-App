package com.example.peanut.domain.Repo

import android.util.Log
import com.example.peanut.data.dto.AccountInfoRequest
import com.example.peanut.data.remote.PeanutApiService
import com.example.peanut.data.remote.RetrofitInstance
import com.example.peanut.domain.Model.AccountInfoResponse

class AccountRepository(api: PeanutApiService) {
    suspend fun getAccountInfo(login: String, token: String): AccountInfoResponse{

        Log.d("AccountVMRepo", " login: $login, Token: $token")
        return RetrofitInstance.api.getAccountInformation(
            AccountInfoRequest(login,token)

        )
    }
}