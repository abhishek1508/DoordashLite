package com.app.doordashlite.restaurants.repo.entity

import android.os.Parcelable
import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Address(
        @field:SerializedName("city")
        val city: String?,
        @field:SerializedName("subpremise")
        val subpremise: String?,
        @field:SerializedName("printable_address")
        val printableAddress: String?,
        @field:SerializedName("state")
        val state: String?,
        @field:SerializedName("street")
        val street: String?,
        @field:SerializedName("country")
        val country: String?,
        @field:SerializedName("shortname")
        val shortname: String?,
        @field:SerializedName("zipcode")
        val zipcode: String?,
        @field:SerializedName("id")
        val id: Long,
        @field:SerializedName("lat")
        val lat: Double,
        @field:SerializedName("lng")
        val lng: Double
): Parcelable