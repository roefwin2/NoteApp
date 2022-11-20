package com.example.noteappkmm.di

import com.example.noteappkmm.data.local.DatabaseDriverFactory
import com.example.noteappkmm.data.note.SqlDelightNoteDataSourceImpl
import com.rdika.noteappkmm.database.NoteDatabase

class DatabaseModule {

    private val factory by lazy { DatabaseDriverFactory() }
    val noteDataSource by lazy {
        SqlDelightNoteDataSourceImpl(NoteDatabase(factory.createDriver()))
    }
}