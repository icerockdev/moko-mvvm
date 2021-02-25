/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import androidx.lifecycle.Observer

actual open class LiveData<T> protected constructor(mutableLiveData: androidx.lifecycle.MutableLiveData<T>) {
    protected val archLiveData: androidx.lifecycle.MutableLiveData<T> = mutableLiveData
    private val observers = mutableMapOf<(T) -> Unit, Observer<T>>()

    @Suppress("UNCHECKED_CAST")
    actual open val value: T
        get() = archLiveData.value as T

    /** change value and notify observers about change */
    protected actual fun changeValue(value: T) {
        archLiveData.value = value
    }

    actual fun addObserver(observer: (T) -> Unit) {
        if (observers.isEmpty()) onActive()

        val archObserver = Observer<T> { value ->
            if (value is T) observer(value)
        }
        observers[observer] = archObserver

        archLiveData.observeForever(archObserver)
    }

    actual fun removeObserver(observer: (T) -> Unit) {
        val archObserver = observers.remove(observer) ?: return
        archLiveData.removeObserver(archObserver)

        if (observers.isEmpty()) onInactive()
    }

    /** Called when observers count changed from 0 to 1 */
    protected actual open fun onActive() {}

    /** Called when observers count changed from 1 to 0 */
    protected actual open fun onInactive() {}

    /** Will be true if any observer already added */
    actual val isActive: Boolean get() = observers.isNotEmpty()

    open fun ld(): androidx.lifecycle.LiveData<T> = archLiveData
}
