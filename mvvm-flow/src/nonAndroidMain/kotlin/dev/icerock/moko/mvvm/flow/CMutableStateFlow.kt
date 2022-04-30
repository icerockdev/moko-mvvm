/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

actual class CMutableStateFlow<T> actual constructor(
    private val flow: MutableStateFlow<T>
) : CStateFlow<T>(flow), MutableStateFlow<T> {

    override var value: T
        get() = super.value
        set(value) {
            flow.value = value
        }

    override val subscriptionCount: StateFlow<Int> = flow.subscriptionCount

    override suspend fun emit(value: T) = flow.emit(value)

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() = flow.resetReplayCache()

    override fun tryEmit(value: T): Boolean = flow.tryEmit(value)

    override fun compareAndSet(expect: T, update: T): Boolean = flow.compareAndSet(expect, update)
}
