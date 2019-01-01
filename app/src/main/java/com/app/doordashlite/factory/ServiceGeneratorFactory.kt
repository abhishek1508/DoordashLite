package com.app.doordashlite.factory

import android.content.SharedPreferences
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject


class ServiceGeneratorFactory @Inject constructor(val retrofitBuilder: Retrofit.Builder,
                                                  val okHttpClient: OkHttpClient) {

    @Inject lateinit var sharedPref: SharedPreferences

    /**
     * The method adds the login interceptor and returns an implementation of the API endpoints
     * defined by the service interface
     */
    fun <S> createLoginService(serviceClass: Class<S>, loginDetails: String): S {
        /*if (loginDetails.isNotEmpty()) {
            val interceptor = LoginInterceptor(loginDetails)
            if (!okHttpClient.interceptors().contains(interceptor)) {
                retrofitBuilder.client(okHttpClient.newBuilder().addInterceptor(interceptor).build())
            }
        }*/
        return retrofitBuilder.build().create(serviceClass)
    }

    /**
     * The method adds the auth interceptor and returns an implementation of the API endpoints
     * defined by the service interface
     */
    fun <S> createService(serviceClass: Class<S>): S {
        /*val authToken: String = sharedPref.getString(AUTH_TOKEN, "")
        val refreshToken: String = sharedPref.getString(REFRESH_TOKEN, "")
        if (authToken.isNotEmpty()) {
            val interceptor = AuthenticationInterceptor(authToken)
            if (!okHttpClient.interceptors().contains(interceptor)) {
                retrofitBuilder.client(okHttpClient.newBuilder().addInterceptor(interceptor).build())
            }
        }*/
        return retrofitBuilder.build().create(serviceClass)
    }
}