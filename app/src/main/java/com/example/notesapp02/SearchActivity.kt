package com.example.notesapp02

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {

    private val viewModel by lazy {
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
        findViewById<AppCompatImageView>(R.id.ivBack).setOnClickListener{
            onBackPressed()
        }
        rView.layoutManager = LinearLayoutManager(this)
        rView.addItemDecoration(GridSpacingItemDecoration(1, 10.dp, true))
        val adapter = NoteAdapter(
            onClick =
            {
                val fragment = NoteDetailed()
                val bundle = Bundle()
                val title = it.title
                val body = it.text
                val id = it.uid
                bundle.putString("TITLE", title)
                bundle.putString("BODY", body)
                bundle.putInt("ID", id)
                fragment.arguments = bundle
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.detailedFragmentSearch, fragment, "CUSTOM")
                    .addToBackStack("CUSTOM")
                    .commit()
            },
            onDelete = {note, view->}
        )
        rView.adapter = adapter
        viewModel.searchLiveData.observe(this) {
            adapter.submitList(it)
        }
    }
}