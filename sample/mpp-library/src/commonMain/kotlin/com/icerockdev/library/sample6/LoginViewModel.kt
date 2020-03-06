/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.library.sample6

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.not
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.launch

class LoginViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val userRepository: UserRepository
) : ViewModel(), EventsDispatcherOwner<LoginViewModel.EventsListener> {
    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading.readOnly()

    val isLoginButtonVisible: LiveData<Boolean> = isLoading.not()

    init {
        eventsDispatcher.dispatchEvent { showError("inited".desc()) }
    }

    fun onLoginButtonPressed() {
        val emailValue = email.value
        val passwordValue = password.value

        viewModelScope.launch {
            _isLoading.value = true

            try {
                userRepository.login(email = emailValue, password = passwordValue)

                eventsDispatcher.dispatchEvent { routeToMainScreen() }
            } catch (error: Throwable) {
                val message = error.message ?: error.toString()
                val errorDesc = message.desc()

                eventsDispatcher.dispatchEvent { showError(errorDesc) }
            } finally {
                _isLoading.value = false
            }
        }
    }

    interface EventsListener {
        fun routeToMainScreen()
        fun showError(error: StringDesc)
    }
}
