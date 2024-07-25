package com.example.notes.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note_table WHERE id=:id")
    suspend fun getNoteByID(id:Int):Note?

//    @Query("DELETE FROM note_table")
//    suspend fun deleteAllNotes()

    @Query("SELECT * FROM note_table")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note_table WHERE title = :title LIMIT 1")
    suspend fun getNoteByTitle(title: String): Note?
}
