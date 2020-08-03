package com.xoxoer.androidkotlinmvvm.binding

import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.xoxoer.androidkotlinmvvm.extensions.createDialog

@BindingAdapter("errorHandler", "errorReasonHandler")
fun bindError(
    view: View,
    error: ObservableField<Boolean>,
    errorReason: ObservableField<String>
) {
    when (error.get()) {
        true -> view
            .context.createDialog(
                "Alert",
                errorReason.get()!!
            ).show()

        false -> {
        }
    }
}

@BindingAdapter("toast")
fun bindToast(view: View, text: LiveData<String>) {
    if (!text.value.isNullOrEmpty()) {
        Toast.makeText(view.context, text.value, Toast.LENGTH_SHORT).show()
    }
}