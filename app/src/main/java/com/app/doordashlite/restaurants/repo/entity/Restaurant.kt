package com.app.doordashlite.restaurants.repo.entity

import android.os.Parcelable
import android.support.annotation.Keep
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName

@Keep
@Parcelize
data class Restaurant(
        @field:SerializedName("id")
        val id: Long,
        @field:SerializedName("name")
        val name: String?,
        @field:SerializedName("description")
        val description: String?,
        @field:SerializedName("cover_img_url")
        val coverImgUrl: String?,
        @field:SerializedName("status")
        val status: String?,
        @field:SerializedName("status_type")
        val statusType: String?,
        @field:SerializedName("delivery_fee")
        val deliveryFee: Double?): Parcelable