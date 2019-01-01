package com.app.doordashlite.restaurants.factory

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.app.doordashlite.factory.ConnectivityFactory
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class ConnectivityFactoryTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var activeNetwork: NetworkInfo
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private lateinit var connectionLiveData: ConnectivityFactory

    private inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

    private inline fun <reified T> argumentCaptor(): ArgumentCaptor<T> = ArgumentCaptor.forClass(T::class.java)

    @Before
    fun before() {
        connectivityManager = mock(ConnectivityManager::class.java)
        activeNetwork = mock(NetworkInfo::class.java)
        `when`(connectivityManager.activeNetworkInfo).thenReturn(activeNetwork)
        doNothing().`when`(connectivityManager).unregisterNetworkCallback(any<ConnectivityManager.NetworkCallback>())
        connectionLiveData = ConnectivityFactory(connectivityManager)
    }

    @Test
    fun alreadyConnectedReceiveTrue() {
        `when`(activeNetwork.isConnectedOrConnecting).thenReturn(true)
        val observer = mock<Observer<Boolean>>()
        connectionLiveData.observeForever(observer)
        verify(observer).onChanged(true)
    }

    @Test
    fun alreadyNotConnectedReceiveFalse() {
        `when`(activeNetwork.isConnectedOrConnecting).thenReturn(false)
        val observer = mock<Observer<Boolean>>()
        connectionLiveData.observeForever(observer)
        verify(observer).onChanged(false)
    }

    @Test
    fun alreadyConnectedBecomeDisconnectedReceiveFalse() {
        `when`(activeNetwork.isConnectedOrConnecting).thenReturn(true)

        val observer = mock<Observer<Boolean>>()
        connectionLiveData.observeForever(observer)

        verify(observer).onChanged(true)
        reset(observer)

        captureNetworkCallback()

        networkCallback.onLost(mock())

        verify(observer).onChanged(false)
    }

    @Test
    fun alreadyDisconnectedBecomeConnectedReceiveTrue() {
        `when`(activeNetwork.isConnectedOrConnecting).thenReturn(false)

        val observer = mock<Observer<Boolean>>()
        connectionLiveData.observeForever(observer)

        verify(observer).onChanged(false)
        reset(observer)

        captureNetworkCallback()

        networkCallback.onAvailable(mock())

        verify(observer).onChanged(true)
    }

    private fun captureNetworkCallback() {
        val captor = argumentCaptor<ConnectivityManager.NetworkCallback>()
        verify(connectivityManager).registerDefaultNetworkCallback(captor.capture())
        networkCallback = captor.value
    }
}