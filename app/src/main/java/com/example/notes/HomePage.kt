package com.example.notes

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notes.Database.Note
import com.example.notes.ViewModels.NoteViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HomePageScreen(navController: NavController, noteViewModel: NoteViewModel, modifier: Modifier = Modifier) {
    //Todo add search
    var searchQuery by remember { mutableStateOf("") }

    val notesHomePage by noteViewModel.allNotes.observeAsState(initial = emptyList())

    Log.d("NotesFromDB", "$notesHomePage")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(12.dp, 25.dp, 12.dp, 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NoteFieldWithSearch(
                value = "",
                onValueChange = {},
                onSearch = { },
            )
        }
        Spacer(modifier = Modifier.padding(7.dp))
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(notesHomePage.asReversed()) { currentNote ->
                    NoteItemBox(currentNote,noteViewModel,navController)
                }}

            Button(
                onClick = { navController.navigate("addNote") },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .alpha(1f)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}
@Composable
fun NoteFieldWithSearch(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit
){
    var searchString by remember { mutableStateOf(value) }
    fun onSearch(queryNote: String){
        Log.d("Search string", "$queryNote")
    }
    OutlinedTextField(
        value = searchString,
        onValueChange = {
            searchString = it
            onValueChange(it)
        },
        label = { Text("Enter the search text") },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { onSearch(searchString) }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
    )
}



@Composable
fun NoteItemBox(note:Note,noteViewModel: NoteViewModel,navController: NavController,modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(0.dp, 5.dp, 0.dp, 5.dp)
        .border(1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
        .height(100.dp)
        .clickable {
            noteViewModel.selectedNote.value = note
            navController.navigate("viewNote")
        }
    ){
        Column(modifier= Modifier.fillMaxSize()){
            Text(
                text = note.title, modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp, 5.dp, 5.dp, 5.dp),
                fontSize = 24.sp,
                
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = if (note.body.length <= 20) note.body else note.body.slice(0..40) + "...",
                modifier = Modifier
                    .padding(5.dp, 5.dp, 0.dp, 0.dp)
            )
        }
    }
}
