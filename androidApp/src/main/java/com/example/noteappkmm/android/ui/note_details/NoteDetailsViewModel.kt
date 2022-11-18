package com.example.noteappkmm.android.ui.note_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappkmm.domain.note.Note
import com.example.noteappkmm.domain.note.NoteDataSource
import com.example.noteappkmm.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val noteTitleState = savedStateHandle.getStateFlow("noteTitle", "")
    private val isNoteTitleFocused = savedStateHandle.getStateFlow("isNoteTitleFocused", false)
    private val noteContent = savedStateHandle.getStateFlow("noteContent", "")
    private val isNoteContentFocused = savedStateHandle.getStateFlow("isNoteContentFocused", false)
    private val noteColor = savedStateHandle.getStateFlow("noteColor", Note.generateRandomColor())

    val state = combine(
        noteTitleState,
        isNoteTitleFocused,
        noteContent,
        isNoteContentFocused,
        noteColor
    ) { title, isTitleFocused, content, isContentFocused, color ->
        NoteDetailsState(
            title, title.isEmpty() && !isTitleFocused, content, content.isEmpty() && !isContentFocused, color,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailsState())

    private val _hasNotBeenSaved = MutableStateFlow(false)
    val hasBeenSaved = _hasNotBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { existingId ->
            //compose not detect nullable
            if (existingId == -1L) {
                return@let
            }
            existingNoteId = existingId
            viewModelScope.launch {
                noteDataSource.getNoteById(existingId)?.let {
                    savedStateHandle["noteTitle"] = it.title
                    savedStateHandle["noteContent"] = it.content
                    savedStateHandle["noteColor"] = it.coloHex
                }
            }
        }
    }

    fun onNoteTitleChanged(title: String) {
        savedStateHandle["noteTitle"] = title
    }

    fun onNoteContentChanged(content: String) {
        savedStateHandle["noteContent"] = content
    }

    fun onNoteTitleFocusChanged(isFocus: Boolean) {
        savedStateHandle["isNoteTitleFocused"] = isFocus
    }

    fun onNoteContentFocusChanged(isFocus: Boolean) {
        savedStateHandle["isNoteContentFocused"] = isFocus
    }

    fun saveNote() {
        val note = state.value
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    existingNoteId,
                    note.noteTitle,
                    note.noteContent,
                    note.noteColor,
                    DateTimeUtil.now()
                )
            )
            _hasNotBeenSaved.value = true
        }
    }

}