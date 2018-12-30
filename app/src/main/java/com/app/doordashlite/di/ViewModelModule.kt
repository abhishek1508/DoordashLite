package com.app.doordashlite.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.multibindings.IntoMap
import dagger.Module
import com.app.doordashlite.factory.ViewModelFactory
import com.app.doordashlite.HomeViewModel
import com.app.doordashlite.restaurants.RestaurantViewModel
import com.app.doordashlite.restaurants.detail.RestaurantDetailViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantViewModel::class)
    abstract fun bindRestaurantViewModel(viewModel: RestaurantViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantDetailViewModel::class)
    abstract fun bindRestaurantDetailViewModel(viewModel: RestaurantDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(
            factory: ViewModelFactory): ViewModelProvider.Factory
}
