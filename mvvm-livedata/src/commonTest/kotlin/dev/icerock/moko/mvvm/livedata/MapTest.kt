/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlin.test.Test

class MapTest {

    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @Test
    fun `map validate`() {
        val input: MutableLiveData<Int> = MutableLiveData(initialValue = 1)
        val output: LiveData<Long> = input.map { it * -1L }
        val observer = AssertObserver<Long>()
        output.addObserver(observer)

        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = 1,
            expectOutput = -1L,
            expectLastObservedValue = -1L,
            expectObserveCount = 1,
            messagePrefix = "initialization ends"
        )

        input.value = 2
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = 2,
            expectOutput = -2L,
            expectLastObservedValue = -2L,
            expectObserveCount = 2,
            messagePrefix = "first change of input"
        )

        input.value = 2
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = 2,
            expectOutput = -2L,
            expectLastObservedValue = -2L,
            expectObserveCount = 3,
            messagePrefix = "input value reset to same value"
        )

        input.postValue(3)
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = 3,
            expectOutput = -3L,
            expectLastObservedValue = -3L,
            expectObserveCount = 4,
            messagePrefix = "input changed by postValue"
        )

        output.removeObserver(observer)
        input.value = 4
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = 4,
            expectOutput = -4L,
            expectLastObservedValue = -3L,
            expectObserveCount = 4,
            messagePrefix = "input changed after removing observer"
        )
    }

    @Test
    fun `mapOrNull validate`() {
        val input: MutableLiveData<Int?> = MutableLiveData(initialValue = 1)
        val output: LiveData<Long?> = input.mapOrNull { it * -1L }
        val observer = AssertObserver<Long?>()
        output.addObserver(observer)

        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = 1,
            expectOutput = -1L,
            expectLastObservedValue = -1L,
            expectObserveCount = 1,
            messagePrefix = "initialization ends"
        )

        input.value = 2
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = 2,
            expectOutput = -2L,
            expectLastObservedValue = -2L,
            expectObserveCount = 2,
            messagePrefix = "first change input"
        )

        input.value = null
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = null,
            expectOutput = null,
            expectLastObservedValue = null,
            expectObserveCount = 3,
            messagePrefix = "null input"
        )

        output.removeObserver(observer)
        input.value = 3
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = 3,
            expectOutput = -3L,
            expectLastObservedValue = null,
            expectObserveCount = 3,
            messagePrefix = "change input after removing observer"
        )
    }

    @Test
    fun `mapTrueOrNull validate`() {
        val input: MutableLiveData<Boolean?> = MutableLiveData(initialValue = null)
        val output: LiveData<Int?> = input.mapTrueOrNull { 1 }
        val observer = AssertObserver<Int?>()
        output.addObserver(observer)

        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = null,
            expectOutput = null,
            expectLastObservedValue = null,
            expectObserveCount = 1,
            messagePrefix = "initialization with null"
        )

        input.value = false
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = false,
            expectOutput = null,
            expectLastObservedValue = null,
            expectObserveCount = 2,
            messagePrefix = "false value"
        )

        input.value = true
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = true,
            expectOutput = 1,
            expectLastObservedValue = 1,
            expectObserveCount = 3,
            messagePrefix = "true value"
        )

        output.removeObserver(observer)
        input.value = null
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = null,
            expectOutput = null,
            expectLastObservedValue = 1,
            expectObserveCount = 3,
            messagePrefix = "change input after removing observer"
        )
    }
}
