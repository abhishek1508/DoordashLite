package com.app.doordashlite.factory

import android.app.Application
import android.arch.lifecycle.LiveData
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.support.annotation.VisibleForTesting

@VisibleForTesting
class ConnectivityFactory constructor(private val connectivityManager: ConnectivityManager):
        LiveData<Boolean>() {

    constructor(application: Application) : this(application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network?) {
            postValue(true)
        }

        override fun onLost(network: Network?) {
            postValue(false)
        }
    }

    override fun onActive() {
        super.onActive()

        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnectedOrConnecting == true)

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}