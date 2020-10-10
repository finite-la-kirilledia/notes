package com.finite_la_kirilledia.notes.screens.notelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.finite_la_kirilledia.notes.database.Note
import com.finite_la_kirilledia.notes.database.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteListViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = NoteDatabase.getInstance(application).noteDao

    fun getAllNotes(): LiveData<List<Note>> {
        return noteDao.getAllNotes()
    }

    fun search(searchQuery: String): LiveData<List<Note>> {
        return noteDao.search(searchQuery)
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteAll()
        }
    }
}