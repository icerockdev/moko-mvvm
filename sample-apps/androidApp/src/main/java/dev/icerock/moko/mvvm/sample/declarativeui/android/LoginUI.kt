/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.sample.declarativeui.android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.icerock.moko.mvvm.compose.observeAsState
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import dev.icerock.moko.mvvm.sample.declarativeui.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(
        factory = viewModelFactory { LoginViewModel(eventsDispatcherOnMain()) }
    ),
    onLoginSuccess: () -> Unit = {}
) {
    val login: String by viewModel.login.observeAsState()
    val password: String by viewModel.password.observeAsState()
    val isLoading: Boolean by viewModel.isLoading.observeAsState()
    val isLoginButtonEnabled: Boolean by viewModel.isLoginButtonEnabled.observeAsState()

    val currentOnLoginSuccess by rememberUpdatedState(onLoginSuccess)
    viewModel.eventsDispatcher.bindToComposable(
        object : LoginViewModel.EventsListener {
            override fun routeSuccessfulAuth() {
                currentOnLoginSuccess()
            }

            override fun showError(message: String) {
                TODO("Not yet implemented")
            }
        }
    )

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = login,
            label = { Text(text = "Login") },
            onValueChange = { viewModel.login.value = it }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { viewModel.password.value = it }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = isLoginButtonEnabled,
            onClick = { viewModel.onLoginPressed() }
        ) {
            if (isLoading) CircularProgressIndicator(modifier = Modifier.size(24.dp))
            else Text(text = "Login")
        }
    }
}

@Preview(showSystemUi = true, group = "empty")
@Composable
fun LoginScreen_Preview() {
    LoginScreen()
}

@Preview(showSystemUi = true, group = "filled")
@Composable
fun LoginScreenFilledLogin_Preview() {
    LoginScreen(
        viewModel = LoginViewModel(eventsDispatcherOnMain()).apply {
            login.value = "test"
        }
    )
}

@Preview(showSystemUi = true, group = "filled")
@Composable
fun LoginScreenFilledAll_Preview() {
    LoginScreen(
        viewModel = LoginViewModel(eventsDispatcherOnMain()).apply {
            login.value = "test"
            password.value = "test"
        }
    )
}

@Preview(showSystemUi = true, group = "load")
@Composable
fun LoginScreenLoading_Preview() {
    LoginScreen(
        viewModel = LoginViewModel(eventsDispatcherOnMain()).apply {
            login.value = "test"
            password.value = "test"
            onLoginPressed()
        }
    )
}
