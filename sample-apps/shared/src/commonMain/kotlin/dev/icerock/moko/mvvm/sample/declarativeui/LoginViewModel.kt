/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.sample.declarativeui

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.mediatorOf
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class LoginViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel(), EventsDispatcherOwner<LoginViewModel.EventsListener> {
    val login: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    val isLoginButtonEnabled: LiveData<Boolean> =
        mediatorOf(isLoading, login, password) { isLoading, login, password ->
            isLoading.not() && login.isNotBlank() && password.isNotBlank()
        }

    fun onLoginPressed() {
        _isLoading.value = true
        viewModelScope.launch {
            delay(3.seconds)

            eventsDispatcher.dispatchEvent { routeSuccessfulAuth() }

            _isLoading.value = false
        }
    }

    interface EventsListener {
        fun routeSuccessfulAuth()
    }
}
