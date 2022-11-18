package com.example.noteappkmm.android.ui.note_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun NoteDetailsScreen(
    noteId: Long,
    navController: NavController,
    viewModel: NoteDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val hasNotBeenSaved by viewModel.hasBeenSaved.collectAsState()

    LaunchedEffect(key1 = hasNotBeenSaved) {
        if (hasNotBeenSaved) {
            navController.popBackStack()
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.saveNote()
        }, backgroundColor = Color.Black) {
            Icon(imageVector = Icons.Default.Check, contentDescription = "save note", tint = Color.White)
        }
    }) { padding ->
        Column(
            modifier = Modifier
                .background(Color(state.noteColor))
                .fillMaxSize()
                .padding(16.dp),
        ) {
            TransparentHiltTextField(
                text = state.noteTitle,
                hint = "Enter a title...",
                isHintVisible = state.isNoteTitleHintVisible,
                onValueChange = {
                    viewModel.onNoteTitleChanged(it)
                },
                onFocusChanged = {
                    viewModel.onNoteTitleFocusChanged(it.isFocused)
                }, singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TransparentHiltTextField(
                text = state.noteContent,
                hint = "Enter a content...",
                isHintVisible = state.isNoteContentHintVisible,
                onValueChange = {
                    viewModel.onNoteContentChanged(it)
                },
                onFocusChanged = {
                    viewModel.onNoteContentFocusChanged(it.isFocused)
                }, singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.weight(1f)  // occupy tje remaining space

            )
        }
    }
}