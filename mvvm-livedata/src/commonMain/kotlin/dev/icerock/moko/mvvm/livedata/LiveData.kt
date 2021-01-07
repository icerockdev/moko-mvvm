/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

expect open class LiveData<T> {
    open val value: T

    fun addObserver(observer: (T) -> Unit)

    fun removeObserver(observer: (T) -> Unit)
}
