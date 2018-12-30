package com.app.doordashlite.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.multibindings.IntoMap
import dagger.Module
import com.app.doordashlite.factory.ViewModelFactory
import com.app.doordashlite.HomeViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(
            factory: com.app.doordashlite.factory.ViewModelFactory): ViewModelProvider.Factory
}
