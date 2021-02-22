/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlin.test.Test

class IfNullTest {

    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @Test
    fun `required validate`() {
        val input: MutableLiveData<Int?> = MutableLiveData(initialValue = null)
        val output: LiveData<Int> = input.required(initialValue = 1)
        val observer = AssertObserver<Int>()
        output.addObserver(observer)

        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = null,
            expectOutput = 1,
            expectLastObservedValue = 1,
            expectObserveCount = 1,
            messagePrefix = "initialization ends"
        )

        input.value = 10
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = 10,
            expectOutput = 10,
            expectLastObservedValue = 10,
            expectObserveCount = 2,
            messagePrefix = "input value not null"
        )

        input.value = null
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = null,
            expectOutput = 10,
            expectLastObservedValue = 10,
            expectObserveCount = 2,
            messagePrefix = "input value cleared to null"
        )

        input.value = 11
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = 11,
            expectOutput = 11,
            expectLastObservedValue = 11,
            expectObserveCount = 3,
            messagePrefix = "input value again not null"
        )

        output.removeObserver(observer)
        input.value = 12
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = 12,
            expectOutput = 12,
            expectLastObservedValue = 11,
            expectObserveCount = 3,
            messagePrefix = "input changed after removing observer"
        )
    }

    @Test
    fun `orEmpty validate`() {
        val input: MutableLiveData<String?> = MutableLiveData(initialValue = null)
        val output: LiveData<String> = input.orEmpty()
        val observer = AssertObserver<String>()
        output.addObserver(observer)

        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = null,
            expectOutput = "",
            expectLastObservedValue = "",
            expectObserveCount = 1,
            messagePrefix = "initialization ends"
        )

        input.value = "test"
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = "test",
            expectOutput = "test",
            expectLastObservedValue = "test",
            expectObserveCount = 2,
            messagePrefix = "input value not null"
        )

        input.value = null
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = null,
            expectOutput = "",
            expectLastObservedValue = "",
            expectObserveCount = 3,
            messagePrefix = "input value cleared to null"
        )

        input.value = ""
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = "",
            expectOutput = "",
            expectLastObservedValue = "",
            expectObserveCount = 4,
            messagePrefix = "input value again not null"
        )

        output.removeObserver(observer)
        input.value = "end"
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = "end",
            expectOutput = "end",
            expectLastObservedValue = "",
            expectObserveCount = 4,
            messagePrefix = "input changed after removing observer"
        )
    }
}
