package com.finite_la_kirilledia.notes.screens.notelist

import android.text.format.DateUtils
import com.finite_la_kirilledia.notes.R
import com.finite_la_kirilledia.notes.database.Note
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.note_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class NoteItem(val note: Note) : Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        // description
        viewHolder.itemView.description.text = note.description

        // creation time
        when {
            // today
            DateUtils.isToday(note.creationTimeMillis) -> viewHolder.itemView.creationTime.text = SimpleDateFormat("HH:mm", Locale.ENGLISH).format(note.creationTimeMillis)

            // yesterday
            DateUtils.isToday(note.creationTimeMillis + DateUtils.DAY_IN_MILLIS) -> viewHolder.itemView.creationTime.text = "Yesterday"

            // default
            else -> viewHolder.itemView.creationTime.text = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(note.creationTimeMillis)
        }
    }

    override fun getLayout(): Int = R.layout.note_item
}