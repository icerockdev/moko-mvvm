![moko-mvvm](img/logo.png)  
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0) [![Download](https://api.bintray.com/packages/icerockdev/moko/moko-mvvm/images/download.svg) ](https://bintray.com/icerockdev/moko/moko-mvvm/_latestVersion) ![kotlin-version](https://img.shields.io/badge/kotlin-1.4.0-orange)

# Mobile Kotlin Model-View-ViewModel architecture components
This is a Kotlin Multiplatform library that provides architecture components of Model-View-ViewModel
 for UI applications. Components are lifecycle-aware on Android.  

## Table of Contents
- [Features](#features)
- [Requirements](#requirements)
- [Versions](#versions)
- [Installation](#installation)
- [Usage](#usage)
- [Samples](#samples)
- [Set Up Locally](#set-up-locally)
- [Contributing](#contributing)
- [License](#license)

## Features
- **ViewModel** - store and manage UI-related data. Interop with `Android Architecture Components` - on Android it's precisely `androidx.lifecycle.ViewModel`;
- **LiveData, MutableLiveData, MediatorLiveData** - lifecycle-aware reactive data holders with set of operators to transform, merge, etc.;
- **EventsDispatcher** - dispatch events from `ViewModel` to `View` with automatic lifecycle control and explicit interface of required events.

## Requirements
- Gradle version 6.0+
- Android API 16+
- iOS version 9.0+

## Versions
- kotlin 1.3.50
  - 0.1.0
  - 0.2.0
  - 0.3.0
  - 0.3.1
- kotlin 1.3.61
  - 0.4.0
  - 0.5.0
- kotlin 1.3.70
  - 0.6.0
- kotlin 1.3.72
  - 0.7.0
  - 0.7.1
- kotlin 1.4.0
  - 0.8.0

## Installation
root build.gradle  
```groovy
allprojects {
    repositories {
        maven { url = "https://dl.bintray.com/icerockdev/moko" }
    }
}
```

project build.gradle
```groovy
dependencies {
    commonMainApi("dev.icerock.moko:mvvm:0.8.0")
    androidMainImplementation("androidx.lifecycle:lifecycle-extensions:2.1.0")
}
```

On iOS, in addition to the Kotlin library add in Podfile
```ruby
pod 'MultiPlatformLibraryMvvm', :git => 'https://github.com/icerockdev/moko-mvvm.git', :tag => 'release/0.8.0'
```
**`MultiPlatformLibraryMvvm` CocoaPod requires that the framework compiled from Kotlin be named 
`MultiPlatformLibrary` and be connected as a CocoaPod `MultiPlatformLibrary`. 
[Here](sample/ios-app/Podfile)'s an example.
To simplify integration with MultiPlatformFramework you can use [mobile-multiplatform-plugin](https://github.com/icerockdev/mobile-multiplatform-gradle-plugin)**

`MultiPlatformLibraryMvvm` CocoaPod contains the extension to `UIView`s for binding with `LiveData`.

## Usage
### Simple view model
Let’s say we need a screen with a button click counter. To implement it we should:
#### common
In `commonMain` we can create a `ViewModel` like:
```kotlin
class SimpleViewModel() : ViewModel() {
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
- The [mvvm directory](mvvm) contains the `mvvm` library;
- In [sample directory](sample) contains sample apps for Android and iOS; plus the mpp-library connected to the apps.

## Contributing
All development (both new features and bug fixes) is performed in the `develop` branch. This way `master` always contains the sources of the most recently released version. Please send PRs with bug fixes to the `develop` branch. Documentation fixes in the markdown files are an exception to this rule. They are updated directly in `master`.

The `develop` branch is pushed to `master` on release.

For more details on contributing please see the [contributing guide](CONTRIBUTING.md).

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