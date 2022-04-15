/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.sample.declarativeui

import dev.icerock.moko.mvvm.flow.CFlow
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.cFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class LoginViewModel : ViewModel() {

    private val _state: MutableStateFlow<State> = InputForm(
        login = "",
        password = ""
    ).let { MutableStateFlow(State.Idle(it)) }
    val state: CStateFlow<State> = _state.cStateFlow()

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions: CFlow<Action> get() = _actions.receiveAsFlow().cFlow()

    fun onLoginChanged(value: String) {
        val idle: State.Idle = _state.value as? State.Idle ?: return
        _state.value = idle.copy(form = idle.form.copy(login = value))
    }

    fun onPasswordChanged(value: String) {
        val idle: State.Idle = _state.value as? State.Idle ?: return
        _state.value = idle.copy(form = idle.form.copy(password = value))
    }

    fun onLoginPressed() {
        val form: InputForm = _state.value.form

        _state.value = State.Loading(form)
        viewModelScope.launch {
            delay(1.seconds)

            if (form.login != "error") {
                _actions.send(Action.RouteToSuccess)
            } else {
                _actions.send(Action.ShowError("some error!".desc()))
            }

            _state.value = State.Idle(form)
        }
    }

    sealed interface State {
        data class Idle(override val form: InputForm) : State {
            override val isLoginButtonEnabled: Boolean =
                form.login.isNotBlank() && form.password.isNotBlank()
        }

        data class Loading(override val form: InputForm) : State {
            override val isLoginButtonEnabled: Boolean get() = false
        }

        val form: InputForm
        val isLoginButtonEnabled: Boolean
    }

    sealed interface Action {
        object RouteToSuccess : Action
        data class ShowError(val error: StringDesc) : Action
    }

    data class InputForm(
        val login: String,
        val password: String
    )
}
