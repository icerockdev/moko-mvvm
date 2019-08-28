[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[ ![Download](https://api.bintray.com/packages/icerockdev/moko/moko-mvvm/images/download.svg) ](https://bintray.com/icerockdev/moko/moko-mvvm/_latestVersion)

# Мультиплатформенные компоненты под MVVM архитектуру
Model-View-ViewModel Основанный на Android Architecture Components https://developer.android.com/topic/libraries/architecture/viewmodel

`ViewModel` на android является вьюмоделью из AAC. На айос - своя реализация.  
`ViewModel` является `CoroutineScope` и при `onCleared` отменяет запущенные корутины.  
 
В связке с `ViewModel` используется `EventsDispatcher` - для типизированного вызова событий на `view`,
 типа: `routeToProfile()`, `showError(stringDesc)`. Все события (то что не должно быть сохранено на
  постоянку в состояние вьюмодели) вызываются через него.

Так же в связке с `ViewModel` используется `LiveData` - реактивное хранилище данных, с возможностью
 привязки к UI на обеих целевых платформах. К `LiveData` сделано множество методов расширений,
 позволяющих упростить код трансформации данных.

## Пример EventsDispatcher
common:
```kotlin
class RootViewModel(val eventsDispatcher: EventsDispatcher<EventsListener>) : ViewModel() {
    
    fun logout() {
        eventsDispatcher.dispatchEvent { routeToAuth() }
    }

    fun loadUser() {
        launch {
            try {
                // ...
            } catch (error: Throwable) {
                eventsDispatcher.dispatchEvent { showError(error.userFriendlyDescription()) }
            }
        }
    }

    interface EventsListener {
        fun showError(message: StringDesc)
        fun routeToAuth()
    }
}
```
android:
```kotlin
class RootActivity : AppCompatActivity(), RootViewModel.EventsListener {
    lateinit var mViewModel: RootViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = getViewModel { RootViewModel(EventsDispatcherExecutor.dispatcher()) }

        mViewModel.eventsDispatcher.bind(this, this)
    }

    override fun showError(message: StringDesc) {
        toast(message.toString(this))
    }

    override fun routeToAuth() {
        intentClearStack<AuthPhoneActivity>().start(this)
    }
}
```
iOS:
```swift
class SideMenuHeaderController: UIViewController, RootViewModelEventsListener {
  private (set) var viewModel: RootViewModel!

  func routeToAuth() {
    parent?.navigationController?.dismiss(animated: true, completion: nil)
    SideMenuManager.default.menuLeftNavigationController?.dismiss(animated: false, completion: nil)
    SideMenuManager.default.menuLeftNavigationController = nil
    AppDelegate.goToLogin()
    PushService.shared.unregisterFromFCM()
  }
  
  func showError(message: StringDesc) {
    //TODO: Localize me
    super.showAlert(title: "Ошибка", message: message.localized(), actions: [])
  }
    
  override func viewDidLoad() {
    super.viewDidLoad()
    
    viewModel = RootViewModel(eventsDispatcher: EventsDispatcher(mListener: self))
  }
}
```
