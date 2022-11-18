package com.example.noteappkmm.android.ui.note_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    //just laucnh at the init no in the recomposition vm init block no reload after change screen and go back
    LaunchedEffect(key1 = true) {
        viewModel.loadNotes()
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate("note_details/-1L") }, backgroundColor = Color.Black) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add note", tint = Companion.White)
        }
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                HideableSearchTextField(
                    text = state.searchText,
                    isSearchActive = state.isSearchActive,
                    onTextChange = {
                        viewModel.onSearchTextChange(it)
                    },
                    onSearchClick = { viewModel.onToggleSearch() },
                    onCloseClick = {
                        viewModel.onToggleSearch()
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                )
                this@Column.AnimatedVisibility(visible = !state.isSearchActive, enter = fadeIn(), exit = fadeOut()) {
                    Text(text = "All notes", fontWeight = FontWeight.Bold, fontSize = 30.sp)
                }
            }
            //occupy the remaining space
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(state.notes, key = {
                    it.id!!
                }) { note ->
                    NoteItem(
                        note = note,
                        backgroundColor = Color(note.coloHex),
                        onNoteClick = {
                            navController.navigate("note_details/${note.id}")
                        },
                        onDeleteClick = { viewModel.deleteById(note.id!!) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                            .animateItemPlacement()
                    )
                }
            }
        }
    }
}