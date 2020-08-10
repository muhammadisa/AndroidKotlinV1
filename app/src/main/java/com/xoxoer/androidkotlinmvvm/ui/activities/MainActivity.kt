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

    @VisibleForTesting
    val exampleViewModel by viewModels<ExampleViewModel>()

    private val binding: ActivityMainBinding by binding(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exampleViewModel.fetchExample()

        binding.apply {
            lifecycleOwner = this@MainActivity
            vm = exampleViewModel
            buttonRefresh.setOnClickListener {
                exampleViewModel.fetchExample()
            }
        }
    }

}