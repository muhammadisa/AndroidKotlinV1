package com.xoxoer.androidkotlinmvvm.persistence

import com.xoxoer.androidkotlinmvvm.utils.MockUtil.mockExample
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23])
class ExampleDaoTest : LocalDatabase() {

    private lateinit var exampleDao: ExampleDao

    @Before
    fun init() {
        exampleDao = db.exampleDao()
    }

    @Test
    fun insertAndLoadExample() = runBlocking {
        val mockExampleList = mockExample()
        exampleDao.insertExample(mockExample())
        val loadFromDB = exampleDao.getExample(1)!!
        assertThat(loadFromDB.toString(), `is`(mockExampleList.toString()))
        assertThat(loadFromDB.id, `is`(1))
    }

}