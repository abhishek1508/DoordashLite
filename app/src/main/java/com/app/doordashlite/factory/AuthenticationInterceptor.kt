package com.app.doordashlite.factory

import com.app.doordashlite.Constants.AUTHORIZATION_KEY
import com.app.doordashlite.Constants.AUTHORIZATION_VAL_BEARER_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(private val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        val original = chain?.request()
        val builder = StringBuilder(AUTHORIZATION_VAL_BEARER_TOKEN)
        builder.append(token)
        val request = original?.newBuilder()?.addHeader(AUTHORIZATION_KEY, builder.toString())?.build()
        return chain?.proceed(request!!)
    }
}