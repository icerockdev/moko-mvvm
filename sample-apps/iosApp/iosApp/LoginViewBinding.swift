//
//  LoginViewBinding.swift
//  iosApp
//
//  Created by Aleksey Mikhailov on 18.03.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared
import Combine

struct LoginView: View {
    @ObservedObject var viewModel: LoginViewModel = LoginViewModel()
    let onLoginSuccess: () -> Void
    @State var alertShowed: Bool = false
    @State var alertMessage: String = ""
    
    var body: some View {
        LoginViewBody(
            login: viewModel.stateKs.login(viewModel),
            password: viewModel.stateKs.password(viewModel),
            isButtonEnabled: viewModel.stateKs.isButtonEnabled,
            isLoading: viewModel.stateKs.isLoadingEnabled,
            onLoginPressed: { viewModel.onLoginPressed() }
        ).onReceive(viewModel.actionsKs) { action in
            switch(action) {
            case .routeToSuccess:
                onLoginSuccess()
            case .showError(let data):
                print(data)
            }
        }.alert(
            isPresented: $alertShowed
        ) {
            Alert(
                title: Text("Error"),
                message: Text(alertMessage),
                dismissButton: .default(Text("Close"))
            )
        }
    }
}

extension LoginViewModel {
    var stateKs: LoginViewModelStateKs {
        get {
            return self.state(
                \.state,
                equals: { $0 === $1 },
                mapper: { LoginViewModelStateKs($0) }
            )
        }
    }
    
    var actionsKs: AnyPublisher<LoginViewModelActionKs, Never> {
        get {
            return createPublisher(self.actions)
                .map { LoginViewModelActionKs($0) }
                .eraseToAnyPublisher()
        }
    }
}

extension LoginViewModelStateKs {
    var isButtonEnabled: Bool {
        get {
            switch(self) {
            case .idle(let data):
                return data.isLoginButtonEnabled
            case .loading(let data):
                return data.isLoginButtonEnabled
            }
        }
    }
    
    var isLoadingEnabled: Bool {
        get {
            switch(self) {
            case .loading(_): return true
            default: return false
            }
        }
    }
    
    func login(_ viewModel: LoginViewModel) -> Binding<String> {
        return Binding(
            get: {
                switch(self) {
                case .loading(let data): return data.form.login
                case .idle(let data): return data.form.login
                }
            },
            set: { viewModel.onLoginChanged(value: $0) }
        )
    }
    
    func password(_ viewModel: LoginViewModel) -> Binding<String> {
        return Binding(
            get: {
                switch(self) {
                case .loading(let data): return data.form.password
                case .idle(let data): return data.form.password
                }
            },
            set: { viewModel.onPasswordChanged(value: $0) }
        )
    }
}
