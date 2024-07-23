package com.example.notes.ViewModels


import androidx.lifecycle.*
import androidx.lifecycle.asLiveData
import com.example.notes.Database.Note
import com.example.notes.Database.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val allNotes: LiveData<List<Note>> = repository.allNotes.asLiveData()

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun getNoteByTitle(title: String): LiveData<Note?> {
        val result = MutableLiveData<Note?>()
        viewModelScope.launch {
            result.value = repository.getNoteByTitle(title)
        }
        return result
    }
}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
