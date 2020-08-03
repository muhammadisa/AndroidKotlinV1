package com.xoxoer.androidkotlinmvvm.network.services

import com.xoxoer.androidkotlinmvvm.model.example.Example
import io.reactivex.Single
import javax.inject.Inject

class ExampleClient @Inject constructor(
    private val exampleService: ExampleService
) {

    fun fetchExample(): Single<Example> {
        return exampleService.fetchExample()
    }

}