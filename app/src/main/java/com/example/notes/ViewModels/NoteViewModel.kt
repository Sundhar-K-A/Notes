package com.example.notes.ViewModels


import androidx.lifecycle.*
import androidx.lifecycle.asLiveData
import com.example.notes.Database.Note
import com.example.notes.Database.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val allNotes: LiveData<List<Note>> = noteRepository.allNotes.asLiveData()
    val notes: Flow<List<Note>> = noteRepository.allNotes

    fun insert(note: Note) = viewModelScope.launch {
        noteRepository.insert(note)
    }

    fun getNoteByTitle(title: String): LiveData<Note?> {
        val result = MutableLiveData<Note?>()
        viewModelScope.launch {
            result.value = noteRepository.getNoteByTitle(title)
        }
        return result
    }
}

class NoteViewModelFactory(private val noteRepository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(noteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
