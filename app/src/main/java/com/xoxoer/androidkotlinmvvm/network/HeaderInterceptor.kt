package com.xoxoer.androidkotlinmvvm.network

import android.content.Context
import com.orhanobut.hawk.Hawk
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor constructor(private val context: Context) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Hawk.init(context).build()
        val request: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${Hawk.get<String>("ACCESS_TOKEN")}")
            .build()
        return chain.proceed(request)
    }
}