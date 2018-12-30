package com.app.doordashlite.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class SchedulerModule {

    companion object {
        const val IO: String = "IO"
        const val MAIN: String = "MAIN"
        const val COMPUTATION: String = "COMPUTATION"
        const val TRAMPOLINE: String = "TRAMPOLINE"
    }

    @Singleton
    @Provides
    @Named(SchedulerModule.IO)
    fun provideIOExecutor(): Scheduler = Schedulers.io()

    @Singleton
    @Provides
    @Named(SchedulerModule.COMPUTATION)
    fun provideComputationExecutor(): Scheduler = Schedulers.computation()

    @Singleton
    @Provides
    @Named(SchedulerModule.TRAMPOLINE)
    fun provideTrampolineExecutor(): Scheduler = Schedulers.trampoline()

    @Singleton
    @Provides
    @Named(SchedulerModule.MAIN)
    fun provideMainExecutor(): Scheduler = AndroidSchedulers.mainThread()
}
