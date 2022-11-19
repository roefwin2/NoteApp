//
//  HideableSearchTextField.swift
//  iosApp
//
//  Created by Regis Dika on 19/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct HideableSearchTextField<Destination:View>: View {
    
    //parameter
    var onSearchToggle: () -> Void
    var destinationProvider : ()-> Destination
    var isSearchActive: Bool
    @Binding var searchText: String
    
    var body: some View {
        HStack{
            TextField("Search...",text:$searchText)
                .textFieldStyle(.roundedBorder)
                .opacity(isSearchActive ? 1 : 0)
            if !isSearchActive{
                Spacer()
            }
            Button(action:onSearchToggle){
                Image(systemName: isSearchActive ? "xmark" : "magnifyingglass")
            }
            NavigationLink(destination: destinationProvider()){
                Image(systemName: "plus")
            }
        }
    }
}

struct HideableSearchTextField_Previews: PreviewProvider {
    static var previews: some View {
        HideableSearchTextField(
            onSearchToggle: {},
            destinationProvider:{EmptyView()},
            isSearchActive: true,
            searchText:.constant("Hello Régis"))
    }
}
