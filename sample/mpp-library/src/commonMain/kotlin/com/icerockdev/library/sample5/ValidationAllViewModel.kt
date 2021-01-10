/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.library.sample5

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.all
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class ValidationAllViewModel : ViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")

    private val isEmailValid: LiveData<Boolean> = email.map { it.isNotEmpty() }
    private val isPasswordValid: LiveData<Boolean> = password.map { it.isNotEmpty() }
    val isLoginButtonEnabled: LiveData<Boolean> = listOf(isEmailValid, isPasswordValid).all(true)
}
