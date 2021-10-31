package com.example.roomsqlit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteTable")
data class Note(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id : Int=0, // this is how can include id if needed
    @ColumnInfo(name = "note") val note: String
)
