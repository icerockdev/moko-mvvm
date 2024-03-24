![moko-mvvm](img/logo.png)  
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0) [![Download](https://img.shields.io/maven-central/v/dev.icerock.moko/mvvm-core) ](https://repo1.maven.org/maven2/dev/icerock/moko/mvvm-core) ![kotlin-version](https://kotlin-version.aws.icerock.dev/kotlin-version?group=dev.icerock.moko&name=mvvm-core)
![badge][badge-android]
![badge][badge-ios]
![badge][badge-mac]
![badge][badge-watchos]
![badge][badge-tvos]
![badge][badge-jvm]
![badge][badge-js]
![badge][badge-windows]
![badge][badge-linux]

# Mobile Kotlin Model-View-ViewModel architecture components
This is a Kotlin Multiplatform library that provides architecture components of Model-View-ViewModel
 for UI applications. Components are lifecycle-aware on Android.  

## Table of Contents
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Documentation](#documentation)
- [Usage](#usage)
- [Samples](#samples)
- [Set Up Locally](#set-up-locally)
- [Contributing](#contributing)
- [License](#license)

## Features
- **ViewModel** - store and manage UI-related data. Interop with `Android Architecture Components` - on Android it's precisely `androidx.lifecycle.ViewModel`;
- **LiveData, MutableLiveData, MediatorLiveData** - lifecycle-aware reactive data holders with set of operators to transform, merge, etc.;
- **EventsDispatcher** - dispatch events from `ViewModel` to `View` with automatic lifecycle control and explicit interface of required events;
- **DataBinding, ViewBinding, Jetpack Compose, SwiftUI support** - integrate to Android & iOS app with commonly used tools;
- **All Kotlin targets support** - `core`, `flow` and `livedata` modules support all Kotlin targets.

## Requirements
- Gradle version 6.8+
- Android API 16+
- iOS version 11.0+

## Installation
root build.gradle  
```groovy
allprojects {
    repositories {
        mavenCentral()
    }
}
```

project build.gradle
```groovy
dependencies {
    commonMainApi("dev.icerock.moko:mvvm-core:0.16.1") // only ViewModel, EventsDispatcher, Dispatchers.UI
    commonMainApi("dev.icerock.moko:mvvm-flow:0.16.1") // api mvvm-core, CFlow for native and binding extensions
    commonMainApi("dev.icerock.moko:mvvm-livedata:0.16.1") // api mvvm-core, LiveData and extensions
    commonMainApi("dev.icerock.moko:mvvm-state:0.16.1") // api mvvm-livedata, ResourceState class and extensions
    commonMainApi("dev.icerock.moko:mvvm-livedata-resources:0.16.1") // api mvvm-core, moko-resources, extensions for LiveData with moko-resources
    commonMainApi("dev.icerock.moko:mvvm-flow-resources:0.16.1") // api mvvm-core, moko-resources, extensions for Flow with moko-resources
    
    // compose multiplatform
    commonMainApi("dev.icerock.moko:mvvm-compose:0.16.1") // api mvvm-core, getViewModel for Compose Multiplatform
    commonMainApi("dev.icerock.moko:mvvm-flow-compose:0.16.1") // api mvvm-flow, binding extensions for Compose Multiplatform
    commonMainApi("dev.icerock.moko:mvvm-livedata-compose:0.16.1") // api mvvm-livedata, binding extensions for Compose Multiplatform

    androidMainApi("dev.icerock.moko:mvvm-livedata-material:0.16.1") // api mvvm-livedata, Material library android extensions
    androidMainApi("dev.icerock.moko:mvvm-livedata-glide:0.16.1") // api mvvm-livedata, Glide library android extensions
    androidMainApi("dev.icerock.moko:mvvm-livedata-swiperefresh:0.16.1") // api mvvm-livedata, SwipeRefreshLayout library android extensions
    androidMainApi("dev.icerock.moko:mvvm-databinding:0.16.1") // api mvvm-livedata, DataBinding support for Android
    androidMainApi("dev.icerock.moko:mvvm-viewbinding:0.16.1") // api mvvm-livedata, ViewBinding support for Android
    
    commonTestImplementation("dev.icerock.moko:mvvm-test:0.16.1") // test utilities
}
```

Also required export of dependency to iOS framework. For example:
```
kotlin {
    // export correct artifact to use all classes of library directly from Swift
    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export("dev.icerock.moko:mvvm-core:0.16.1")
            export("dev.icerock.moko:mvvm-livedata:0.16.1")
            export("dev.icerock.moko:mvvm-livedata-resources:0.16.1")
            export("dev.icerock.moko:mvvm-state:0.16.1")
        }
    }
}
```

### KSwift

For iOS we recommend use [moko-kswift](https://github.com/icerockdev/moko-kswift) with extensions  
generation enabled. All `LiveData` to `UIView` bindings is extensions for UI elements.

### SwiftUI additions

To use MOKO MVVM with SwiftUI set name of your kotlin framework to `MultiPlatformLibrary` and add
dependency to CocoaPods:
```ruby
pod 'mokoMvvmFlowSwiftUI', :podspec => 'https://raw.githubusercontent.com/icerockdev/moko-mvvm/release/0.16.1/mokoMvvmFlowSwiftUI.podspec'
```
required export of `mvvm-core` and `mvvm-flow`.

## Usage
### Simple view model
Let’s say we need a screen with a button click counter. To implement it we should:
#### common
In `commonMain` we can create a `ViewModel` like:
```kotlin
class SimpleViewModel : ViewModel() {
    private val _counter: MutableLiveData<Int> = MutableLiveData(0)
    val counter: LiveData<String> = _counter.map { it.toString() }

    fun onCounterButtonPressed() {
        val current = _counter.value
        _counter.value = current + 1
    }
}
``` 
And after that integrate the `ViewModel` on platform the sides.
#### Android  
`SimpleActivity.kt`:
```kotlin
class SimpleActivity : MvvmActivity<ActivitySimpleBinding, SimpleViewModel>() {
    override val layoutId: Int = R.layout.activity_simple
    override val viewModelVariableId: Int = BR.viewModel
    override val viewModelClass: Class<SimpleViewModel> = SimpleViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory {
        return createViewModelFactory { SimpleViewModel() }
    }
}
```
`MvvmActivity` automatically loads a databinding layout, resolves `ViewModel` object and sets a databinding variable.  
`activity_simple.xml`:
```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="viewModel"
            type="com.icerockdev.library.sample1.SimpleViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:padding="16dp">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{viewModel.counter.ld}" />

        <Button
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:onClick="@{() -> viewModel.onCounterButtonPressed()}"
            android:text="Press me to count" />
    </LinearLayout>
</layout>
```
#### iOS
`SimpleViewController.swift`:
```swift
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm

class SimpleViewController: UIViewController {
    @IBOutlet private var counterLabel: UILabel!
    
    private var viewModel: SimpleViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        viewModel = SimpleViewModel()
        
        counterLabel.bindText(liveData: viewModel.counter)
    }
    
    @IBAction func onCounterButtonPressed() {
        viewModel.onCounterButtonPressed()
    }
    
    override func didMove(toParentViewController parent: UIViewController?) {
        if(parent == nil) { viewModel.onCleared() }
    }
}
```
`bindText` is an extension from the `MultiPlatformLibraryMvvm` CocoaPod.

### ViewModel with send events to View
Let’s say we need a screen from which we should go to another screen by pressing a button. To implement it we should:
#### common
```kotlin
class EventsViewModel(
    val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel() {

    fun onButtonPressed() {
        eventsDispatcher.dispatchEvent { routeToMainPage() }
    }

    interface EventsListener {
        fun routeToMainPage()
    }
}
```
`EventsDispatcher` is a special class that automatically removes observers from lifecycle and buffers input
 events while listener is not attached (on the Android side).
#### Android
`EventsActivity.kt`:
```kotlin
class EventsActivity : MvvmActivity<ActivityEventsBinding, EventsViewModel>(),
    EventsViewModel.EventsListener {
    override val layoutId: Int = R.layout.activity_events
    override val viewModelVariableId: Int = BR.viewModel
    override val viewModelClass: Class<EventsViewModel> = EventsViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory {
        return createViewModelFactory { EventsViewModel(eventsDispatcherOnMain()) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.eventsDispatcher.bind(
            lifecycleOwner = this,
            listener = this
        )
    }

    override fun routeToMainPage() {
        Toast.makeText(this, "here must be routing to main page", Toast.LENGTH_SHORT).show()
    }
}
```
`eventsDispatcher.bind` attaches `EventsDispatcher` to the lifecycle (in this case - to an activity) to correctly
 subscribe and unsubscribe, without memory leaks.

We can also simplify the binding of `EventsDispatcher` with `MvvmEventsActivity` and `EventsDispatcherOwnder`.
`EventsOwnerViewModel.kt`:
```kotlin
class EventsOwnerViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel(), EventsDispatcherOwner<EventsOwnerViewModel.EventsListener> {

    fun onButtonPressed() {
        eventsDispatcher.dispatchEvent { routeToMainPage() }
    }

    interface EventsListener {
        fun routeToMainPage()
    }
}
```
`EventsOwnderActivity.kt`:
```kotlin
class EventsOwnerActivity :
    MvvmEventsActivity<ActivityEventsOwnerBinding, EventsOwnerViewModel, EventsOwnerViewModel.EventsListener>(),
    EventsOwnerViewModel.EventsListener {

    override val layoutId: Int = R.layout.activity_events_owner
    override val viewModelVariableId: Int = BR.viewModel
    override val viewModelClass: Class<EventsOwnerViewModel> = EventsOwnerViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory {
        return createViewModelFactory { EventsOwnerViewModel(eventsDispatcherOnMain()) }
    }

    override fun routeToMainPage() {
        Toast.makeText(this, "here must be routing to main page", Toast.LENGTH_SHORT).show()
    }
}
```

#### iOS
`EventsViewController.swift`:
```swift
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm

class EventsViewController: UIViewController {
    private var viewModel: EventsViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let eventsDispatcher = EventsDispatcher<EventsViewModelEventsListener>(listener: self)
        viewModel = EventsViewModel(eventsDispatcher: eventsDispatcher)
    }
    
    @IBAction func onButtonPressed() {
        viewModel.onButtonPressed()
    }
    
    override func didMove(toParentViewController parent: UIViewController?) {
        if(parent == nil) { viewModel.onCleared() }
    }
}

extension EventsViewController: EventsViewModelEventsListener {
    func routeToMainPage() {
        showAlert(text: "go to main page")
    }
}
```
On iOS we create an instance of `EventsDispatcher` with the link to the listener. We shouldn't call `bind` like
 on Android (in iOS this method doesn't exist).

### ViewModel with validation of user input
```kotlin
class ValidationMergeViewModel() : ViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")

    val isLoginButtonEnabled: LiveData<Boolean> = email.mergeWith(password) { email, password ->
        email.isNotEmpty() && password.isNotEmpty()
    }
}
```
`isLoginButtonEnabled` is observable `email` & `password` `LiveData`, and in case there are any changes it calls lambda
 with the newly calculated value.

We can also use one of these combinations:
```kotlin
class ValidationAllViewModel() : ViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")

    private val isEmailValid: LiveData<Boolean> = email.map { it.isNotEmpty() }
    private val isPasswordValid: LiveData<Boolean> = password.map { it.isNotEmpty() }
    val isLoginButtonEnabled: LiveData<Boolean> = listOf(isEmailValid, isPasswordValid).all(true)
}
```
Here we have separated LiveData with the validation flags - `isEmailValid`, `isPasswordValid` and combine both
 to `isLoginButtonEnabled` by merging all boolean LiveData in the list with on the condition that "all values must be true".

### ViewModel for login feature
#### common
```kotlin
class LoginViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val userRepository: UserRepository
) : ViewModel(), EventsDispatcherOwner<LoginViewModel.EventsListener> {
    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading.readOnly()

    val isLoginButtonVisible: LiveData<Boolean> = isLoading.not()

    fun onLoginButtonPressed() {
        val emailValue = email.value
        val passwordValue = password.value

        viewModelScope.launch {
            _isLoading.value = true

            try {
                userRepository.login(email = emailValue, password = passwordValue)

                eventsDispatcher.dispatchEvent { routeToMainScreen() }
            } catch (error: Throwable) {
                val message = error.message ?: error.toString()
                val errorDesc = message.desc()

                eventsDispatcher.dispatchEvent { showError(errorDesc) }
            } finally {
                _isLoading.value = false
            }
        }
    }

    interface EventsListener {
        fun routeToMainScreen()
        fun showError(error: StringDesc)
    }
}
```
`viewModelScope` is a `CoroutineScope` field of the `ViewModel` class with a default Dispatcher - `UI` on both platforms. 
 All coroutines will be canceled in `onCleared` automatically.
#### Android
`LoginActivity.kt`:
```kotlin
class LoginActivity :
    MvvmEventsActivity<ActivityLoginBinding, LoginViewModel, LoginViewModel.EventsListener>(),
    LoginViewModel.EventsListener {

    override val layoutId: Int = R.layout.activity_login
    override val viewModelVariableId: Int = BR.viewModel
    override val viewModelClass: Class<LoginViewModel> =
        LoginViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory {
        return createViewModelFactory {
            LoginViewModel(
                userRepository = MockUserRepository(),
                eventsDispatcher = eventsDispatcherOnMain()
            )
        }
    }

    override fun routeToMainScreen() {
        Toast.makeText(this, "route to main page here", Toast.LENGTH_SHORT).show()
    }

    override fun showError(error: StringDesc) {
        Toast.makeText(this, error.toString(context = this), Toast.LENGTH_SHORT).show()
    }
}
```
`activity_login.xml`:
```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>

        <variable
            name="viewModel"
            type="com.icerockdev.library.sample6.LoginViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:padding="16dp">

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="email"
            android:text="@={viewModel.email.ld}" />

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:hint="password"
            android:text="@={viewModel.password.ld}" />

        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <Button
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:onClick="@{() -> viewModel.onLoginButtonPressed()}"
                android:text="Login"
                app:visibleOrGone="@{viewModel.isLoginButtonVisible.ld}" />

            <ProgressBar
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center"
                app:visibleOrGone="@{viewModel.isLoading.ld}" />
        </FrameLayout>
    </LinearLayout>
</layout>
```
#### iOS
`LoginViewController.swift`:
```swift
class LoginViewController: UIViewController {
    @IBOutlet private var emailField: UITextField!
    @IBOutlet private var passwordField: UITextField!
    @IBOutlet private var loginButton: UIButton!
    @IBOutlet private var progressBar: UIActivityIndicatorView!
    
    private var viewModel: LoginViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let eventsDispatcher = EventsDispatcher<LoginViewModelEventsListener>(listener: self)
        viewModel = LoginViewModel(eventsDispatcher: eventsDispatcher,
                                   userRepository: MockUserRepository())
        
        emailField.bindTextTwoWay(liveData: viewModel.email)
        passwordField.bindTextTwoWay(liveData: viewModel.password)
        loginButton.bindVisibility(liveData: viewModel.isLoginButtonVisible)
        progressBar.bindVisibility(liveData: viewModel.isLoading)
    }
    
    @IBAction func onLoginButtonPressed() {
        viewModel.onLoginButtonPressed()
    }
    
    override func didMove(toParentViewController parent: UIViewController?) {
        if(parent == nil) { viewModel.onCleared() }
    }
}

extension LoginViewController: LoginViewModelEventsListener {
    func routeToMainScreen() {
        showAlert(text: "route to main screen")
    }
    
    func showError(error: StringDesc) {
        showAlert(text: error.localized())
    }
}
```

## Samples
Please see more examples in the [sample directory](sample).

## Set Up Locally 
- The [mvvm directory](mvvm) contains the umbrella library;
- The [mvvm-core directory](mvvm-core) contains the core - ViewModel, EventsDispatcher;
- The [mvvm-livedata directory](mvvm-livedata) contains the livedata classes and extensions;
- The [mvvm-databinding directory](mvvm-databinding) contains DataBinding support code for Android;
- The [mvvm-viewbinding directory](mvvm-viewbinding) contains ViewBinding support code for Android;
- The [mvvm-test directory](mvvm-test) contains the test utilities;
- In [sample directory](sample) contains sample apps for Android and iOS; plus the mpp-library connected to the apps;
- In [sample-declarative-ui directory](sample-declarative-ui) contains sample apps with Jetpack Compose and SwiftUI.

## Contributing
All development (both new features and bug fixes) is performed in the `develop` branch. This way `master` always contains the sources of the most recently released version. Please send PRs with bug fixes to the `develop` branch. Documentation fixes in the markdown files are an exception to this rule. They are updated directly in `master`.

The `develop` branch is pushed to `master` on release.

For more details on contributing please see the [contributing guide](CONTRIBUTING.md).

## We’re hiring a Mobile Developers for our main team in Novosibirsk and remote team with Moscow timezone!

If you like to develop mobile applications, are an expert in iOS/Swift or Android/Kotlin and eager to use Kotlin Multiplatform in production, we'd like to talk to you.

[To learn more and apply](https://career.habr.com/companies/icerockdev)

## License
        
    Copyright 2019 IceRock MAG Inc.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[badge-android]: http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat
[badge-ios]: http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat
[badge-js]: http://img.shields.io/badge/platform-js-F8DB5D.svg?style=flat
[badge-jvm]: http://img.shields.io/badge/platform-jvm-DB413D.svg?style=flat
[badge-linux]: http://img.shields.io/badge/platform-linux-2D3F6C.svg?style=flat
[badge-windows]: http://img.shields.io/badge/platform-windows-4D76CD.svg?style=flat
[badge-mac]: http://img.shields.io/badge/platform-macos-111111.svg?style=flat
[badge-watchos]: http://img.shields.io/badge/platform-watchos-C0C0C0.svg?style=flat
[badge-tvos]: http://img.shields.io/badge/platform-tvos-808080.svg?style=flat
[badge-wasm]: https://img.shields.io/badge/platform-wasm-624FE8.svg?style=flat
[badge-nodejs]: https://img.shields.io/badge/platform-nodejs-68a063.svg?style=flat
