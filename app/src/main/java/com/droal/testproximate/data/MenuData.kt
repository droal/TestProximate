package com.droal.testproximate.data

import com.google.gson.annotations.SerializedName

data class MenuData(
    @SerializedName("id") val id: Int,
    val icon: String,
    val productId: Int,
    val redirectTo: String
)
