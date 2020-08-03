package com.xoxoer.androidkotlinmvvm.extensions

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

fun Context.createDialog(title: String, message: String): AlertDialog {
    val builder = AlertDialog.Builder(this)
    return builder.also {
        it.setTitle(title)
        it.setMessage(message)
        it.setCancelable(true)
    }.create()
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}