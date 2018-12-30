package com.app.doordashlite.restaurants.api

import com.app.doordashlite.restaurants.repo.entity.Restaurant
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantService {

    @GET("v2/restaurant")
    fun getRestaurants(@Query("lat") lat: Double,
                       @Query("lng") lng: Double,
                       @Query("offset") offset: Int,
                       @Query("limit") limit: Int): Observable<Result<List<Restaurant>>>
}