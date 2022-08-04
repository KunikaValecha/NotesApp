package com.example.notesapp02

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)

    @Query("SELECT * from note order by uid DESC")
    fun returnAllNotes(): LiveData<List<Note>>

    @Query("SELECT * from note where title like :query order by uid DESC ")
    suspend fun searchNotes(query: String): List<Note>
}