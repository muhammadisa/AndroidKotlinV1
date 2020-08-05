package com.xoxoer.androidkotlinmvvm.utils

import com.xoxoer.androidkotlinmvvm.model.example.Example

object MockUtil {
    fun mockExample() = Example(
        id = 1,
        postId = 2,
        body = "example_body",
        email = "example@example.com",
        name = "example_name"
    )
}