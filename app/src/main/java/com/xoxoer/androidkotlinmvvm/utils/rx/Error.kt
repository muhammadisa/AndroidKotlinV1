package com.xoxoer.androidkotlinmvvm.utils.rx

class Error(
    var message: String = "",
    var errorCode: String = "",
    var throwable: Throwable? = null
)