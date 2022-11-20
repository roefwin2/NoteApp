//
//  NoteItem.swift
//  iosApp
//
//  Created by Regis Dika on 19/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteItem: View {

    var note :Note
    var onDeleteClick : () -> Void
    
    var body: some View {
        VStack(alignment: .leading){
            HStack(){
                Text(note.title)
                    .font(.title3)
                    .fontWeight(.semibold)
                Spacer()
                Button(action: onDeleteClick){
                    Image(systemName: "xmark").foregroundColor(.black)
                }
            }.padding(.bottom,3)
            Text(note.content)
                .fontWeight(.light)
                .padding(.bottom,3)
            HStack{
                Spacer()
                Text(DateTimeUtil().formatNoteDate(dateTime : note.created))
                    .font(.footnote)
            }
        }
        .padding()// push a little bit in the middle
        .background(Color(hex: note.coloHex))
        .clipShape(RoundedRectangle(cornerRadius: 10.0))
    }
}

struct NoteItem_Previews: PreviewProvider {
    static var previews: some View {
        NoteItem(
            note: Note(id: nil, title: "Note 1", content: "Content 1",coloHex: 0xFF2341, created: DateTimeUtil().now()), onDeleteClick:{ }
        )
    }
}
