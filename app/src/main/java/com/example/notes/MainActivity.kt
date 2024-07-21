package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.unit.dp
import com.example.notes.ui.theme.NotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesTheme {
                var note by remember { mutableStateOf("") }
                var notes by remember { mutableStateOf(listOf<String>()) }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .absolutePadding(16.dp,25.dp,16.dp,16.dp),
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
                    LazyColumn {
                        items(notes){ currentNote ->
                            Text(text = currentNote)
                        }
                    }
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
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Save")
                }
            }
        )
    }
}

