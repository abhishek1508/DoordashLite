package com.app.doordashlite.restaurants.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.app.doordashlite.factory.ServiceGeneratorFactory
import com.app.doordashlite.restaurants.api.RestaurantService
import com.app.doordashlite.restaurants.repo.cache.RestaurantCache
import com.app.doordashlite.restaurants.repo.entity.Restaurant
import com.app.doordashlite.restaurants.repo.entity.local.LatLng
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class RestaurantRepository @Inject constructor(private val factory: ServiceGeneratorFactory,
                                               @Named("IO") private val backgroundThread: Scheduler,
                                               @Named("MAIN") private val mainThread: Scheduler) {

    fun getRestaurants(lat: Double, lng: Double, offset: Int, limit: Int, latLng: LatLng):
            LiveData<List<Restaurant>> {
        val restaurantList = RestaurantCache.getInstance().get(latLng)
        if (restaurantList != null) {
            return MutableLiveData<List<Restaurant>>().apply {
                postValue(restaurantList)
            }
        }
        val data: MutableLiveData<List<Restaurant>> = MutableLiveData()
        val restaurantApi =
                factory.createService(RestaurantService::class.java).getRestaurants(lat, lng, offset, limit)
        restaurantApi
                .subscribeOn(backgroundThread)
                .observeOn(mainThread)
                .subscribe {
                    if (it?.isError!!) {
                        Log.d("Testing", "${it.error()}")
                    } else {
                        data.postValue(it.response()?.body()!!)
                        RestaurantCache.getInstance().put(latLng, it.response()?.body()!!)
                    }
                }
        return data
    }
}