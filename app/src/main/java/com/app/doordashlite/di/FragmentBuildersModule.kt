package com.app.doordashlite.di

import com.app.doordashlite.restaurants.RestaurantFragment
import com.app.doordashlite.restaurants.detail.RestaurantDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeRestaurant(): RestaurantFragment

    @ContributesAndroidInjector
    abstract fun contributeRestaurantDetail(): RestaurantDetailFragment
}
