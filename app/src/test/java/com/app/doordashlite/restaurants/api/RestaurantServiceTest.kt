package com.app.doordashlite.restaurants.api

import com.app.doordashlite.restaurants.repo.entity.Restaurant
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.Result
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class RestaurantServiceTest {

    private lateinit var service: RestaurantService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val okHttpClient = OkHttpClient.Builder().build()
        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RestaurantService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun testRestaurantReturnListOfRestaurants() {
        val observer = TestObserver<Result<List<Restaurant>>>()
        val path = "/v2/restaurant/?lat=37.42274&lng=-122.139956&offset=0&limit=1"
        val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody(getJson("api-response/restaurant.json"))
        mockWebServer.enqueue(mockResponse)
        val restaurantObserver = service.getRestaurants(37.422740, -122.139956, 0, 1)
        restaurantObserver.subscribe(observer)
        observer.assertNoErrors()
        observer.assertValue { 1 == it.response()?.body()?.size!! }
        observer.awaitTerminalEvent(2, TimeUnit.SECONDS)
        val request = mockWebServer.takeRequest()
        assertEquals(path, request.path)
    }

    @Test
    fun testRestaurantReturnError() {
        val observer = TestObserver<Result<List<Restaurant>>>()
        val mockResponse = MockResponse().setResponseCode(500)
        mockWebServer.enqueue(mockResponse)
        val restaurantObserver = service.getRestaurants(37.422740, -122.139956, 0, 1)
        restaurantObserver.subscribe(observer)
        assertEquals("Server Error", observer.values()[0].response()?.raw()?.message())
        observer.awaitTerminalEvent(2, TimeUnit.SECONDS)
    }

    private fun getJson(path : String) : String {
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}