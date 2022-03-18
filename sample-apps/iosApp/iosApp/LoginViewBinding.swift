//
//  LoginViewBinding.swift
//  iosApp
//
//  Created by Aleksey Mikhailov on 18.03.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LoginView: View {
    @ObservedObject var viewModel: LoginViewModel = LoginViewModel(
        eventsDispatcher: EventsDispatcher()
    )
    let onLoginSuccess: () -> Void
    
    var body: some View {
        LoginViewBody(
            login: viewModel.binding { $0.login },
            password: viewModel.binding { $0.password },
            isButtonEnabled: viewModel.state { $0.isLoginButtonEnabled },
            isLoading: viewModel.state { $0.isLoading },
            onLoginPressed: { viewModel.onLoginPressed() }
        ).onAppear {
            let listener = LoginEventsListener()
            listener.doRouteSuccessfulAuth = { self.onLoginSuccess() }
            viewModel.eventsDispatcher.listener = listener
        }
    }
}

private class LoginEventsListener: NSObject, LoginViewModelEventsListener {
    var doRouteSuccessfulAuth: () -> Void = {}
    
    func routeSuccessfulAuth() {
        doRouteSuccessfulAuth()
    }
}
