package com.app.doordashlite.factory

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.CacheControl
import java.util.concurrent.TimeUnit


class CachingInterceptor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        val cacheControl = CacheControl.Builder().maxAge(2, TimeUnit.MINUTES).build()
        val original = chain?.request()
        val request = original
                ?.newBuilder()
                ?.addHeader("Cache-Control", cacheControl.toString())
                ?.build()
        return chain?.proceed(request!!)
    }
}