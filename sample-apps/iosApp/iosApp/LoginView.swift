//
//  LoginView.swift
//  iosApp
//
//  Created by Aleksey Mikhailov on 18.03.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct LoginView: View {
    @ObservedObject var viewModel: LoginViewModel = LoginViewModel(
        eventsDispatcher: EventsDispatcher()
    ).observed {
        $0.login.distinct()
        $0.password.distinct()
        $0.isLoading.distinct()
        $0.isLoginButtonEnabled.distinct()
    }
    let onLoginSuccess: () -> Void
    
    var body: some View {
        LoginViewBody(
            login: binding(viewModel.login),
            password: binding(viewModel.password),
            isButtonEnabled: state(viewModel.isLoginButtonEnabled),
            isLoading: state(viewModel.isLoading),
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

struct LoginViewBody: View {
    @Binding var login: String
    @Binding var password: String
    let isButtonEnabled: Bool
    let isLoading: Bool
    let onLoginPressed: () -> Void
    
    var body: some View {
        VStack(spacing: 8.0) {
            TextField("Login", text: $login)
                .textFieldStyle(.roundedBorder)
                .disabled(isLoading)
            
            TextField("Password", text: $password)
                .textFieldStyle(.roundedBorder)
                .disabled(isLoading)
            
            if isLoading {
                ProgressView()
            } else {
                Button("Login") {
                    onLoginPressed()
                }.disabled(!isButtonEnabled)
            }
        }.padding()
    }
}


struct LoginView_Previews: PreviewProvider {
    struct Preview: View {
        var body: some View {
            LoginViewBody(
                login: State(initialValue: "").projectedValue,
                password: State(initialValue: "").projectedValue,
                isButtonEnabled: false,
                isLoading: false,
                onLoginPressed: {}
            )
            LoginViewBody(
                login: State(initialValue: "test").projectedValue,
                password: State(initialValue: "pass").projectedValue,
                isButtonEnabled: true,
                isLoading: false,
                onLoginPressed: {}
            )
            LoginViewBody(
                login: State(initialValue: "test").projectedValue,
                password: State(initialValue: "pass").projectedValue,
                isButtonEnabled: false,
                isLoading: true,
                onLoginPressed: {}
            )
        }
    }
    
    static var previews: some View {
        Group {
            Preview()
        }.previewDisplayName("Light theme")
            .preferredColorScheme(.light)
        
        Group {
            Preview()
        }.previewDisplayName("Dark theme")
            .preferredColorScheme(.dark)
    }
}
