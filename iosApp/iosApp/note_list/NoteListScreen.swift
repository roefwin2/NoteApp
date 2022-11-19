//
//  NoteListScreen.swift
//  iosApp
//
//  Created by Regis Dika on 18/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteListScreen: View {
    private var noteDataSource : NoteDataSource? = nil
    var viewModel = NoteListViewModel(noteDataSource: nil)
    
    var body: some View {
        Text(/*@START_MENU_TOKEN@*/"Hello, World!"/*@END_MENU_TOKEN@*/)
    }
}

struct NoteListScreen_Previews: PreviewProvider {
    static var previews: some View {
        NoteListScreen()
    }
}
