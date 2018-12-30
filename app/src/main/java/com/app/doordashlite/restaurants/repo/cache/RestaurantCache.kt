package com.app.doordashlite.restaurants.repo.cache

import com.app.doordashlite.restaurants.repo.entity.Restaurant
import com.app.doordashlite.restaurants.repo.entity.local.LatLng
import java.util.concurrent.TimeUnit

const val DEFAULT_TIMEOUT_SECONDS: Long = 30
class RestaurantCache {
    private var cache: MutableMap<LatLng, Value> = mutableMapOf()

    companion object {
        private var INSTANCE: RestaurantCache? = null
        fun getInstance(): RestaurantCache {
            synchronized(this) {
                if(INSTANCE == null){
                    INSTANCE = RestaurantCache()
                }
                return INSTANCE!!
            }
        }
    }

    fun getTimeout(): Long {
        return DEFAULT_TIMEOUT_SECONDS
    }

    fun put(key: LatLng, value: List<Restaurant>) {
        if (!cache.containsKey(key)) {
            cache[key] = Value(value)
        }
    }

    fun get(key: LatLng): List<Restaurant>? {
        val data = cache[key]
        if (data != null) {
            return if (!data.isExpired()) {
                data.value
            } else {
                cache.remove(key)
                null
            }
        }
        return null
    }

    private class Value(val value: List<Restaurant>) {
        private val insertTime: Long = System.currentTimeMillis()

        fun isExpired(): Boolean {
            return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - insertTime) >= DEFAULT_TIMEOUT_SECONDS
        }
    }
}