package com.droal.testproximate.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.droal.testproximate.data.DataResponse
import com.droal.testproximate.data.LoginBody
import com.droal.testproximate.data.LoginResponse
import com.droal.testproximate.data.ProductData
import com.droal.testproximate.network.ProximateApi
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {
    init {
    }

    private val _responseLoginMsg = MutableLiveData<String>()
    val responseLoginMsg: LiveData<String>
        get() = _responseLoginMsg

    private val _responseLoginOk = MutableLiveData<Boolean>()
    val responseLoginOk: LiveData<Boolean>
        get() = _responseLoginOk

    private val _responseData = MutableLiveData<DataResponse>()
    val responseData: LiveData<DataResponse>
        get() = _responseData


    private val _navigateToSelectedProduct = MutableLiveData<ProductData>()
    val navigateToSelectedProduct: LiveData<ProductData>
        get() = _navigateToSelectedProduct


    public fun login(user:String, password: String){

        if(user.isEmpty()){
            _responseLoginMsg.value = "Campo usuario está vacío, por favor ingrese un usuario valido"
            return
        }
        if(password.isEmpty()){
            _responseLoginMsg.value = "Campo password está vacío, por favor ingrese un password valido"
            return
        }

        val body = LoginBody(user, password)

        ProximateApi.retrofitService.loginUser(body).enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                val status: Boolean = response.body()?.status ?: false
                if(status){
                    _responseLoginOk.value = true

                    val dataresp: String = response.body()?.data.toString()
                    val data: DataResponse = Gson().fromJson(dataresp, DataResponse::class.java)
                    _responseData.value = data
                }else{
                    _responseLoginMsg.value = response.body()?.message.toString()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _responseLoginMsg.value = t.message
            }
        })

    }



    fun displayProductDetails(product: ProductData) {
        _navigateToSelectedProduct.value = product
    }

    fun displayProductDetailsComplete() {
        _navigateToSelectedProduct.value = null
    }

    fun displayProductListComplete() {
        _responseLoginOk.value = null
    }

    override fun onCleared() {
        super.onCleared()
        _responseLoginMsg.value = null
        _responseLoginOk.value = null
        _responseData.value = null
        _navigateToSelectedProduct.value = null
    }
}