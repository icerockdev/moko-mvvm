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
                viewModel: LoginViewModel(
                    eventsDispatcher: EventsDispatcher()
                ).observed { vm in
                    vm.login.readOnly()
                    vm.password.readOnly()
                },
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

class LoginViewModelWrapper: ObservableObject {
    let wrapped: LoginViewModel
    
    let login: Binding<String>
    let password: Binding<String>
    var isButtonEnabled: Bool {
        get { wrapped.isLoginButtonEnabled.value!.boolValue }
    }
    var isLoading: Bool {
        get { wrapped.isLoading.value!.boolValue }
    }
    
    init(viewModel: LoginViewModel) {
        self.wrapped = viewModel
        
        self.login = createBinding(viewModel.login)
        self.password = createBinding(viewModel.password)
        
        createState(wrapped.isLoginButtonEnabled)
        createState(wrapped.isLoading)
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
        
//        var propertiesCount : CUnsignedInt = 0
//        let propertiesInAClass = class_copyPropertyList(Self.self, &propertiesCount)
//        var propertiesDictionary = [String:Any]()
//
//        for i in 0 ..< Int(propertiesCount) {
//          if let property = propertiesInAClass?[i],
//             let strKey = NSString(utf8String: property_getName(property)) as String? {
//              print(strKey)
//          }
//        }
//
//        var methodsCount : CUnsignedInt = 0
//        let methodsInAClass = class_copyMethodList(Self.self, &methodsCount)
//        var methodsDictionary = [String:Any]()
//
//        for i in 0 ..< Int(methodsCount) {
//          if let method = methodsInAClass?[i] {
//              print(method_getName(method))
//
//              print(String(cString: method_copyReturnType(method)))
//          }
//        }
//
//        let mirror = Mirror(reflecting: self)
//
//        let allLiveData = mirror
//            .children
//            .compactMap {
//                $0.value as? LiveData<AnyObject>
//            }
        
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
    @ObservedObject var viewModel: LoginViewModel
    let onLoginSuccess: () -> Void
    
    var body: some View {
        LoginViewBody(
            login: createBinding(viewModel.login),
            password: createBinding(viewModel.password),
            isButtonEnabled: viewModel.isLoginButtonEnabled.value?.boolValue ?? false,
            isLoading: viewModel.isLoading.value?.boolValue ?? false,
            onLoginPressed: { viewModel.onLoginPressed() }
        ).onReceive(viewModel.objectWillChange) { data in
            print("updated \(data)")
        }
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

//@propertyWrapper
//struct LiveDataBinding<Value, InternalValue: AnyObject> {
//    private var value: Value
//
//    init(liveData: LiveData<InternalValue>, mapper: @escaping (InternalValue) -> Value) {
//        self.value = mapper(liveData.value as! InternalValue)
//
//        liveData.addObserver { newValue in
//            print("LiveDataBinding - got new value \(newValue)")
//            if let value = newValue {
//                self.value = mapper(value)
//            }
//        }
//    }
//
//    var wrappedValue: Value {
//        get { value }
//    }
//}

//func createLiveDataBinding<Value, InternalValue: AnyObject>(
//    _ liveData: LiveData<InternalValue>,
//    mapper: @escaping (InternalValue) -> Value
//) -> LiveDataBinding<Value, InternalValue> {
//    return LiveDataBinding(liveData: liveData, mapper: mapper)
//}


// TODO think about https://kean.blog/post/rxui
func createBinding<T, R>(
    _ liveData: MutableLiveData<T>,
    getMapper: @escaping (T) -> R,
    setMapper: @escaping (R) -> T
) -> Binding<R> {
    var setEnabled = true
    var result = Binding(
        get: { getMapper(liveData.value!) },
        set: {
            if setEnabled {
                liveData.value = setMapper($0)
            }
        }
    )
    
    print("i create new binding!")
    
    liveData.addObserver { newValue in
        print("createBinding - got new value \(newValue)")
        setEnabled = false
        result.wrappedValue = getMapper(newValue!)
        setEnabled = true
//        result.update()
    }
    
    return result
}

extension ObservableObject where ObjectWillChangePublisher: Combine.ObservableObjectPublisher {
    
    func createState<T, R>(
        _ liveData: LiveData<T>,
        mapper: @escaping (T) -> R
    ) -> State<R> {
        var result: State<R> = State(initialValue: mapper(liveData.value!))
        result.update()
        
        print("i create new state!")
        
        liveData.addObserver { newValue in
            print("createState - got new value \(newValue)")
            result.wrappedValue = mapper(newValue!)
            self.objectWillChange.send()
        }
        
        return result
    }
    
    func createState(_ liveData: LiveData<KotlinBoolean>) -> State<Bool> {
        return createState(liveData, mapper: { $0.boolValue })
    }
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
