//
//  NoteDetailsViewModel.swift
//  iosApp
//
//  Created by Regis Dika on 20/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteDetailsScreen{
    @MainActor class NoteDetailsViewModel : ObservableObject{
        private var noteDataSource : NoteDataSource? = nil
        private var noteId: Int64?
        
        @Published var noteTitle : String = ""
        
        @Published var noteContent : String = ""
        
        @Published private (set) var noteColor : Int64 = Note.companion.generateRandomColor()
        
        init(noteDataSource: NoteDataSource? = nil, noteId: Int64? = nil) {
            self.noteDataSource = noteDataSource
            self.noteId = noteId
        }
        
        private func loadNote(noteId : Int64?){
            self.noteId = noteId
            if self.noteId != nil {
                noteDataSource?.getNoteById(id: self.noteId!, completionHandler: { note, error in
                    self.noteTitle = note?.title ?? ""
                    self.noteContent = note?.content ?? ""
                    self.noteColor = note?.coloHex ?? Note.companion.generateRandomColor()
                })
            }
        }
        
        func saveNote(onSaved : @escaping () -> Void){
            let newNote = Note(id: self.noteId == nil ? nil : KotlinLong(value: self.noteId!), title: noteTitle, content: noteContent, coloHex: self.noteColor, created: DateTimeUtil().now())
            noteDataSource?.insertNote(note: newNote, completionHandler: { error in
            })
        }
        
        func setParams(noteDatabase :NoteDataSource,noteId: Int64?){
            self.noteDataSource = noteDatabase
            loadNote(noteId: noteId)
        }
    }
}
