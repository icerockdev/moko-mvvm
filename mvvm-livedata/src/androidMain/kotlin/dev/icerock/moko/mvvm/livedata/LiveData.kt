/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import androidx.lifecycle.Observer

actual open class LiveData<T> {
    protected val archLiveData: androidx.lifecycle.MutableLiveData<T> =
        object : androidx.lifecycle.MutableLiveData<T>() {
            override fun onActive() {
                super.onActive()

                _activeCount++
            }

            override fun onInactive() {
                super.onInactive()

                _activeCount--
            }
        }
    private val observers = mutableMapOf<(T) -> Unit, Observer<T>>()

    private var _activeCount: Int = 0
        set(value) {
            val old = field
            field = value

            if (old == 0) onActive()
            else if (value == 0) onInactive()
        }

    @Suppress("UNCHECKED_CAST")
    actual open val value: T
        get() = archLiveData.value as T

    /** change value and notify observers about change */
    protected actual fun changeValue(value: T) {
        archLiveData.value = value
    }

    actual fun addObserver(observer: (T) -> Unit) {
        if (observers.isEmpty()) _activeCount++

        val archObserver = Observer<T> { value ->
            if (value is T) observer(value)
        }
        observers[observer] = archObserver

        archLiveData.observeForever(archObserver)
    }

    actual fun removeObserver(observer: (T) -> Unit) {
        val archObserver = observers.remove(observer) ?: return
        archLiveData.removeObserver(archObserver)

        if (observers.isEmpty()) _activeCount--
    }

    /** Called when observers count changed from 0 to 1 */
    protected actual open fun onActive() {
        println("activate $this")
    }

    /** Called when observers count changed from 1 to 0 */
    protected actual open fun onInactive() {
        println("deactivate $this")
    }

    /** Will be true if any observer already added */
    actual val isActive: Boolean get() = _activeCount > 0

    open fun ld(): androidx.lifecycle.LiveData<T> = archLiveData
}
