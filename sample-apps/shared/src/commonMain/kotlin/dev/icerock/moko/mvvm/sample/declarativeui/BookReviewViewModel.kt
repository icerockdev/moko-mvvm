/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.sample.declarativeui

import dev.icerock.moko.mvvm.CFlow
import dev.icerock.moko.mvvm.CStateFlow
import dev.icerock.moko.mvvm.cFlow
import dev.icerock.moko.mvvm.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class BookReviewViewModel(
    private val bookId: Int
) : ViewModel() {

    private val _state = MutableStateFlow<State>(
        State.Idle(form = InputForm(rate = 0, message = ""))
    )
    val state: CStateFlow<State> get() = _state.cStateFlow()

    private val _actions = MutableSharedFlow<Action>()
    val actions: CFlow<Action> get() = _actions.cFlow()

    fun onRateChange(rate: Int) {
        val state: State.Idle = _state.value as? State.Idle ?: return
        _state.value = state.copy(form = state.form.copy(rate = rate))
    }

    fun onMessageChange(message: String) {
        val state: State.Idle = _state.value as? State.Idle ?: return
        _state.value = state.copy(form = state.form.copy(message = message))
    }

    fun onSendPressed() {
        val state: State.Idle = _state.value as? State.Idle ?: return
        val form: InputForm = state.form

        _state.value = State.Loading(form)
        viewModelScope.launch {
            println("here i send some request with $bookId and $form")
            delay(3.seconds)

            if (form.rate == 0) {
                _state.value = State.Error(form, "invalid rate!".desc())
            } else {
                _actions.tryEmit(Action.CloseScreen)
            }
        }
    }

    sealed interface State {
        data class Idle(val form: InputForm) : State
        data class Loading(val form: InputForm) : State
        data class Error(val form: InputForm, val message: StringDesc) : State
    }

    sealed interface Action {
        object CloseScreen : Action
    }

    data class InputForm(
        val rate: Int,
        val message: String
    )
}
