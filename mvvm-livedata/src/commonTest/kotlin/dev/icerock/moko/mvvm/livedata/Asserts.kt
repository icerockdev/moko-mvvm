/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import kotlin.test.assertEquals

class AssertObserver<T> : (T) -> Unit {
    private var _invokeCount: Int = 0
    val invokeCount: Int get() = _invokeCount

    private var _lastObservedValue: T? = null
    val lastObservedValue: T? get() = _lastObservedValue

    override fun invoke(p1: T) {
        _lastObservedValue = p1
        _invokeCount++
    }
}

fun <IT, OT> assert(
    input: LiveData<IT>,
    output: LiveData<OT>,
    outputObserver: AssertObserver<OT>,
    expectInput: IT,
    expectOutput: OT,
    expectLastObservedValue: OT,
    expectObserveCount: Int,
    messagePrefix: String
) {
    assertEquals(
        expected = expectInput,
        actual = input.value,
        message = "$messagePrefix input value invalid"
    )
    assertEquals(
        expected = expectOutput,
        actual = output.value,
        message = "$messagePrefix output value invalid"
    )
    assertEquals(
        expected = expectLastObservedValue,
        actual = outputObserver.lastObservedValue,
        message = "$messagePrefix observer last value invalid"
    )
    assertEquals(
        expected = expectObserveCount,
        actual = outputObserver.invokeCount,
        message = "$messagePrefix observer invoke times invalid"
    )
}
