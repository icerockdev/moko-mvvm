/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

actual open class ViewModel actual constructor() : ViewModel() {
    protected actual val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    actual override fun onCleared() {
        super.onCleared()

        coroutineScope.cancel()
    }
}
