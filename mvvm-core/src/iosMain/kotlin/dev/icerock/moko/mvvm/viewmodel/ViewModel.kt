/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.viewmodel

import dev.icerock.moko.mvvm.UI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.native.internal.GC

@Suppress("EmptyDefaultConstructor")
actual open class ViewModel actual constructor() {
    // for now dispatcher fixed on Main. after implementing multithread coroutines on native - we can change it
    protected actual val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.UI)

    actual open fun onCleared() {
        viewModelScope.cancel()

        dispatch_async(dispatch_get_main_queue()) { GC.collect() }
    }
}
