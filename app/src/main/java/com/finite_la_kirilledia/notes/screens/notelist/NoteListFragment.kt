package com.finite_la_kirilledia.notes.screens.notelist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.finite_la_kirilledia.notes.R
import com.finite_la_kirilledia.notes.hideKeyboard
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_note_list.*

class NoteListFragment : Fragment(R.layout.fragment_note_list) {

    private val viewModel: NoteListViewModel by viewModels()

    private val adapter = GroupAdapter<ViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setupRecyclerView()
        setupCreateNoteFab()
    }

    private fun setupRecyclerView() {
        recyclerViewNoteList.layoutManager = LinearLayoutManager(activity)
        recyclerViewNoteList.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider, null))
        recyclerViewNoteList.addItemDecoration(dividerItemDecoration)

        adapter.setOnItemClickListener { item, _ ->
            findNavController().navigate(NoteListFragmentDirections.actionNoteListFragmentToEditNoteFragment((item as NoteItem).note))
        }

        viewModel.getAllNotes().observe(viewLifecycleOwner) {
            adapter.clear()
            adapter.addAll(it.map(::NoteItem))
        }
    }

    private fun setupCreateNoteFab() {
        createNoteFab.setOnClickListener {
            findNavController().navigate(NoteListFragmentDirections.actionNoteListFragmentToCreateNoteFragment())
        }
    }

    private fun deleteAllNotes() {
        viewModel.deleteAll()
        hideKeyboard()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_note_list, menu)

        // listen for search query
        val searchView = menu.findItem(R.id.menu_search).actionView as? SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchQuery: String?): Boolean {
                return onQueryTextChange(searchQuery)
            }

            override fun onQueryTextChange(searchQuery: String?): Boolean {
                if (searchQuery != null) {
                    viewModel.search(searchQuery).observe(viewLifecycleOwner) {
                        adapter.clear()
                        adapter.addAll(it.map(::NoteItem))
                    }
                }
                return true
            }
        })

        // listen for search item expand
        menu.findItem(R.id.menu_search).setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                requireActivity().invalidateOptionsMenu()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                requireActivity().invalidateOptionsMenu()
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) deleteAllNotes()
        return super.onOptionsItemSelected(item)
    }
}