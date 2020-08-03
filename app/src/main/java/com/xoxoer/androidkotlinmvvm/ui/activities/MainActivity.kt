package com.xoxoer.androidkotlinmvvm.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import com.xoxoer.androidkotlinmvvm.R
import com.xoxoer.androidkotlinmvvm.base.BaseAppCompatActivity
import com.xoxoer.androidkotlinmvvm.databinding.ActivityMainBinding
import com.xoxoer.androidkotlinmvvm.ui.viewmodels.ExampleViewModel
import com.xoxoer.lifemarklibrary.Lifemark
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseAppCompatActivity() {

    @Inject
    private lateinit var lifemark: Lifemark

    @VisibleForTesting
    private val exampleViewModel by viewModels<ExampleViewModel>()

    private val binding: ActivityMainBinding by binding(R.layout.activity_main)

//    private fun retryWhenConnected() {
//        lifemark.ObservableNetworkCondition().observe(this@MainActivity,
//            Observer { isConnected ->
//                if (isConnected) {
//                    exampleViewModel.fetchExample()
//                }
//            })
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exampleViewModel.fetchExample()
//        retryWhenConnected()
        binding.apply {
            lifecycleOwner = this@MainActivity
            vm = exampleViewModel
        }
    }
}