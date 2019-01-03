package com.app.doordashlite.restaurants.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.app.doordashlite.restaurants.repo.RestaurantRepository
import com.app.doordashlite.restaurants.repo.entity.RestaurantDetail
import javax.inject.Inject

class RestaurantDetailViewModel @Inject constructor() : ViewModel() {
    @Inject lateinit var restaurantRepo: RestaurantRepository

    fun getRestaurantDetail(id: Long): LiveData<RestaurantDetail> {
        return restaurantRepo.getRestaurantDetail(id)
    }
}