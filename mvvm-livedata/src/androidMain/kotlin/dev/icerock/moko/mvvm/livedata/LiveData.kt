/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

// TODO #110 commonize logic of Android and iOS without broking of API and ABI.
//  we can't just extract `ld` to extension function
actual open class LiveData<T>(initialValue: T) {
    private var storedValue: T = initialValue
    private val observers = mutableListOf<(T) -> Unit>()

    protected val archLiveData: androidx.lifecycle.MutableLiveData<T> by lazy {
        object : androidx.lifecycle.MutableLiveData<T>() {
            init {
                this.setValue(this@LiveData.value)
            }

            private val commonObserver: (T) -> Unit = {
                this.setValue(it)
            }

            override fun onActive() {
                super.onActive()

                this@LiveData.addObserver(commonObserver)
            }

            override fun onInactive() {
                super.onInactive()

                this@LiveData.removeObserver(commonObserver)
            }

            override fun setValue(value: T) {
                super.setValue(value)

                if (value == this@LiveData.value) return

                this@LiveData.changeValue(value)
            }
        }
    }

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

    open fun ld(): androidx.lifecycle.LiveData<T> = archLiveData
}
