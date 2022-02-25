/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

class ValidationAllViewController: UIViewController {
    @IBOutlet private var emailField: UITextField!
    @IBOutlet private var passwordField: UITextField!
    @IBOutlet private var button: UIButton!
    
    private var viewModel: ValidationAllViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        viewModel = ValidationAllViewModel()
        
        emailField.bindTextTwoWay(liveData: viewModel.email)
        passwordField.bindTextTwoWay(liveData: viewModel.password)
        button.bindEnabled(liveData: viewModel.isLoginButtonEnabled)
    }
    
    override func didMove(toParent parent: UIViewController?) {
        if(parent == nil) { viewModel.onCleared() }
    }
}
