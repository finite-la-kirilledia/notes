package com.finite_la_kirilledia.notes.screens.editnote

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.finite_la_kirilledia.notes.R
import com.finite_la_kirilledia.notes.database.Note
import com.finite_la_kirilledia.notes.hideKeyboard
import kotlinx.android.synthetic.main.fragment_edit_note.*

class EditNoteFragment : Fragment(R.layout.fragment_edit_note) {

    private val viewModel: EditNoteViewModel by viewModels()

    private val args: EditNoteFragmentArgs by navArgs()

    private lateinit var selectedNote: Note

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        
        selectedNote = args.selectedNote
        description.setText(selectedNote.description)
    }

    private fun updateNote(note: Note) {
        note.description = description.text.toString()
        viewModel.insert(note)
        hideKeyboard()
    }

    private fun deleteNote(note: Note) {
        AlertDialog.Builder(requireContext())
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.delete(note)
                hideKeyboard()
                findNavController().navigate(EditNoteFragmentDirections.actionEditNoteFragmentToNoteListFragment())
            }
            .setNegativeButton("No") { _, _ -> }
            .create()
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_done -> updateNote(selectedNote)
            R.id.menu_delete -> deleteNote(selectedNote)
        }
        return super.onOptionsItemSelected(item)
    }
}