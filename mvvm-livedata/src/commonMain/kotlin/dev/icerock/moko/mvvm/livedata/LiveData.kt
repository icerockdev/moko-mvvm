/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

expect open class LiveData<T> {
    open val value: T

    /** change value and notify observers about change */
    protected fun changeValue(value: T)

    /** Add new observer */
    fun addObserver(observer: (T) -> Unit)

    /** Remove already added observer */
    fun removeObserver(observer: (T) -> Unit)

    /** Called when observers count changed from 0 to 1 */
    protected open fun onActive()

    /** Called when observers count changed from 1 to 0 */
    protected open fun onInactive()

    /** Will be true if any observer already added */
    val isActive: Boolean
}

fun <T> LiveData<T>.addCloseableObserver(observer: (T) -> Unit): Closeable {
    addObserver(observer)
    return Closeable { removeObserver(observer) }
}
