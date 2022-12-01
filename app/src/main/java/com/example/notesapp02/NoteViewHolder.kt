package com.example.notesapp02

import android.view.TextureView
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

class NoteViewHolder(
    eachView: View,
    private val onClick: (note: Note) -> Unit,
    private val onDelete: (note: Note, view:View) -> Unit
) : RecyclerView.ViewHolder(eachView) {
    fun onBind(note: Note) {
        val title = itemView.findViewById<AppCompatTextView>(R.id.NoteTitle)
        title.text = note.title
        val body = itemView.findViewById<AppCompatTextView>(R.id.NoteCell)
        body.text = note.text
        val date = itemView.findViewById<AppCompatTextView>(R.id.dateAndTime)
        date.text = note.date
        itemView.setOnClickListener {
            onClick.invoke(note)
            it.context.hideKeyboard(it)
        }
        itemView.setOnLongClickListener {
            onDelete.invoke(note, it)
            it.context.hideKeyboard(it)
            true
        }
    }
}