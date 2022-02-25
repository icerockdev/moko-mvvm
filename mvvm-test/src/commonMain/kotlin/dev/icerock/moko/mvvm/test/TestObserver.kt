/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.test

class TestObserver<T> {
    private var _invokeCount: Int = 0
    val invokeCount: Int get() = _invokeCount

    private var _lastObservedValue: T? = null
    val lastObservedValue: T? get() = _lastObservedValue

    operator fun invoke(p1: T) {
        _lastObservedValue = p1
        _invokeCount++
    }

    val lambda: (T) -> Unit = { invoke(it) }
}
