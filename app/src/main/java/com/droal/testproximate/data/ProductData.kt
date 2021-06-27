package com.droal.testproximate.data

import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductData(
    @SerializedName("id") val id: Int,
    val path: String,
    val image: String,
    val title: String,
    val longDescription: String,
    val shortDescription: String
): Parcelable
