package com.example.roomsqlit

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var edNote: EditText
    lateinit var btnsave: Button
    val myViewM by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }

    //    lateinit var Notelist: List<Note>
    private lateinit var rvNotes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myViewM.getNote().observe(this, { listNote ->
            updateRV(listNote)
        })
        rvNotes = findViewById(R.id.rv)
        edNote = findViewById(R.id.et)
        btnsave = findViewById(R.id.b)
//        Notelist = listOf()
        NoteDataBase.getInstance(applicationContext)
//        updateRV(NoteDataBase.getInstance(applicationContext).NoteDao().getNotes())
        btnsave.setOnClickListener {
            val s = edNote.text.toString()
            edNote.text.clear()
            myViewM.addNote(s)
//            CoroutineScope(IO).launch {
//                NoteDataBase.getInstance(applicationContext).NoteDao().addNote(s)
//                updateRV(NoteDataBase.getInstance(applicationContext).NoteDao().getNotes())
//            }
            Toast.makeText(applicationContext, "data saved successfully! ", Toast.LENGTH_SHORT)
                .show()
        }
//        CoroutineScope(IO).launch {
//            Notelist = NoteDataBase.getInstance(applicationContext).NoteDao().getNotes()
//            updateRV(Notelist)
//        }
    }

    fun updateRV(data: List<Note>) {
        CoroutineScope(IO).launch {
            withContext(Main) {
                rvNotes.adapter = adapteritem(this@MainActivity, data)
                rvNotes.layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    fun removeNote(note: Note) {
        myViewM.removeNote(note)
//        NoteDataBase.getInstance(applicationContext).NoteDao().deleteNote(note)
//        updateRV(NoteDataBase.getInstance(applicationContext).NoteDao().getNotes())
    }

    fun updateNote(note: Note) {
        myViewM.update(note)
//        NoteDataBase.getInstance(applicationContext).NoteDao().updateNote(note)
//        updateRV(NoteDataBase.getInstance(applicationContext).NoteDao().getNotes())
    }

    fun Alert(note: Note) {

        val dialogBuilder = AlertDialog.Builder(this)
        val input = EditText(this)
        dialogBuilder.setMessage("Enter note")
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                updateNote(Note(note.id, input.text.toString()))
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        val alert = dialogBuilder.create()

        alert.setTitle("Update your note")
        alert.setView(input)
        alert.show()

    }
}