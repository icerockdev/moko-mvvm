import SwiftUI
import shared

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
            VStack {
                Button("to main") {
                    self.route = .toMain
                }
                Button("to second") {
                    self.route = .toSecond(text: "from root")
                }
            }
            
            switch(self.route) {
            case .toMain:
                NavigationLink(
                    isActive: createRouteActiveBinding(route: $route),
                    destination: {
                        MainView(onBackPressed: {
                            self.route = .toSecond(text: "hello!")
                        })
                    },
                    label: { EmptyView().hidden() }
                )
            case .toSecond(let text):
                NavigationLink(
                    isActive: createRouteActiveBinding(route: $route),
                    destination: {
                        Text("got \(text)")
                    },
                    label: { EmptyView().hidden() }
                )
            case nil:
                EmptyView().hidden()
            }
            
//
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

struct LoginView: View {
    let viewModel: LoginViewModel
    let onLoginSuccess: () -> Void
    
    init(
        viewModel: LoginViewModel = LoginViewModel(eventsDispatcher: EventsDispatcher()),
        onLoginSuccess: @escaping () -> Void
    ) {
        self.viewModel = viewModel
        self.onLoginSuccess = onLoginSuccess
        
        let any: Any? = viewModel.password2.value
        let string: NSString? = viewModel.password2S.value
    }
    
    var body: some View {
        LoginViewBody(
            login: createBinding(viewModel.login),
            password: createBinding(viewModel.password),
            isButtonEnabled: createState(viewModel.isLoginButtonEnabled),
            isLoading: createState(viewModel.isLoading),
            onLoginPressed: { viewModel.onLoginPressed() }
        )
    }
}

struct LoginViewBody: View {
    let login: Binding<String>
    let password: Binding<String>
    let isButtonEnabled: State<Bool>
    let isLoading: State<Bool>
    let onLoginPressed: () -> Void
    
    var body: some View {
        VStack(spacing: 8.0) {
            TextField("Login", text: login)
                .textFieldStyle(.roundedBorder)
            
            TextField("Password", text: password)
                .textFieldStyle(.roundedBorder)
            
            if isLoading.wrappedValue {
                ProgressView()
            } else {
                Button("Login") {
                    onLoginPressed()
                }.disabled(!isButtonEnabled.wrappedValue)
            }
        }.padding()
    }
}

// TODO think about https://kean.blog/post/rxui
func createBinding<T, R>(
    _ liveData: MutableLiveData<T>,
    getMapper: @escaping (T) -> R,
    setMapper: @escaping (R) -> T
) -> Binding<R> {
    var result = Binding(
        get: { getMapper(liveData.value!) },
        set: {
            liveData.value = setMapper($0)
        }
    )
    
    liveData.addObserver { _ in
        result.update()
    }
    
    return result
}

func createState<T, R>(
    _ liveData: LiveData<T>,
    mapper: (T) -> R
) -> State<R> {
    var result = State(
        wrappedValue: mapper(liveData.value!)
    )
    
    liveData.addObserver { _ in
        result.update()
    }
    
    return result
}

func createState(_ liveData: LiveData<KotlinBoolean>) -> State<Bool> {
    return createState(liveData, mapper: { $0.boolValue })
}

func createBinding(_ liveData: MutableLiveData<NSString>) -> Binding<String> {
    return createBinding(
        liveData,
        getMapper: { $0 as String },
        setMapper: { $0 as NSString }
    )
}

@propertyWrapper
public struct LiveDataPublished<T: AnyObject, Value> {
    let liveData: MutableLiveData<T>
    let getMapper: (T) -> Value
    let setMapper: (Value) -> T
    
    public var wrappedValue: Value {
        set { liveData.value = setMapper(newValue) }
        get { getMapper(liveData.value!) }
    }
}
