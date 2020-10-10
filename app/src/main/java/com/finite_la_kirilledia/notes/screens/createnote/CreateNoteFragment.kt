package com.finite_la_kirilledia.notes.screens.createnote

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.finite_la_kirilledia.notes.R
import com.finite_la_kirilledia.notes.database.Note
import com.finite_la_kirilledia.notes.hideKeyboard
import kotlinx.android.synthetic.main.fragment_create_note.*

class CreateNoteFragment : Fragment(R.layout.fragment_create_note) {

    private val viewModel: CreateNoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun createNote() {
        val note = Note(
            id = 0,
            description = description.text.toString(),
            creationTimeMillis = System.currentTimeMillis()
        )
        viewModel.insert(note)
        hideKeyboard()
        findNavController().navigate(CreateNoteFragmentDirections.actionCreateNoteFragmentToNoteListFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_create_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_done) {
            createNote()
        }
        return super.onOptionsItemSelected(item)
    }
}