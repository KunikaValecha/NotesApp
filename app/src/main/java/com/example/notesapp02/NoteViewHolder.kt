package com.example.notesapp02

import android.view.TextureView
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteViewHolder(eachView : View):RecyclerView.ViewHolder(eachView) {
    fun onBind(note:Note){
        val title = itemView.findViewById<TextView>(R.id.NoteTitle)
        title.text = note.title
        val body = itemView.findViewById<TextView>(R.id.NoteCell)
        body.text = note.text
    }
}