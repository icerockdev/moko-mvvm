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
    
    @ObservedObject private var listener: LoginEventsListener = LoginEventsListener()
    
    var body: some View {
        LoginViewBody(
            login: viewModel.binding(\.login),
            password: viewModel.binding(\.password),
            isButtonEnabled: viewModel.state(\.isLoginButtonEnabled),
            isLoading: viewModel.state(\.isLoading),
            onLoginPressed: { viewModel.onLoginPressed() }
        ).onAppear {
            let listener = LoginEventsListener()
            listener.doRouteSuccessfulAuth = {
                self.onLoginSuccess()
            }
            viewModel.eventsDispatcher.listener = listener
        }.alert(
            isPresented: listener.$isErrorShowed
        ) {
            Alert(
                title: Text("Error"),
                message: Text(listener.errorText ?? ""),
                dismissButton: .default(Text("Close"))
            )
        }
    }
}

private class LoginEventsListener: ObservableObject, LoginViewModelEventsListener {
    @State var isErrorShowed: Bool = false
    @State var errorText: String? = nil
    
    func showError(message: String) {
        isErrorShowed = true
        errorText = message
    }
    
    var doRouteSuccessfulAuth: () -> Void = {}
    
    func routeSuccessfulAuth() {
        doRouteSuccessfulAuth()
    }
}
