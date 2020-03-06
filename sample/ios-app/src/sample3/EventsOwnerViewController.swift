/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import Foundation
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm

class EventsOwnerViewController: UIViewController {
    private var viewModel: EventsOwnerViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let eventsDispatcher = EventsDispatcher<EventsOwnerViewModelEventsListener>(listener: self)
        viewModel = EventsOwnerViewModel(eventsDispatcher: eventsDispatcher)
    }
    
    @IBAction func onButtonPressed() {
        viewModel.onButtonPressed()
    }
    
    override func didMove(toParentViewController parent: UIViewController?) {
        if(parent == nil) { viewModel.onCleared() }
    }
}

extension EventsOwnerViewController: EventsOwnerViewModelEventsListener {
    func routeToMainPage() {
        showAlert(text: "go to main page")
    }
}
