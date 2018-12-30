package com.app.doordashlite.factory

import android.content.SharedPreferences
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject


class ServiceGeneratorFactory @Inject constructor(val retrofitBuilder: Retrofit.Builder,
                                                  val okHttpClient: OkHttpClient) {

    @Inject lateinit var sharedPref: SharedPreferences

    fun <S> createLoginService(serviceClass: Class<S>, loginDetails: String): S {
        // Needed if the app requires Login.
        /*if (loginDetails.isNotEmpty()) {
            val interceptor = LoginInterceptor(loginDetails)
            if (!okHttpClient.interceptors().contains(interceptor)) {
                retrofitBuilder.client(okHttpClient.newBuilder().addInterceptor(interceptor).build())
            }
        }*/
        return retrofitBuilder.build().create(serviceClass)
    }

    fun <S> createService(serviceClass: Class<S>): S {
        // Needed if the API needs authentication
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