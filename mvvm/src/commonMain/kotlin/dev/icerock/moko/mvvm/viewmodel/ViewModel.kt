/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.viewmodel

import kotlinx.coroutines.CoroutineScope

expect open class ViewModel() {
    protected val viewModelScope: CoroutineScope

    protected open fun onCleared()
}
