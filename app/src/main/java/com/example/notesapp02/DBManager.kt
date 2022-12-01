package com.example.notesapp02

import android.content.Context
import androidx.room.Room

object DBManager {
    private var noteDB: NoteDataBase? = null
    fun getDBInstance(context: Context): NoteDataBase {
        if (noteDB == null) {
            noteDB = Room.databaseBuilder(
                context.applicationContext,
                NoteDataBase::class.java,
                "database-name"
            ).fallbackToDestructiveMigration()
                .build()
        }
        return noteDB!!
    }

    private var noteDao: NoteDao? = null
    fun getDaoInstance(context: Context): NoteDao {
        if (noteDao == null) {
            noteDao = getDBInstance(context)?.noteDao()
        }
        return noteDao!!
    }

}