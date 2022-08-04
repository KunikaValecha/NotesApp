package com.example.notesapp02

import android.icu.text.CaseMap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val uid : Int = 0,
    val text : String,
    val title: String
    )
