package com.app.doordashlite.restaurants

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.app.doordashlite.restaurants.repo.RestaurantRepository
import com.app.doordashlite.restaurants.repo.entity.Restaurant
import com.app.doordashlite.restaurants.repo.entity.local.LatLng
import javax.inject.Inject

const val DOORDASH_HQ_LAT = 37.422740
const val DOORDASH_HQ_LNG = -122.139956
const val OFFSET = 0
const val LIMIT = 50

class RestaurantViewModel @Inject constructor() : ViewModel() {
    @Inject lateinit var restaurantRepo: RestaurantRepository
    private val latlng = LatLng(DOORDASH_HQ_LAT, DOORDASH_HQ_LNG)

    fun getRestaurants(lat: Double, lng: Double, offset: Int, limit: Int): LiveData<List<Restaurant>> {
        return restaurantRepo.getRestaurants(lat, lng, offset, limit, latlng)
    }

    fun getLatitude(): Double {
        return DOORDASH_HQ_LAT
    }

    fun getLongitude(): Double {
        return DOORDASH_HQ_LNG
    }

    fun getOffset(): Int {
        return OFFSET
    }

    fun getLimit(): Int {
        return LIMIT
    }
}