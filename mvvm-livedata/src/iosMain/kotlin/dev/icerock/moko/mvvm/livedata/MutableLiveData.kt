/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_current_queue
import platform.darwin.dispatch_get_main_queue

actual open class MutableLiveData<T> actual constructor(initialValue: T) :
    LiveData<T>(initialValue) {

    actual override var value: T
        get() = super.value
        set(newValue) {
            changeValue(newValue)
        }

    actual fun postValue(value: T) {
        val mainQueue = dispatch_get_main_queue()
        if (dispatch_get_current_queue() == mainQueue) {
            changeValue(value)
        } else {
            dispatch_async(mainQueue) { changeValue(value) }
        }
    }
}
