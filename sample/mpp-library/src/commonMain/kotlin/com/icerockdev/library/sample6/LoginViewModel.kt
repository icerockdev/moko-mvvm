/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.library.sample6

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.all
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.livedata.revert
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.launch

class LoginViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val userRepository: UserRepository
) : ViewModel(), EventsDispatcherOwner<LoginViewModel.EventsListener> {
    val email: MutableLiveData<String> = MutableLiveData("")
    private val _emailValidation = MutableLiveData<String?>(null)
    val emailValidation: LiveData<String?> get() = _emailValidation
    val password: MutableLiveData<String> = MutableLiveData("")

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading.readOnly()

    val isLoginButtonVisible: LiveData<Boolean> = listOf(
        isLoading.revert(),
        email.map { it.isNotBlank() },
        password.map { it.isNotBlank() }
    ).all(true)

    init {
        eventsDispatcher.dispatchEvent { showError("inited".desc()) }
    }

    fun onLoginButtonPressed() {
        val emailValue = email.value
        val passwordValue = password.value

        if (emailValue.isBlank()) {
            _emailValidation.value = "email can't be blank"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true

            @Suppress("TooGenericExceptionCaught")
            try {
                userRepository.login(email = emailValue, password = passwordValue)

                eventsDispatcher.dispatchEvent { routeToMainScreen() }
            } catch (error: Exception) {
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
