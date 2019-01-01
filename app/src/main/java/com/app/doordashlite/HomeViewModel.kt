package com.app.doordashlite

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.app.doordashlite.factory.ConnectivityFactory
import javax.inject.Inject

class HomeViewModel @Inject constructor(val app: Application) : AndroidViewModel(app) {

    private var isNetworkConnected = true
    val connectivityManager: ConnectivityFactory = ConnectivityFactory(app)

    fun setNetworkConnected(isNetworkConnected: Boolean) {
        this.isNetworkConnected = isNetworkConnected
    }

    fun getNetworkConnected(): Boolean {
        return isNetworkConnected
    }
}
