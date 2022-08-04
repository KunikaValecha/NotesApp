package com.example.notesapp02

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity:AppCompatActivity() {
    private val viewModel by lazy{
        ViewModelProvider(this).get(NoteViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)
        val searchET = findViewById<AppCompatEditText>(R.id.searching)
        val rView = findViewById<RecyclerView>(R.id.searchRV)
        searchET.doAfterTextChanged {
            viewModel.search(it.toString())
        }
        rView.layoutManager = LinearLayoutManager(this)
        val adapter = SearchAdapter()
        rView.adapter = adapter
        viewModel.searchLiveData.observe(this){
            adapter.submitList(it)
        }
    }
}