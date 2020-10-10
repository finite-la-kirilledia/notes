package com.finite_la_kirilledia.notes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY creationTimeMillis DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE description LIKE '%' || :searchQuery || '%' ORDER BY creationTimeMillis DESC")
    fun search(searchQuery: String): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM notes")
    fun deleteAll()
}