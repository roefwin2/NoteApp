package com.example.noteappkmm.android.di

import android.app.Application
import com.example.noteappkmm.data.local.DatabaseDriverFactory
import com.example.noteappkmm.data.note.SqlDelightNoteDataSourceImpl
import com.example.noteappkmm.domain.note.NoteDataSource
import com.rdika.noteappkmm.database.NoteDatabase
import com.rdika.noteappkmm.database.NoteDatabase.Companion
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNotaDataSource(driver : SqlDriver): NoteDataSource {
        return SqlDelightNoteDataSourceImpl(NoteDatabase.invoke(driver))
    }
}