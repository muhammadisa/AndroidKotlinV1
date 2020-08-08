package com.xoxoer.androidkotlinmvvm.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.xoxoer.androidkotlinmvvm.RxTrampolineSchedulerRule
import com.xoxoer.androidkotlinmvvm.model.example.Example
import com.xoxoer.androidkotlinmvvm.network.services.ExampleClient
import com.xoxoer.androidkotlinmvvm.network.services.ExampleService
import com.xoxoer.androidkotlinmvvm.persistence.ExampleDao
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

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
    fun setup(){
        client = ExampleClient(service)
        repository = ExampleRepository(client, exampleDao)
    }

    @Test
    fun fetchExampleFromNetworkTest() = runBlocking{
        val mockData = Example(
            1,
            "body_example",
            "email_example",
            "name_example",
            2
        )

        whenever(exampleDao.getExample(id_ = 0)).thenReturn(null)
        whenever(service.fetchExample()).thenReturn(Single.just(mockData))

        repository.fetchExample(
            onResult = {
                assertThat(it.id, `is`(1))
                assertThat(it.body, `is`("body_example"))
                assertThat(it.email, `is`("email_example"))
                assertThat(it.name, `is`("name_example"))
                assertThat(it.postId, `is`(2))
            },
            onError = {}
        )
    }

}