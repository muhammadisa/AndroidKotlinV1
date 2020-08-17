package com.xoxoer.androidkotlinmvvm.ui.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.xoxoer.androidkotlinmvvm.model.example.Example
import com.xoxoer.androidkotlinmvvm.repository.ExampleRepository
import com.xoxoer.androidkotlinmvvm.utils.rx.ApiSingleObserver
import com.xoxoer.androidkotlinmvvm.utils.rx.Error
import com.xoxoer.lifemarklibrary.Lifemark
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

class ExampleViewModel @ViewModelInject constructor(
    private val exampleRepository: ExampleRepository,
    private val lifemark: Lifemark,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelContracts {

    override var valid = ObservableBoolean()
    override val isLoading = MutableLiveData(false)
    override val error = ObservableField<Boolean>()
    override val errorReason = ObservableField<String>()

    private val _exampleSuccess = MutableLiveData<Example>()
    val exampleSuccess: LiveData<Example>
        get() = _exampleSuccess

    private fun <T> errorDispatcher(errorReason: String, targetMutable: MutableLiveData<T>) {
        this.error.set(true)
        this.errorReason.set(errorReason)
        this._exampleSuccess.postValue(null)
    }

    private fun onStart() {
        isLoading.value = true
        Timber.e("LOADING ON START ${isLoading.value}")
    }

    private fun onFinish() {
        isLoading.value = false
        Timber.e("LOADING ON FINISH ${isLoading.value}")
    }

    private fun <T> handler(targetMutable: MutableLiveData<T>) =
        object : ApiSingleObserver<T>(CompositeDisposable()) {
            override fun onResult(data: T) {
                targetMutable.value = data
            }

            override fun onError(e: Error) {
                when (lifemark.isNetworkConnected()) {
                    true -> errorDispatcher(e.message, targetMutable)
                    false -> errorDispatcher("No Internet Connection", targetMutable)
                }
            }
        }

    fun fetchExample() {
        exampleRepository.fetchExample(
            { onStart() },
            { onFinish() },
            handler(_exampleSuccess)
        )
    }
}