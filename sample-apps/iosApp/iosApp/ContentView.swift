import SwiftUI
import shared

struct ContentView: View {
    @State var isOnMainScreen = false
    
    var body: some View {
        NavigationView {
            LoginView {
                isOnMainScreen = true
            }
            
            NavigationLink(
                "Main Screen",
                isActive: $isOnMainScreen,
                destination: {
                    MainView(onBackPressed: {
                        isOnMainScreen = false
                    })
                }
            )
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
