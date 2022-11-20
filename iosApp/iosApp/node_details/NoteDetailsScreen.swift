//
//  NoteDetailsScreen.swift
//  iosApp
//
//  Created by Regis Dika on 19/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteDetailsScreen: View {
    private var noteDataSource : NoteDataSource
    private var noteId :Int64?
    
    @Environment(\.presentationMode) var  presentation
    
    @StateObject private var viewModel = NoteDetailsViewModel(noteDataSource: nil)
    
    init(noteDataSource: NoteDataSource, noteId: Int64?) {
        self.noteDataSource = noteDataSource
        self.noteId = noteId
    }
    
    var body: some View{
        VStack(alignment:.leading){
            TextField("Enter a title", text: $viewModel.noteTitle).padding()
            TextField("Enter a content",text: $viewModel.noteContent).padding()
            Spacer()
        }.onAppear{
            viewModel.setParams(noteDatabase: noteDataSource, noteId: noteId)
        }.background(Color(hex: viewModel.noteColor))
            .toolbar(content: {
                Button(action: {
                    viewModel.saveNote {
                        presentation.wrappedValue.dismiss()
                    }
                }){
                    Image(systemName: "checkmark")
                }
            })
    }
    
    
    struct NoteDetailsScreen_Previews: PreviewProvider {
        static var previews: some View {
            EmptyView()
        }
    }
}
