package com.example.peanut.domain.Model


data class AccountInfoResponse(
    val address: String,
    val balance: Double,
    val city: String,
    val country: String,
    val currency: Int,
    val equity: Double,
    val freeMargin: Double,
    val leverage: Int,
    val name: String,
    val phone: String,
    val totalTradesCount: Int,
    val totalTradesVolume: Double,
    val verificationLevel: Int,
    val zipCode: String
)
