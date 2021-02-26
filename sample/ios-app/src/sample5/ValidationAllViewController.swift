/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import Foundation
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm

class MyTextField: UITextField {
    
}

class ValidationAllViewController: UIViewController {
    @IBOutlet private var emailField: MyTextField!
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
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        DispatchQueue.main.async { [weak self] in
            self?.navigationController?.popViewController(animated: true)
        }
    }
}

class StartViewController: UIViewController {
    private var counter: Int = 0
    @IBOutlet var openButton: UIButton!
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        if counter > 10 {
            GarbageCollectorKt.collect()
            GarbageCollectorKt.cycles()
            return
        }
        
        DispatchQueue.main.async { [weak self] in
            self?.counter += 1
            self?.openButton.sendActions(for: .touchUpInside)
        }
    }
}
