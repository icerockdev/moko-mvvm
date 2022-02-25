/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

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
    
    override func didMove(toParent parent: UIViewController?) {
        if(parent == nil) { viewModel.onCleared() }
    }
}
