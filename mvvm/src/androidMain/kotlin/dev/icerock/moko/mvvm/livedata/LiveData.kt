/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import androidx.lifecycle.Observer
import androidx.lifecycle.LiveData as ArchLiveData

actual open class LiveData<T> {
    protected val archLiveData: ArchLiveData<T>
    private val observers = mutableMapOf<(T) -> Unit, Observer<T>>()

    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(liveData: ArchLiveData<T>) {
        archLiveData = liveData
    }

    @Suppress("UNCHECKED_CAST")
    actual open val value: T
        get() = archLiveData.value as T

    actual fun addObserver(observer: (T) -> Unit) {
        val archObserver = Observer<T> { value ->
            if(value is T) observer(value)
        }
        observers[observer] = archObserver

        archLiveData.observeForever(archObserver)
    }

    actual fun removeObserver(observer: (T) -> Unit) {
        val archObserver = observers.remove(observer) ?: return
        archLiveData.removeObserver(archObserver)
    }

    open fun ld(): ArchLiveData<T> = archLiveData
}
