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

    fun fetchExample(handler: ApiSingleObserver<Example>) {
        exampleClient.fetchExample()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                persistExample(
                    Example(
                        it.id,
                        "Cached ${it.body}",
                        it.email,
                        it.name,
                        it.postId
                    )
                )
            }
            .doOnError {
                exampleDao.getExample(id_ = 1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(handler)
            }
            .subscribe(handler)
    }

}