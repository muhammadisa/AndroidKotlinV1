package com.xoxoer.androidkotlinmvvm.network.services

import com.xoxoer.androidkotlinmvvm.model.example.Example
import io.reactivex.Single
import retrofit2.http.GET

interface ExampleService{

    @GET("comments/1")
    fun fetchExample(): Single<Example>

}