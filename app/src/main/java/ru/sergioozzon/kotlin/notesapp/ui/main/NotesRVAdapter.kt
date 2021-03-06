package ru.sergioozzon.kotlin.notesapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*
import ru.sergioozzon.kotlin.notesapp.R
import ru.sergioozzon.kotlin.notesapp.common.getColorRes
import ru.sergioozzon.kotlin.notesapp.data.entity.Note

class NotesRVAdapter(val onItemViewClick: ((Note) -> Unit)? = null) :
    RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(notes[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) = with(itemView) {
            //плохо для производительности, тк каждый раз при вызове bind будет вызываться findViewById
            with(note) {
                tv_text.text = body
                tv_title.text = title

            }

            val color = note.color.getColorRes()

            (this as CardView).setCardBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    color
                )
            )
            itemView.setOnClickListener { onItemViewClick?.invoke(note) }
        }
    }
}