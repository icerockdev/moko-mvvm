//
//  LoginViewBinding.swift
//  iosApp
//
//  Created by Aleksey Mikhailov on 18.03.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI
import Combine

struct LoginView: View {
    @ObservedObject var viewModel: LoginViewModel = LoginViewModel()
    let onLoginSuccess: () -> Void
    @State var alertShowed: Bool = false
    @State var alertMessage: String = ""
    
    var body: some View {
        LoginViewBody(
            login: viewModel.binding(\.login),
            password: viewModel.binding(\.password),
            isButtonEnabled: viewModel.state(\.isLoginButtonEnabled),
            isLoading: viewModel.state(\.isLoading),
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
    var actionsKs: AnyPublisher<LoginViewModelActionKs, Never> {
        get {
            return createPublisher(self.actions)
                .map { LoginViewModelActionKs($0) }
                .eraseToAnyPublisher()
        }
    }
}
