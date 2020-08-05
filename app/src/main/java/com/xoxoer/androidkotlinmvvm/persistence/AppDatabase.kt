package com.xoxoer.androidkotlinmvvm.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xoxoer.androidkotlinmvvm.model.example.Example

@Database(entities = [Example::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exampleDao(): ExampleDao
}