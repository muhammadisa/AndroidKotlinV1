package com.xoxoer.androidkotlinmvvm.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xoxoer.androidkotlinmvvm.model.example.Example

@Dao
interface ExampleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExample(example: Example)

    @Query("SELECT * FROM example WHERE id = :id_")
    fun getExample(id_: Int): Example?

    @Query("DELETE FROM example")
    fun deleteExample()

}