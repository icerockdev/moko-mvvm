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

/**
 * Sample ViewModel for screen with input forms implemented by state
 * in single StateFlow (mapped to class) and actions by Flow (also mapped to class).
 * User can change state by onRateChange and onMessageChange - ViewModel
 * just like reducer in Redux will change state and send new version to UI.
 *
 * Notes:
 * - Flow and StateFlow lost generics on Swift side (because it's interfaces), so we should convert
 *   it to classes - it was done by cStateFlow and cFlow extensions. CFlow and CStateFlow it's
 *   generic classes that can be used from Swift without problems
 * - Flow and StateFlow can be observed with Jetpack Compose out of box. For SwiftUI required
 *   multiple utils functions - binding/state on Swift
 * - To use exhaust swift enum we should use moko-kswift plugin
 */
class BookReviewViewModel(
    private val bookId: Int
) : ViewModel() {

    private val _state = MutableStateFlow<State>(
        State.Idle(form = InputForm(rate = 0, message = ""))
    )
    val state: CStateFlow<State> get() = _state.cStateFlow()

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions: CFlow<Action> get() = _actions.receiveAsFlow().cFlow()

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
                _actions.send(Action.CloseScreen)
            }
        }
    }

    fun onErrorClosed() {
        val state: State.Error = _state.value as? State.Error ?: return
        _state.value = State.Idle(state.form)
    }

    sealed interface State {
        data class Idle(override val form: InputForm) : State
        data class Loading(override val form: InputForm) : State
        data class Error(override val form: InputForm, val message: StringDesc) : State

        val form: InputForm
    }

    sealed interface Action {
        object CloseScreen : Action
    }

    data class InputForm(
        val rate: Int,
        val message: String
    )
}
