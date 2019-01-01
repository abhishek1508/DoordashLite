package com.app.doordashlite.factory

import com.app.doordashlite.Constants.AUTHORIZATION_KEY
import okhttp3.Interceptor
import okhttp3.Response

class LoginInterceptor(private val usernamePassword: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        val original = chain?.request()
        val request = original?.newBuilder()?.addHeader(AUTHORIZATION_KEY, usernamePassword)?.build()
        return chain?.proceed(request!!)
    }
}