package com.app.doordashlite.restaurants.cache

import com.app.doordashlite.restaurants.repo.cache.RestaurantCache
import com.app.doordashlite.restaurants.repo.entity.Restaurant
import com.app.doordashlite.restaurants.repo.entity.local.LatLng
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RestaurantCacheTest {

    private val restaurantList = mutableListOf<Restaurant>()

    @Before
    fun setUp() {
        restaurantList.add(Restaurant(30, "Hobee's", "Breakfast place",
                                      "https://hobees.cover.image", "22 mins",
                                      "open", 1.0))
    }

    @Test
    fun testCacheAfterAdding() {
        RestaurantCache.getInstance().put(LatLng(37.12345, -122.54321), restaurantList)
        val restaurant = RestaurantCache.getInstance().get(LatLng(37.12345, -122.54321))
        assertEquals(1, restaurant?.size)
        assertEquals("Hobee's", restaurant?.get(0)?.name)
    }

    @Test
    fun testCacheFetchWrongLatLng() {
        RestaurantCache.getInstance().put(LatLng(37.12345, -122.54321), restaurantList)
        val restaurant = RestaurantCache.getInstance().get(LatLng(37.54321, -122.12345))
        assertEquals(null, restaurant)
    }

    @Test
    fun testCacheTimeoutExpired() {
        RestaurantCache.getInstance().put(LatLng(37.12345, -122.54321), restaurantList)
        val restaurant = RestaurantCache.getInstance().get(LatLng(37.12345, -122.54321))
        assertEquals(1, restaurant?.size)
        assertEquals("Hobee's", restaurant?.get(0)?.name)
        Thread.sleep(30000)
        val restaurantAfterTimeout = RestaurantCache.getInstance().get(LatLng(37.12345, -122.54321))
        assertEquals(null, restaurantAfterTimeout)
    }

    @Test
    fun testCacheTimeoutNotExpired() {
        RestaurantCache.getInstance().put(LatLng(37.12345, -122.54321), restaurantList)
        val restaurant = RestaurantCache.getInstance().get(LatLng(37.12345, -122.54321))
        assertEquals(1, restaurant?.size)
        assertEquals("Hobee's", restaurant?.get(0)?.name)
        Thread.sleep(2000)
        val restaurantAfterTimeout = RestaurantCache.getInstance().get(LatLng(37.12345, -122.54321))
        assertEquals(1, restaurantAfterTimeout?.size)
        assertEquals("Hobee's", restaurantAfterTimeout?.get(0)?.name)
    }
}