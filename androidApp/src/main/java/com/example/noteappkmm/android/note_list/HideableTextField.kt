package com.example.noteappkmm.android.note_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun HideableSearchTextField(
    text: String,
    isSearchActive: Boolean,
    onTextChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    Box(modifier = modifier) {
        AnimatedVisibility(visible = isSearchActive, enter = fadeIn(), exit = fadeOut()) {
            OutlinedTextField(
                value = text, onValueChange = onTextChange, shape = RoundedCornerShape(50.dp), placeholder = {
                    Text(
                        text = "Search"
                    )
                }, modifier = androidx.compose.ui.Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(end = 40.dp)
            )
        }
        AnimatedVisibility(
            visible = isSearchActive, enter = fadeIn(), exit = fadeOut(), modifier = androidx.compose.ui.Modifier.align(
                Alignment.CenterEnd
            )
        ) {
            IconButton(onClick = onCloseClick) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close search")
            }
        }
        AnimatedVisibility(
            visible = isSearchActive, enter = fadeIn(), exit = fadeOut(), modifier = androidx.compose.ui.Modifier.align(
                Alignment.CenterEnd
            )
        ) {
            IconButton(onClick = onSearchClick) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
    }
}