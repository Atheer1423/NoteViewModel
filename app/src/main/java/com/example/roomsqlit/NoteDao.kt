package com.example.roomsqlit

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {


    @Insert
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM NoteTable ORDER BY id ASC")
    fun getNotes(): LiveData<List<Note>>

    @Update
     fun updateNote(note: Note)

    @Delete
     fun deleteNote(note: Note)

}
