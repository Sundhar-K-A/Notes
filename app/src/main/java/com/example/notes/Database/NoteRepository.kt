package com.example.notes.Database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }
    suspend fun getNotesByTitle(title: String): List<Note> {
        return noteDao.getNotesByTitle(title)
    }
    suspend fun getNoteByID(id:Int):Note?{
        return noteDao.getNoteByID(id)
    }
    suspend fun update(note: Note) {
        noteDao.update(note)
    }

//    fun searchNotesByTitle(searchString: String): Flow<List<Note>> {
//        return noteDao.getNoteByTitle("%$searchString%")
//    }

}