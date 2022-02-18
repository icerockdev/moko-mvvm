/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

class LoginViewController: UIViewController {
    @IBOutlet private var emailField: UITextField!
    @IBOutlet private var emailValidationLabel: UILabel!
    @IBOutlet private var passwordField: UITextField!
    @IBOutlet private var loginButton: UIButton!
    @IBOutlet private var progressBar: UIActivityIndicatorView!
    
    private var viewModel: LoginViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        viewModel = LoginViewModel(eventsDispatcher: EventsDispatcher(),
                                   userRepository: MockUserRepository())
        
        viewModel.eventsDispatcher.listener = self

        emailField.bindTextTwoWay(liveData: viewModel.email)
        //emailValidationLabel.bindText(liveData: viewModel.emailValidation)
        passwordField.bindTextTwoWay(liveData: viewModel.password)
        loginButton.bindHidden(liveData: viewModel.isLoginButtonVisible.revert())
        progressBar.bindHidden(liveData: viewModel.isLoading.revert())
    }
    
    @IBAction func onLoginButtonPressed() {
        viewModel.onLoginButtonPressed()
    }
    
    override func didMove(toParent parent: UIViewController?) {
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
