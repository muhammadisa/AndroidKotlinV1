package com.xoxoer.androidkotlinmvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.xoxoer.androidkotlinmvvm.RxTrampolineSchedulerRule
import com.xoxoer.androidkotlinmvvm.model.example.Example
import com.xoxoer.androidkotlinmvvm.network.services.ExampleClient
import com.xoxoer.androidkotlinmvvm.network.services.ExampleService
import com.xoxoer.androidkotlinmvvm.persistence.ExampleDao
import com.xoxoer.androidkotlinmvvm.repository.ExampleRepository
import com.xoxoer.androidkotlinmvvm.ui.viewmodels.ExampleViewModel
import com.xoxoer.androidkotlinmvvm.utils.MockUtil
import com.xoxoer.lifemarklibrary.Lifemark
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExampleViewModelTest {

    private lateinit var viewModel: ExampleViewModel
    private lateinit var exampleRepository: ExampleRepository
    private val exampleService: ExampleService = mock()
    private val exampleClient: ExampleClient = ExampleClient(exampleService)
    private val exampleDao: ExampleDao = mock()
    private val lifemark: Lifemark = mock()

    @Rule
    @JvmField
    var testSchedulerRule = RxTrampolineSchedulerRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        exampleRepository = ExampleRepository(exampleClient, exampleDao)
        viewModel = ExampleViewModel(exampleRepository, lifemark, SavedStateHandle())
    }

    @Test
    fun fetchExampleTest() = runBlocking {
        val mockData = MockUtil.mockExample()
        whenever(exampleDao.getExample(id_ = 1)).thenReturn(mockData).then {
            verify(exampleDao, atLeastOnce()).getExample(id_ = 1)
        }
        whenever(exampleDao.deleteExample()).then {
            verify(exampleDao, atLeastOnce()).deleteExample()
        }

        val observer: Observer<Example> = mock()
        val fetchedData = MutableLiveData<Example>()
        fetchedData.postValue(mockData)
        fetchedData.observeForever(observer)

        viewModel.fetchExample()
        delay(500L)

        verify(observer).onChanged(mockData)
        fetchedData.removeObserver(observer)
    }

}