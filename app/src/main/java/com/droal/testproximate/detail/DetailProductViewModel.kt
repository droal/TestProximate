package com.droal.testproximate.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.droal.testproximate.data.DataResponse
import com.droal.testproximate.data.ProductData

class DetailProductViewModel(productData: ProductData): ViewModel() {

    private val _product = MutableLiveData<ProductData>()
    val product: LiveData<ProductData>
        get() = _product

    init {
        _product.value = productData
    }
}