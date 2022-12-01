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

    @Query("SELECT * from note where title like :query or text like :query order by uid DESC ")
    fun searchNotes(query: String): LiveData<List<Note>>

    @Query("UPDATE note SET title = :titleUpdated , text = :body WHERE uid = :id")
    suspend fun edit(titleUpdated : String, body:String, id: Int )

    @Query("DELETE FROM note WHERE uid = :id")
    suspend fun delete(id: Int )

    @Query("DELETE FROM note")
    suspend fun deleteAll()
}