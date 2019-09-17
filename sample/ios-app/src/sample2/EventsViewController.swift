/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import Foundation
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm

class EventsViewController: UIViewController {
    private var viewModel: EventsViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let eventsDispatcher = EventsDispatcher<EventsViewModelEventsListener>(listener: self)
        viewModel = EventsViewModel(eventsDispatcher: eventsDispatcher)
    }
    
    @IBAction func onButtonPressed() {
        viewModel.onButtonPressed()
    }
    
    deinit {
        viewModel.onCleared()
    }
}

extension EventsViewController: EventsViewModelEventsListener {
    func routeToMainPage() {
        showAlert(text: "go to main page")
    }
}
