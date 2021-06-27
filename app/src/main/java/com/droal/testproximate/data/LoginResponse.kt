package com.droal.testproximate.data



data class LoginResponse(
    val status: Boolean,
    val codeStatus: String,
    val message: String,
    val data: String
)
