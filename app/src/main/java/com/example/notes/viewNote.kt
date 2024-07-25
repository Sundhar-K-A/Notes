package com.example.notes

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notes.Database.Note
import com.example.notes.ViewModels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun viewNoteScreen(navController: NavController,noteViewModel: NoteViewModel,modifier: Modifier = Modifier) {
    val selectedNote by noteViewModel.selectedNote.observeAsState()
    Log.d("Data from the viewNote","${selectedNote}")

    var title by remember { mutableStateOf(selectedNote?.title ?: "") }
    var textState by remember { mutableStateOf(TextFieldValue(
        text = selectedNote?.body ?:""
    )) }
    var annotatedText by remember { mutableStateOf(AnnotatedString("")) }
    var isBold by remember { mutableStateOf(false) }
    var isItalic by remember { mutableStateOf(false) }

    fun updateAnnotatedText() {
        annotatedText = AnnotatedString.Builder().apply {
            append(textState.text)
            val currentStyle = SpanStyle(
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
                fontStyle = if (isItalic) FontStyle.Italic else FontStyle.Normal
            )
            addStyle(currentStyle, 0, textState.text.length)
        }.toAnnotatedString()
    }

    DisposableEffect(Unit) {
        onDispose {
            val updatedNote = selectedNote?.copy(
                title = title,
                body = textState.text
            )
            if (title.isNotEmpty() && textState.text.isNotEmpty())
                updatedNote?.let { noteViewModel.update(it) }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 20.dp, 10.dp, 0.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back to Home",
                tint = Color.White,
                modifier = Modifier
                    .clickable { navController.navigate("home") }
                    .padding(8.dp)
            )
        }
        TextField(
            value = title,
            onValueChange = { title = if (it.length <= 20) it else title },
            placeholder = { Text(text = "Title", fontSize = 40.sp, color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .height(100.dp),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 36.sp),
        )
        TextField(
            value = textState,
            onValueChange = { textState = it; updateAnnotatedText() },
            modifier = Modifier
                .fillMaxSize(),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
        )
    }
}