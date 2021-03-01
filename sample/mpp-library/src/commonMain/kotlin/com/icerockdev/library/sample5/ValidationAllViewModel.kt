/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.library.sample5

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.flatMap
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.viewmodel.ViewModel

private val myLazyRepository: Repo by lazy { RepoImpl() }

class RepoImpl : Repo {
    override val sourceLiveData: LiveData<String> = MutableLiveData(
        initialValue = buildString {
            @Suppress("MagicNumber")
            for (i in 0..1000000) {
                append("XAXA")
            }
        }
    )
}

interface Repo {
    val sourceLiveData: LiveData<String>
}

class ValidationAllViewModel : ViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")

    private val isEmailValid: LiveData<Boolean> =
        email.flatMap { myLazyRepository.sourceLiveData }.map { it + it }.map { it.isNotEmpty() }
    private val isPasswordValid: LiveData<Boolean> =
        MutableLiveData(false) // password.map { it.isNotEmpty() }
    val isLoginButtonEnabled: LiveData<Boolean> =
        isEmailValid // MutableLiveData(false)//listOf(isEmailValid, isPasswordValid).all(true)
}
