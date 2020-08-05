package com.xoxoer.androidkotlinmvvm.model.example


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "example")
data class Example(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("body")
    val body: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("postId")
    val postId: Int
)