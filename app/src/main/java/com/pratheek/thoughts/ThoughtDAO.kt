package com.pratheek.thoughts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ThoughtDAO {

    @Query("select * from thought_table")
    fun getListOfThought(): LiveData<List<ThoughtEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertThought(vararg thought: ThoughtEntity)

    @Query("delete from thought_table")
    fun deleteAll()

    @Query("delete from thought_table where thought_content == :content")
    fun delete(content: String)
}