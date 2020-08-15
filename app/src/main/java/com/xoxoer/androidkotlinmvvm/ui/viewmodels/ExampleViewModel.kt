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

class ExampleViewModel @ViewModelInject constructor(
    private val exampleRepository: ExampleRepository,
    private val lifemark: Lifemark,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelContracts {

    override var valid = ObservableBoolean()
    override val isLoading = MutableLiveData<Boolean>()
    override val error = ObservableField<Boolean>()
    override val errorReason = ObservableField<String>()

    private val _exampleSuccess = MutableLiveData<Example>()
    val exampleSuccess: LiveData<Example>
        get() = _exampleSuccess

    private fun errorDispatcher(errorReason: String) {
        this.error.set(true)
        this.errorReason.set(errorReason)
        this._exampleSuccess.postValue(null)
        this.isLoading.postValue(false)
    }

    fun fetchExample() {
        isLoading.postValue(true)
        when (lifemark.isNetworkConnected()) {
            true -> exampleRepository.fetchExample(object :
                ApiSingleObserver<Example>(CompositeDisposable()) {
                override fun onResult(data: Example) {
                    _exampleSuccess.postValue(data)
                    isLoading.postValue(false)
                }

                override fun onError(e: Error) {
                    errorDispatcher(e.message)
                }
            })
            false -> exampleRepository.fetchExample(object :
                ApiSingleObserver<Example>(CompositeDisposable()) {
                override fun onResult(data: Example) {
                    _exampleSuccess.postValue(data)
                    isLoading.postValue(false)
                }

                override fun onError(e: Error) {
                    errorDispatcher("No Internet Connection")
                }
            })
        }
    }
}