package com.xoxoer.androidkotlinmvvm.di

import com.xoxoer.androidkotlinmvvm.annotations.BasicRetrofitClient
import com.xoxoer.androidkotlinmvvm.network.services.ExampleClient
import com.xoxoer.androidkotlinmvvm.network.services.ExampleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesExampleService(
        @BasicRetrofitClient retrofit: Retrofit
    ): ExampleService {
        return retrofit.create(ExampleService::class.java)
    }

    @Provides
    @Singleton
    fun providesExampleClient(exampleService: ExampleService): ExampleClient {
        return ExampleClient(exampleService)
    }

}