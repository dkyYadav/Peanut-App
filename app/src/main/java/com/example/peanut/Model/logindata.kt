package com.example.peanut.Model

data class LoginRequest(
    val login: String,
    val password: String
)

data class LoginResponse(
    val result: Boolean,
    val token: String?
)
