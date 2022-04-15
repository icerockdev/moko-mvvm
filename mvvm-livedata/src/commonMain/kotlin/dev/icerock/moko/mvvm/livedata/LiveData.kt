/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

expect open class LiveData<T> {
    open val value: T

    open fun addObserver(observer: (T) -> Unit)

    open fun removeObserver(observer: (T) -> Unit)
}

fun <T> LiveData<T>.addCloseableObserver(observer: (T) -> Unit): Closeable {
    addObserver(observer)
    return Closeable { removeObserver(observer) }
}
