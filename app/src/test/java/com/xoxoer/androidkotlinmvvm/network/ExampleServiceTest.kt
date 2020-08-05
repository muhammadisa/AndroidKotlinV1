package com.xoxoer.androidkotlinmvvm.network

import com.nhaarman.mockitokotlin2.mock
import com.xoxoer.androidkotlinmvvm.RxTrampolineSchedulerRule
import com.xoxoer.androidkotlinmvvm.model.example.Example
import com.xoxoer.androidkotlinmvvm.network.services.ExampleClient
import com.xoxoer.androidkotlinmvvm.network.services.ExampleService
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class ExampleServiceTest : ApiAbstract<ExampleService>() {
    private lateinit var service: ExampleService
    private val client: ExampleClient = mock()

    @Rule
    @JvmField
    var testSchedulerRule = RxTrampolineSchedulerRule()

    @Before
    fun initService() {
        service = createService(ExampleService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun fetchExampleFromNetworkTest() = runBlocking {
        enqueueResponse("/Example.json")
        val response = service.fetchExample()
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .blockingGet()
        val responseBody = requireNotNull((response as Example))
        mockWebServer.takeRequest()
        client.fetchExample()
        assertThat(responseBody.id, `is`(1))
    }

}