/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

// TODO #110 commonize logic of Android and iOS without broking of API and ABI.
actual open class LiveData<T>(initialValue: T) {
    private var storedValue: T = initialValue
    private val observers = mutableListOf<(T) -> Unit>()

    actual open val value: T
        get() = storedValue

    protected actual fun changeValue(value: T) {
        storedValue = value

        observers.forEach { it(value) }
    }

    actual fun addObserver(observer: (T) -> Unit) {
        if (observers.isEmpty()) onActive()

        observer(value)
        observers.add(observer)
    }

    actual fun removeObserver(observer: (T) -> Unit) {
        if (observers.remove(observer).not()) return

        if (observers.isEmpty()) onInactive()
    }

    /** Called when observers count changed from 0 to 1 */
    protected actual open fun onActive() {}

    /** Called when observers count changed from 1 to 0 */
    protected actual open fun onInactive() {}

    /** Will be true if any observer already added */
    actual val isActive: Boolean get() = observers.isNotEmpty()
}
