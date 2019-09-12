/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import Foundation
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm

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
    
    deinit {
        viewModel.onCleared()
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
