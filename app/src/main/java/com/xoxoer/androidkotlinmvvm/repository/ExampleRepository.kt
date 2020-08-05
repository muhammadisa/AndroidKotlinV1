package com.xoxoer.androidkotlinmvvm.repository

import com.xoxoer.androidkotlinmvvm.model.example.Example
import com.xoxoer.androidkotlinmvvm.network.services.ExampleClient
import com.xoxoer.androidkotlinmvvm.persistence.ExampleDao
import com.xoxoer.androidkotlinmvvm.utils.rx.ApiSingleObserver
import com.xoxoer.androidkotlinmvvm.utils.rx.Error
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ExampleRepository @Inject constructor(
    private val exampleClient: ExampleClient,
    private val exampleDao: ExampleDao
): Repository {

    private fun persistExample(data: Example) {
        exampleDao.insertExample(data)
    }

    fun fetchExample(
        onResult: (data: Example) -> Unit,
        onError: (e: Error) -> Unit
    ){
        exampleClient.fetchExample()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiSingleObserver<Example>(CompositeDisposable()) {
                override fun onResult(data: Example) {
                    onResult(data)
                    persistExample(data)
                }

                override fun onError(e: Error) {
                    onError(e)
                }
            })
    }

}