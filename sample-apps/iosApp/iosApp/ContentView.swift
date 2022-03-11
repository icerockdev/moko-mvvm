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

@resultBuilder
struct LiveDataObserverBuilder {
    static func buildBlock() -> [LiveData<AnyObject>] { [] }
}

extension LiveDataObserverBuilder {
    static func buildBlock(_ settings: LiveData<AnyObject>...) -> [LiveData<AnyObject>] {
        settings
    }
}

extension ObservableObject where Self: ViewModel {
    
    func observed(
        @LiveDataObserverBuilder _ content: (Self) -> [LiveData<AnyObject>]
    ) -> Self {
        let allLiveData: [LiveData<AnyObject>] = content(self)

        for liveData in allLiveData {
            liveData.addObserver { _ in
                self.objectWillChange.send()
            }
        }
        
        return self
    }
}

extension ViewModel: ObservableObject {
    
}

struct LoginView: View {
    @ObservedObject var viewModel: LoginViewModel = LoginViewModel(
        eventsDispatcher: EventsDispatcher()
    ).observed { vm in
        vm.login.readOnly()
        vm.password.readOnly()
        vm.isLoading.distinct()
        vm.isLoginButtonEnabled.distinct()
    }
    let onLoginSuccess: () -> Void
    
    var body: some View {
        LoginViewBody(
            login: binding(viewModel.login),
            password: binding(viewModel.password),
            isButtonEnabled: state(viewModel.isLoginButtonEnabled),
            isLoading: state(viewModel.isLoading),
            onLoginPressed: { viewModel.onLoginPressed() }
        )
    }
}

struct LoginViewBody: View {
    @Binding var login: String
    @Binding var password: String
    let isButtonEnabled: Bool
    let isLoading: Bool
    let onLoginPressed: () -> Void
    
    var body: some View {
        VStack(spacing: 8.0) {
            TextField("Login", text: $login)
                .textFieldStyle(.roundedBorder)
            
            TextField("Password", text: $password)
                .textFieldStyle(.roundedBorder)
            
            if isLoading {
                ProgressView()
            } else {
                Button("Login") {
                    onLoginPressed()
                }.disabled(!isButtonEnabled)
            }
        }.padding()
    }
}

func binding<T, R>(
    _ liveData: MutableLiveData<T>,
    getMapper: @escaping (T) -> R,
    setMapper: @escaping (R) -> T
) -> Binding<R> {
    return Binding(
        get: { getMapper(liveData.value!) },
        set: { liveData.value = setMapper($0) }
    )
}

func binding(_ liveData: MutableLiveData<NSString>) -> Binding<String> {
    return binding(
        liveData,
        getMapper: { $0 as String },
        setMapper: { $0 as NSString }
    )
}

func state<T, R>(
    _ liveData: LiveData<T>,
    mapper: @escaping (T) -> R
) -> R {
    return mapper(liveData.value!)
}

func state(_ liveData: LiveData<KotlinBoolean>) -> Bool {
    return state(liveData, mapper: { $0.boolValue })
}
