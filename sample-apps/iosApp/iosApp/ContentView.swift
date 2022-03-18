import SwiftUI
import shared
import Combine

enum Screen {
    case main
    case second
}

enum Route {
    case toMain
    case toSecond(text: String)
    
    var destination: Screen {
        switch(self) {
        case .toMain:
            return Screen.main
        case .toSecond(_):
            return Screen.second
        }
    }
}

class Router {
    var activeRoute: State<Route?>
    let activeRouteBinding: Binding<Screen?>
    
    init() {
        let activeRoute: State<Route?> = State(initialValue: nil)
        
        self.activeRoute = activeRoute
        self.activeRouteBinding = Binding(
            get: { return activeRoute.wrappedValue?.destination },
            set: { newValue in
                if let newValue = newValue {
                    fatalError("i can't set \(newValue)")
                } else {
                    activeRoute.wrappedValue = nil
                }
            }
        )
    }
    
    func route(_ destination: Route?) {
        self.activeRoute.wrappedValue = destination
        self.activeRoute.update()
    }
}

func createRouteActiveBinding(route: Binding<Route?>) -> Binding<Bool> {
    return Binding(
        get: {
            route.wrappedValue != nil
        },
        set: { newValue in
            if !newValue {
                route.wrappedValue = nil
            }
        }
    )
}

struct NavigationDestination<Destination>: View where Destination: View {
    @State var isActive: Bool = false
    private let destination: () -> Destination
    
    init(@ViewBuilder destination: @escaping () -> Destination) {
        self.destination = destination
    }
    
    var body: some View {
        NavigationLink(
            isActive: $isActive,
            destination: self.destination,
            label: { EmptyView().hidden() }
        )
    }
}

struct ContentView: View {
    @State var route: Route? = nil
    
    var body: some View {
        NavigationView {
//            VStack {
//                Button("to main") {
//                    self.route = .toMain
//                }
//                Button("to second") {
//                    self.route = .toSecond(text: "from root")
//                }
//            }
//
//            switch(self.route) {
//            case .toMain:
//                NavigationLink(
//                    isActive: createRouteActiveBinding(route: $route),
//                    destination: {
//                        MainView(onBackPressed: {
//                            self.route = .toSecond(text: "hello!")
//                        })
//                    },
//                    label: { EmptyView().hidden() }
//                )
//            case .toSecond(let text):
//                NavigationLink(
//                    isActive: createRouteActiveBinding(route: $route),
//                    destination: {
//                        Text("got \(text)")
//                    },
//                    label: { EmptyView().hidden() }
//                )
//            case nil:
//                EmptyView().hidden()
//            }

            LoginView(
                onLoginSuccess: {  }
            )

//            NavigationLink(
//                "Main Screen",
//                isActive: $isOnMainScreen,
//                destination: {
//                    MainView(onBackPressed: {
//                        isOnMainScreen = false
//                    })
//
//                    NavigationLink(
//                        "235 Screen",
//                        isActive: $isOnMainScreen,
//                        destination: {
//                            MainView(onBackPressed: {
//                                isOnMainScreen = false
//                            })
//                        }
//                    )
//                }
//            )
//
//            NavigationLink(
//                "234 Screen",
//                isActive: $isOnMainScreen,
//                destination: {
//                    MainView(onBackPressed: {
//                        isOnMainScreen = false
//                    })
//                }
//            )
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

struct MainView: View {
    let onBackPressed: () -> Void
    
    var body: some View {
        Button("Back") {
            onBackPressed()
        }
    }
}
