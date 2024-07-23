package com.example.notes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun HomePageScreen(navController: NavController, modifier: Modifier = Modifier) {
    var note by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf(listOf<String>()) }
    var defaultNotes = listOf("Note 1", "Note 2", "Note 3","Note 1", "Note 2", "Note 3","Note 1", "Note 2", "Note 3","Note 1", "Note 2", "Note 3","Note 1", "Note 2", "Note 3",)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(12.dp, 25.dp, 12.dp, 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NoteFieldWithSave(
                value = "",
                onValueChange = {},
                onSave = { note ->
                    if (note.isNotBlank()) {
                        notes += note
                    }
                }
            )
        }
        Spacer(modifier = Modifier.padding(7.dp))
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(defaultNotes) { currentNote ->
                    NoteItemBox(currentNote)
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
fun NoteFieldWithSave(
    value: String,
    onValueChange: (String) -> Unit,
    onSave: (String) -> Unit
){
    var note by remember { mutableStateOf(value) }

    OutlinedTextField(
        value = note,
        onValueChange = {
            note = it
            onValueChange(it)
        },
        label = { Text("Add a note") },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { onSave(note) }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Save")
            }
        }
    )
}

fun onSearch(queryNote: String){
    // TODO: complete the search functionality
}

@Composable
fun NoteItemBox(Note:String,modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(0.dp, 5.dp, 0.dp, 5.dp)
        .border(1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
    ){
        Text(text = Note,modifier = Modifier
            .padding(16.dp)
            .align(Alignment.Center))
    }
}