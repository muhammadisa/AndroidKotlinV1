package com.xoxoer.androidkotlinmvvm.repository

import com.xoxoer.androidkotlinmvvm.model.example.Example
import com.xoxoer.androidkotlinmvvm.network.services.ExampleClient
import com.xoxoer.androidkotlinmvvm.persistence.ExampleDao
import com.xoxoer.androidkotlinmvvm.utils.rx.ApiSingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ExampleRepository @Inject constructor(
    private val exampleClient: ExampleClient,
    private val exampleDao: ExampleDao
) : Repository {

    private fun persistExample(data: Example) {
        exampleDao.insertExample(data)
    }

    @Suppress("UnstableApiUsage")
    fun fetchExample(
        onStart: () -> Unit,
        onFinish: () -> Unit,
        handler: ApiSingleObserver<Example>
    ) {
        exampleClient.fetchExample()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart() }
            .doOnTerminate { onFinish() }
            .doOnSuccess { persistExample(it) }
            .doOnError {
                exampleDao.getExample(id_ = 1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { onStart() }
                    .doOnTerminate { onFinish() }
                    .subscribe(handler)
            }
            .subscribe(handler)
    }

}