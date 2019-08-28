/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

actual open class ViewModel actual constructor() {
    protected actual val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    protected actual open fun onCleared() {
        coroutineScope.cancel()
    }
}
