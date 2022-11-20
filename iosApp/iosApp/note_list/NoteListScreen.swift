//
//  NoteListScreen.swift
//  iosApp
//
//  Created by Regis Dika on 18/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteListScreen<Destination: View>: View {
    private var noteDataSource : NoteDataSource
    //to avoid recreate after recomposition
    @StateObject var viewModel = NoteListViewModel(noteDataSource: nil)
    
    @State private var isNoteSelected = false
    @State private var selectedNoteId: Int64? = nil

    init(noteDataSource: NoteDataSource) {
        self.noteDataSource = noteDataSource
    }
    
    var body: some View {
        VStack{
            ZStack{
                NavigationLink(destination: NoteDetailsScreen(noteDataSource:self.noteDataSource,noteId: self.selectedNoteId),isActive: $isNoteSelected){
                    EmptyView()
                }.hidden()
                HideableSearchTextField(onSearchToggle:{ viewModel.toggleIsSearchActive()}, destinationProvider:{EmptyView()}, isSearchActive:viewModel.isSearchActive, searchText: $viewModel.searchText)
                    .frame(maxWidth:.infinity,minHeight: 40)
                    .padding()
                
                if !viewModel.isSearchActive{
                    Text("All Notes")
                        .font(.title2)
                }
            }
            
            List{
                ForEach(viewModel.filteredNotes,id: \.self.id) { note in
                    Button(action : {
                        isNoteSelected = true
                        selectedNoteId = note.id?.int64Value
                    }){
                        NoteItem(note: note, onDeleteClick:{ viewModel.deleteNotesById(id: note.id?.int64Value)})
                    }
                }
            }.onAppear{
                viewModel.loadNotes()
            }.listStyle(.plain)
                .listRowSeparator(.hidden)
        }
        .onAppear{
            viewModel.setNoteDataSource(noteDataSource: self.noteDataSource)
        }
    }
}

struct NoteListScreen_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
    }
}
