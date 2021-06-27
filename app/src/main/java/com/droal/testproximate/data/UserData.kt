package com.droal.testproximate.data

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("id") val id: Int,
    val name: String,
    val role: String,
    val lastName: String,
    val position: String
)
