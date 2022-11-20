import SwiftUI
import shared

@main
struct iOSApp: App {
    
    private let dataBaseModule = DatabaseModule()

    
	var body: some Scene {
        WindowGroup {
            NavigationView{
                NoteListScreen<EmptyView>(noteDataSource: dataBaseModule.noteDataSource)
            }
        }
	}
}
