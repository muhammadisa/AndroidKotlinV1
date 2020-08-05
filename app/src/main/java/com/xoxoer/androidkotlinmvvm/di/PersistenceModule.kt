package com.xoxoer.androidkotlinmvvm.di

import android.app.Application
import androidx.room.Room
import com.xoxoer.androidkotlinmvvm.persistence.AppDatabase
import com.xoxoer.androidkotlinmvvm.persistence.ExampleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "template.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideExample(appDatabase: AppDatabase): ExampleDao {
        return appDatabase.exampleDao()
    }

}