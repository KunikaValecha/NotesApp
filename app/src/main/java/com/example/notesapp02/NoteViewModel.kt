package com.example.notesapp02

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.launch

class NoteViewModel(private val mApp: Application) : AndroidViewModel(mApp) {
    private val noteDataBase by lazy {
        Room.databaseBuilder(
            mApp.applicationContext,
            NoteDataBase::class.java,
            "database-name"
        ).build()
    }
    private val noteDao by lazy {
        noteDataBase.noteDao()
    }

    val noteLiveData = noteDao.returnAllNotes()
    val searchLiveData = MutableLiveData<List<Note>>()

    fun insert(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            val listOfSearch = noteDao.searchNotes("%$query%")
            searchLiveData.postValue(listOfSearch)
        }
    }
}