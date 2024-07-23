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
    suspend fun getNoteByTitle(title: String): Note? {
        return noteDao.getNoteByTitle(title)
    }
//    fun searchNotesByTitle(searchString: String): Flow<List<Note>> {
//        return noteDao.getNoteByTitle("%$searchString%")
//    }

}