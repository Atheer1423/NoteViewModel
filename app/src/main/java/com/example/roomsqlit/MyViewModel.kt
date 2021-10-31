package com.example.roomsqlit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(activity: Application): AndroidViewModel(activity) {
    var listNote: LiveData<List<Note>>
    val db = NoteDataBase.getInstance(activity)

    init {
        listNote = db.NoteDao().getNotes()
    }

    fun update(note: Note) {
        db.NoteDao().updateNote(note)
    }

    fun getNote(): LiveData<List<Note>> {
        return listNote
    }

    fun addNote(note: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.NoteDao().addNote(Note(0, note))
        }
    }

    fun removeNote(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            db.NoteDao().deleteNote(note)
        }
    }

}
