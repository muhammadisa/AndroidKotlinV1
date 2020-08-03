package com.xoxoer.androidkotlinmvvm.annotations

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BasicOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BasicOkHttpAuthenticatedClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BasicRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BasicRetrofitAuthenticatedClient