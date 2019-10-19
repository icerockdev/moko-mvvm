/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

actual open class ViewModel actual constructor() {
    // for now dispatcher fixed on Main. after implementing multithread coroutines on native - we can change it
    protected actual val viewModelScope: CoroutineScope = CoroutineScope(UIDispatcher())

    protected actual open fun onCleared() {
        viewModelScope.cancel()
    }
}
