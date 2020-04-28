/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import androidx.lifecycle.MutableLiveData

actual open class MutableLiveData<T> : LiveData<T> {

    actual constructor(initialValue: T) : super(MutableLiveData<T>().apply { value = initialValue })

    constructor(mutableLiveData: MutableLiveData<T>) : super(mutableLiveData)

    @Suppress("UNCHECKED_CAST")
    actual override var value: T
        get() = archLiveData.value as T
        set(newValue) {
            if(newValue == archLiveData.value) return
            (archLiveData as MutableLiveData<T>).value = newValue
        }

    actual fun postValue(value: T) {
        if(value == archLiveData.value) return
        (archLiveData as MutableLiveData<T>).postValue(value)
    }

    override fun ld(): MutableLiveData<T> = archLiveData as MutableLiveData<T>
}
