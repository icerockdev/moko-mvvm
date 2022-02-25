/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.library.sample4

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.mediatorOf
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class ValidationMergeViewModel : ViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")

    val isLoginButtonEnabled: LiveData<Boolean> = mediatorOf(email, password) { email, password ->
        email.isNotEmpty() && password.isNotEmpty()
    }
}
