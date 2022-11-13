package com.example.noteappkmm.domain

import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val coloHex: Long,
    val created: LocalDateTime
)
