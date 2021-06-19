/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

class EventsOwnerViewController: UIViewController {
    private var viewModel: EventsOwnerViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let eventsDispatcher = EventsDispatcher<EventsOwnerViewModelEventsListener>(listener: self)
        viewModel = EventsOwnerViewModel(eventsDispatcher: eventsDispatcher)
        viewModel.clearOnDetach(viewController: self)
    }
    
    @IBAction func onButtonPressed() {
        viewModel.onButtonPressed()
    }
}

extension EventsOwnerViewController: EventsOwnerViewModelEventsListener {
    func routeToMainPage() {
        showAlert(text: "go to main page")
    }
}
