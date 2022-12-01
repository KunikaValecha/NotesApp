package com.example.notesapp02

import android.app.Application
import androidx.lifecycle.*
import androidx.room.Room
import kotlinx.coroutines.launch

class NoteViewModel(private val mApp: Application) : AndroidViewModel(mApp) {

    private val noteDao = DBManager.getDaoInstance(mApp)

    val noteLiveData = noteDao.returnAllNotes()
    val searchQueryLiveData = MutableLiveData<String>()

    val searchLiveData = Transformations.switchMap(searchQueryLiveData) {
        noteDao.searchNotes("%$it%")
    }

    fun insert(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }

    fun edit(note: Note) {
        viewModelScope.launch {
            noteDao.edit(note.title, note.text, note.uid)
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note.uid)
        }
    }


    fun search(query: String) {
        viewModelScope.launch {
            searchQueryLiveData.postValue(query)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            noteDao.deleteAll()
        }
    }
}