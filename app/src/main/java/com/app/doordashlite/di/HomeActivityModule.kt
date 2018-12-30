package com.app.doordashlite.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.app.doordashlite.HomeActivity

@Module
abstract class HomeActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeHomeActivity(): HomeActivity
}
