package com.xoxoer.androidkotlinmvvm.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.xoxoer.androidkotlinmvvm.RxTrampolineSchedulerRule
import com.xoxoer.androidkotlinmvvm.model.example.Example
import com.xoxoer.androidkotlinmvvm.network.services.ExampleClient
import com.xoxoer.androidkotlinmvvm.network.services.ExampleService
import com.xoxoer.androidkotlinmvvm.persistence.ExampleDao
import com.xoxoer.androidkotlinmvvm.utils.MockUtil
import com.xoxoer.androidkotlinmvvm.utils.rx.ApiSingleObserver
import com.xoxoer.androidkotlinmvvm.utils.rx.Error
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExampleRepositoryTest {

    private lateinit var repository: ExampleRepository
    private lateinit var client: ExampleClient
    private val service: ExampleService = mock()
    private val exampleDao: ExampleDao = mock()

    @Rule
    @JvmField
    var testSchedulerRule = RxTrampolineSchedulerRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        client = ExampleClient(service)
        repository = ExampleRepository(client, exampleDao)
    }

    @Test
    fun fetchExampleFromNetworkTest() = runBlocking {
        whenever(exampleDao.getExample(id_ = 0)).thenReturn(null)
        whenever(service.fetchExample()).thenReturn(Single.just(MockUtil.mockExample()))

        repository.fetchExample(
            object : ApiSingleObserver<Example>(CompositeDisposable()) {
                override fun onResult(data: Example) {
                    assertThat(data.id, `is`(1))
                    assertThat(data.body, `is`("example_body"))
                    assertThat(data.email, `is`("example@example.com"))
                    assertThat(data.name, `is`("example_name"))
                    assertThat(data.postId, `is`(2))
                }

                override fun onError(e: Error) {}
            })
    }

}