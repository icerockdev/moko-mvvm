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
        
        viewModel.email.bindStringTwoWayToTextFieldText(textField: emailField)
        viewModel.password.bindStringTwoWayToTextFieldText(textField: passwordField)
        viewModel.isLoginButtonEnabled.bindBoolToControlEnabled(control: button)
    }
    
    override func didMove(toParent parent: UIViewController?) {
        if(parent == nil) { viewModel.onCleared() }
    }
}
