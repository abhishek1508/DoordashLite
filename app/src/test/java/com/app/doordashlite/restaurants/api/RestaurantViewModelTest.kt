package com.app.doordashlite.restaurants.api

import com.app.doordashlite.restaurants.RestaurantViewModel
import com.app.doordashlite.restaurants.repo.RestaurantRepository
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Test
import org.mockito.Mockito.mock


class RestaurantViewModelTest {
    private val userRepository = mock(RestaurantRepository::class.java)
    private val userViewModel = RestaurantViewModel()

    @Test
    fun testRepositoryNonNull() {
        assertThat(userRepository, notNullValue())
    }

    @Test
    fun testViewModelNonNull() {
        assertThat(userViewModel, notNullValue())
    }
}