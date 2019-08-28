/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm

class TestViewController: UIViewController {
    @IBOutlet weak var labelString: UILabel!
    @IBOutlet weak var labelStringDesc: UILabel!
    @IBOutlet weak var inputSwitch: UISwitch!
    @IBOutlet weak var outputSwitch: UISwitch!
    @IBOutlet weak var inputTextField: UITextField!
    @IBOutlet weak var outputTextView: UITextView!
    
    private var viewModel: TestViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let eventsDispatcher = EventsDispatcher<TestViewModelEventsListener>(listener: self)
        viewModel = TestViewModel(eventsDispatcher: eventsDispatcher)
        
        labelString.bindText(liveData: viewModel.string)
        labelStringDesc.bindText(liveData: viewModel.stringDesc)
        inputSwitch.bindValueTwoWay(liveData: viewModel.mutableSwitch)
        outputSwitch.bindValue(liveData: viewModel.readOnlySwitch)
        inputTextField.bindTextTwoWay(liveData: viewModel.mutableString)
        outputTextView.bindText(liveData: viewModel.mutableString)
    }
    
    @IBAction func onCounterButtonPressed() {
        viewModel.onCounterButtonPressed()
    }
    
    @IBAction func onErrorButtonPressed() {
        viewModel.onErrorButtonPressed()
    }
    
    deinit {
        viewModel.onCleared()
    }
}

extension TestViewController: TestViewModelEventsListener {
    func showError(error: StringDesc) {
        let alert = UIAlertController(title: nil,
                                      message: error.localized(),
                                      preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "Ok", style: .cancel, handler: nil))
        present(alert, animated: true, completion: nil)
    }
}
