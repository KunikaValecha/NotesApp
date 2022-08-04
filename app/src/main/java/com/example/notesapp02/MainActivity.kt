package com.example.notesapp02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy{
        ViewModelProvider(this).get(NoteViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.NotesRV)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val searchBar = findViewById<AppCompatImageView>(R.id.search_bar)

        searchBar.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        fab.setOnClickListener {
            showDialog()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NoteAdapter()
        recyclerView.adapter = adapter

        viewModel.noteLiveData.observe(this){
            adapter.submitList(it)
        }
    }
    fun showDialog() {
        val fragmentManager = supportFragmentManager
        val newFragment = DialogFAB()
        newFragment.show(fragmentManager, "dialog")
    }
}