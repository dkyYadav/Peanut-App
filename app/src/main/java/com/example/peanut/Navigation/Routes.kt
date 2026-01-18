package com.example.peanut.Navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    object LoginScreen: Routes()

    @Serializable
    object  HomeScreen: Routes()
}