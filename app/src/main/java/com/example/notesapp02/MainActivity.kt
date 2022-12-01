package com.example.notesapp02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.PopupWindow
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(NoteViewModel::class.java)
    }

    private var currentSpanCount = Constants.SPAN_COUNT


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
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(currentSpanCount, 10.dp, true))
        findViewById<AppCompatImageView>(R.id.option).setOnClickListener {
            val popupWindowLayout = LayoutInflater.from(this).inflate(R.layout.options_popup, null)
            popupWindowLayout.findViewById<AppCompatTextView>(R.id.tvPin).text =
                getString(R.string.list_view)
            val popupWindow = PopupWindow(
                popupWindowLayout, 120.dp,
                100.dp, true
            )
            popupWindow.elevation = "20".toFloat()
            popupWindow.showAsDropDown(findViewById(R.id.option), -17, -17, Gravity.END)
            if (currentSpanCount == 2) {
                popupWindowLayout.findViewById<AppCompatTextView>(R.id.tvPin).text =
                    getString(R.string.list_view)
            } else {
                popupWindowLayout.findViewById<AppCompatTextView>(R.id.tvPin).text =
                    getString(R.string.grid_view)
            }
            popupWindowLayout.findViewById<AppCompatTextView>(R.id.tvPin).setOnClickListener {
                updateSpanCount()
                popupWindow.dismiss()
            }
            popupWindowLayout.findViewById<AppCompatTextView>(R.id.tvDelete).text = "Delete All"
            popupWindowLayout.findViewById<AppCompatTextView>(R.id.tvDelete).setOnClickListener {
                viewModel.deleteAll()
                popupWindow.dismiss()
            }

        }
        val adapter = NoteAdapter(onClick = {
            edit(it)
        },
            onDelete = { note , view ->
                val popupWindowLayout =
                    LayoutInflater.from(this).inflate(R.layout.options_popup, null)
                val popupWindow = PopupWindow(
                    popupWindowLayout, 120.dp,
                    100.dp, true
                )
                popupWindow.elevation = "20".toFloat()
                popupWindow.showAsDropDown(view, 20, -200, Gravity.END)
                popupWindowLayout.findViewById<AppCompatTextView>(R.id.tvDelete)
                    .setOnClickListener {
                        if (note.text.isNullOrBlank().not() && note.title.isNullOrBlank().not()) {
                            viewModel.delete(note)
                            popupWindow.dismiss()
                        }
                    }
                popupWindowLayout.findViewById<AppCompatTextView>(R.id.tvPin).text = "Edit"
                popupWindowLayout.findViewById<AppCompatTextView>(R.id.tvPin).setOnClickListener {
                    edit(note)
                    popupWindow.dismiss()
                }


            })
        recyclerView.adapter = adapter

        viewModel.noteLiveData.observe(this) {
            adapter.submitList(it)
        }
    }

    fun showDialog() {

        val newFragment = DialogFAB()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.detailedFragment, newFragment, "CUSTOM")
            .addToBackStack("CUSTOM")
            .commit()
    }

    private fun updateSpanCount() {
        if (currentSpanCount == 2) {
            currentSpanCount = 1

        } else {
            currentSpanCount = 2


        }
        findViewById<RecyclerView>(R.id.NotesRV).post {
            TransitionManager.beginDelayedTransition(findViewById<RecyclerView>(R.id.NotesRV))
            findViewById<RecyclerView>(R.id.NotesRV).layoutManager =
                GridLayoutManager(this, currentSpanCount)
        }
    }

    private fun edit(note: Note) {

        val fragment = NoteDetailed()
        val bundle = Bundle()
        val title = note.title
        val body = note.text
        val id = note.uid
        bundle.putString("TITLE", title)
        bundle.putString("BODY", body)
        bundle.putInt("ID", id)
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.detailedFragment, fragment, "CUSTOM")
            .addToBackStack("CUSTOM")
            .commit()
    }
}