package com.droal.testproximate.data

import com.google.gson.annotations.SerializedName

data class DataResponse(

    @SerializedName("menu") val menu: ArrayList<MenuData>,
    @SerializedName("user")  val user: UserData,
    @SerializedName("products")  val products: ArrayList<ProductData>
)
