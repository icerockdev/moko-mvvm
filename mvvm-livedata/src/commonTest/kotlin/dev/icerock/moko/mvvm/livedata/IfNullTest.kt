/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.test.TestObserver
import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlin.test.Test

@Suppress("LongMethod")
class IfNullTest {

    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @Test
    fun `required validate`() {
        val input: MutableLiveData<Int?> = MutableLiveData(initialValue = null)
        val output: LiveData<Int> = input.required(initialValue = 1)
        val observer = TestObserver<Int>()
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
            expectOutput = 11,
            expectLastObservedValue = 11,
            expectObserveCount = 3,
            messagePrefix = "input changed after removing observer"
        )
    }

    @Test
    fun `String orEmpty validate`() {
        val input: MutableLiveData<String?> = MutableLiveData(initialValue = null)
        val output: LiveData<String> = input.orEmpty()
        val observer = TestObserver<String>()
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

    @Test
    fun `List orEmpty validate`() {
        val input: MutableLiveData<List<Int>?> = MutableLiveData(initialValue = null)
        val output: LiveData<List<Int>> = input.orEmpty()
        val observer = TestObserver<List<Int>>()
        output.addObserver(observer)

        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = null,
            expectOutput = emptyList(),
            expectLastObservedValue = emptyList(),
            expectObserveCount = 1,
            messagePrefix = "initialization ends"
        )

        input.value = listOf(1, 2)
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = listOf(1, 2),
            expectOutput = listOf(1, 2),
            expectLastObservedValue = listOf(1, 2),
            expectObserveCount = 2,
            messagePrefix = "input value not null"
        )

        input.value = null
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = null,
            expectOutput = emptyList(),
            expectLastObservedValue = emptyList(),
            expectObserveCount = 3,
            messagePrefix = "input value cleared to null"
        )

        output.removeObserver(observer)
        input.value = listOf(1)
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = listOf(1),
            expectOutput = listOf(1),
            expectLastObservedValue = emptyList(),
            expectObserveCount = 3,
            messagePrefix = "input changed after removing observer"
        )
    }
}
