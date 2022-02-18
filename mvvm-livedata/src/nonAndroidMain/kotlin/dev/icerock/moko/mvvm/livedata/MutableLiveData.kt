/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

actual open class MutableLiveData<T> actual constructor(
    initialValue: T
) : LiveData<T>(initialValue) {

    actual override var value: T
        get() = super.value
        set(newValue) {
            changeValue(newValue)
        }
}
