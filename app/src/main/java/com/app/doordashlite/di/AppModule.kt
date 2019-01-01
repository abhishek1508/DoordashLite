package com.app.doordashlite.di

import dagger.Module

@Module(includes = [ViewModelModule::class, ApiModule::class, SharedPreferencesModule::class])
class AppModule {

}
