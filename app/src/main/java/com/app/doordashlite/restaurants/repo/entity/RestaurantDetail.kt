package com.app.doordashlite.restaurants.repo.entity

import android.os.Parcelable
import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class RestaurantDetail(
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
        @field:SerializedName("address")
        val address: Address
): Parcelable