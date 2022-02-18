/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_current_queue
import platform.darwin.dispatch_get_main_queue

actual fun <T> MutableLiveData<T>.postValue(value: T) {
    val mainQueue = dispatch_get_main_queue()
    if (dispatch_get_current_queue() == mainQueue) {
        this.value = value
    } else {
        dispatch_async(mainQueue) { this.value = value }
    }
}
