/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlin.test.Test

class RevertTest {

    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @Test
    fun `revert validate`() {
        val input: MutableLiveData<Boolean> = MutableLiveData(initialValue = false)
        val output: LiveData<Boolean> = input.revert()
        val observer = AssertObserver<Boolean>()
        output.addObserver(observer)

        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = false,
            expectOutput = true,
            expectLastObservedValue = true,
            expectObserveCount = 1,
            messagePrefix = "initialization ends"
        )

        input.value = true
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = true,
            expectOutput = false,
            expectLastObservedValue = false,
            expectObserveCount = 2,
            messagePrefix = "first change of input"
        )

        input.value = true
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = true,
            expectOutput = false,
            expectLastObservedValue = false,
            expectObserveCount = 3,
            messagePrefix = "input value reset to same value"
        )

        output.removeObserver(observer)
        input.value = false
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = false,
            expectOutput = true,
            expectLastObservedValue = false,
            expectObserveCount = 3,
            messagePrefix = "input changed after removing observer"
        )
    }
}
