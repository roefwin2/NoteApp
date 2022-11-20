//
//  NoteListViewModel.swift
//  iosApp
//
//  Created by Regis Dika on 18/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteListScreen{
   @MainActor class NoteListViewModel : ObservableObject{
       private var noteDataSource : NoteDataSource? = nil
       
       
       private let serachNotes = SearchNotesUseCase()
       
       private var notes = [Note]()
       @Published private (set) var filteredNotes = [Note]()
       
       @Published var searchText = "" {
           // execute after set value
           didSet{
               self.filteredNotes = SearchNotesUseCase().execute(notes: self.notes, query: searchText)
           }
       }
       
       @Published private(set)var isSearchActive = false
       
    
    init(noteDataSource: NoteDataSource? = nil) {
           self.noteDataSource = noteDataSource
       }
       
       func loadNotes(){
           noteDataSource?.getAllNotes(completionHandler: { notes, error in
               self.notes = notes ?? []
               self.filteredNotes = self.notes
           })
       }
       
       func deleteNotesById(id: Int64?){
           if id != nil {
               noteDataSource?.deleteNoteById(id: id!, completionHandler: { error in
                   self.loadNotes()
               })
           }
       }
       
       func toggleIsSearchActive(){
           isSearchActive = !isSearchActive
           if !isSearchActive {
               searchText = ""
           }
       }
       
       func setNoteDataSource(noteDataSource: NoteDataSource){
           self.noteDataSource = noteDataSource
           noteDataSource.insertNote(note: Note(id: nil, title: "Note 1", content: "Cotent 1", coloHex: 0xFF2355, created: DateTimeUtil().now()), completionHandler:{ error in})
       }
    }
}
