package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes.Database.NoteRoomDatabase
import com.example.notes.Database.NoteRepository
import com.example.notes.UIx.themes.NotesTheme
import com.example.notes.ViewModels.NoteViewModel
import com.example.notes.ViewModels.NoteViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class MainActivity : ComponentActivity() {
    private val activityScope: CoroutineScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize the database, repository, and ViewModel
        val database = NoteRoomDatabase.getDatabase(this, activityScope)
        val repository = NoteRepository(database.noteDao())
        val viewModelFactory = NoteViewModelFactory(repository)
        val noteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

        setContent {
            NotesTheme {
                Navigation(noteViewModel)
            }
        }
    }

    @Composable
    fun Navigation(noteViewModel: NoteViewModel) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { HomePageScreen(navController,noteViewModel) }
            composable("addNote") { AddNoteScreen(navController, noteViewModel) }
        }
    }
}
