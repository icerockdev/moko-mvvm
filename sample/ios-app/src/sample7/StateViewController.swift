/*
* Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
*/

import UIKit
import MultiPlatformLibrary

class StateViewController: UIViewController {
    @IBOutlet private var infoLabel: UILabel!
    @IBOutlet private var updateButton: UIButton!
    @IBOutlet private var loadingIndicator: UIActivityIndicatorView!
    private var viewModel: StateSampleViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        viewModel = StateSampleViewModel()
        viewModel.state.addObserver { [weak self] state in
            guard let nState = state else { return }
            self?.updateView(withState: nState)
        }
        
        //updateButton.bindEnabled(liveData: viewModel.state.isLoadingState().not())
        
    }
    
    private func updateView(withState state: State<NSString, StringDesc>) {
        infoLabel.isHidden = state.isLoading()
        state.isLoading() ? loadingIndicator.startAnimating() : loadingIndicator.stopAnimating()
        updateButton.isUserInteractionEnabled = !state.isLoading()
        
        if (state.isEmpty()) {
            infoLabel.textColor = .lightGray
            infoLabel.text = "No data"
        }
        
        if (state.isError()) {
            infoLabel.textColor = .red
            infoLabel.text = state.errorValue()?.localized()
        }
        
        if let text = state.dataValue(), state.isSuccess() {
            infoLabel.textColor = .black
            infoLabel.text = String(text)
        }
    }
    
    @IBAction private func updateState(_ sender: UIButton!) {
        viewModel.updateState()
    }
}
