/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

actual open class MutableLiveData<T> actual constructor(initialValue: T) :
    LiveData<T>(initialValue) {

    actual override var value: T
        get() = super.value
        set(newValue) {
            changeValue(newValue)
        }

    @OptIn(DelicateCoroutinesApi::class)
    actual fun postValue(value: T) {
        GlobalScope.launch(Dispatchers.Main) { changeValue(value) }
    }
}
