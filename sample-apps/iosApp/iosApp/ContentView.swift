import SwiftUI
import shared
import Combine

struct ContentView: View {
    @State var isAuthorized: Bool = false
    
    var body: some View {
        if isAuthorized {
            MainView {
                withAnimation(.linear) {
                    isAuthorized = false
                }
            }
        } else {
            LoginView {
                withAnimation(.easeIn) {
                    isAuthorized = true
                }
            }
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

struct MainView: View {
    let onUnauthorized: () -> Void
    
    var body: some View {
        NavigationView {
            BooksView()
                .toolbar {
                    ToolbarItem(placement: .navigationBarTrailing) {
                        Button("Logout") {
                            self.onUnauthorized()
                        }
                    }
                }
        }
    }
}

struct BooksView: View {
    let items: Array<String> = ["test", "test2", "test3", "test4", "test5"]
    
    var body: some View {
        List(items, id: \.hashValue) { text in
            NavigationLink {
                BookDetailsView(id: text)
            } label: {
                Text(text)
            }
        }.navigationTitle("Books")
    }
}

struct BookDetailsView: View {
    let id: String
    
    var body: some View {
        ScrollView {
            VStack {
                Text("id: ")
                Text(id)
            }
        }.navigationTitle("Book details")
    }
}
