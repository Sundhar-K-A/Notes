package com.example.notes.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    @ColumnInfo(name = "title")
    val Title: String,
    @ColumnInfo(name = "body")
    val body: String,
)