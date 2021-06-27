package com.droal.testproximate.network

import com.droal.testproximate.data.LoginBody
import com.droal.testproximate.data.LoginResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private const val BASE_URL = "https://serveless.proximateapps-services.com.mx"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ProximateApiService {

    @POST("/proximatetools/dev/webadmin/testproximate/login")
    fun loginUser(@Body body: LoginBody): Call<LoginResponse>
}

object ProximateApi{
    val retrofitService : ProximateApiService by lazy {
        retrofit.create(ProximateApiService::class.java)
    }
}