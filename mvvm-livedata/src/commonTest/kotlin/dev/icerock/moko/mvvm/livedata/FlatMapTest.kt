/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlin.test.Test
import kotlin.test.assertEquals

class FlatMapTest {

    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @Test
    fun `flat map valid`() {
        var counter = 0
        val input = MutableLiveData(initialValue = 0)
        val output = input.flatMap { inputValue ->
            val newOutput = MutableLiveData(initialValue = inputValue)
            newOutput.value += counter++
            newOutput
        }
        var observerCounter = 0
        output.addObserver { observerCounter++ }

        assertEquals(expected = 0, actual = output.value)
        assertEquals(expected = 1, actual = observerCounter)

        input.value = 1
        assertEquals(expected = 2, actual = output.value)
        assertEquals(expected = 2, actual = observerCounter)

        input.value = 2
        assertEquals(expected = 4, actual = output.value)
        assertEquals(expected = 3, actual = observerCounter)
    }

    @Test
    fun `flat map shared`() {
        val input = MutableLiveData(initialValue = 0)
        val internalLiveData = MutableLiveData(initialValue = 1)
        val output = input.flatMap { internalLiveData }
        var observerCounter = 0
        output.addObserver { observerCounter++ }

        assertEquals(expected = 1, actual = output.value)
        assertEquals(expected = 1, actual = observerCounter)

        input.value = 1
        assertEquals(expected = 1, actual = output.value)
        assertEquals(expected = 2, actual = observerCounter)

        input.value = 2
        assertEquals(expected = 1, actual = output.value)
        assertEquals(expected = 3, actual = observerCounter)

        input.value = 3
        assertEquals(expected = 1, actual = output.value)
        assertEquals(expected = 4, actual = observerCounter)

        internalLiveData.value = 2
        assertEquals(expected = 2, actual = output.value)
        assertEquals(expected = 5, actual = observerCounter)
    }

    @Test
    fun `flat map shared source`() {
        val sharedSource = MutableLiveData(initialValue = 0)

        for (i in 0..10) {
            val tmpLiveData = MutableLiveData(initialValue = i)
            tmpLiveData.flatMap { sharedSource }.addObserver { println("hello $i $it") }
        }

        sharedSource.value = 1
    }

    @Test
    fun `flatMapBuffered validate`() {
        val input: MutableLiveData<Int> = MutableLiveData(initialValue = 2)
        val input2: MutableLiveData<Int> = MutableLiveData(initialValue = 1)
        val output: LiveData<String> = input.flatMapBuffered { current, new ->
            MutableLiveData("$current:$new").mergeWith(input2) { str, int ->
                "$str:$int"
            }
        }
        val observer = AssertObserver<String>()
        output.addObserver(observer)

        // TODO maybe we should change logic of flatMapBuffered at initialization step?
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = 2,
            expectOutput = "2:2:1",
            expectLastObservedValue = "2:2:1",
            expectObserveCount = 1,
            messagePrefix = "initialization with null"
        )

        input.value = 3
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = 3,
            expectOutput = "2:3:1",
            expectLastObservedValue = "2:3:1",
            expectObserveCount = 2,
            messagePrefix = "new value"
        )

        input2.value = 2
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = 3,
            expectOutput = "2:3:2",
            expectLastObservedValue = "2:3:2",
            expectObserveCount = 3,
            messagePrefix = "new value"
        )

        output.removeObserver(observer)
        input.value = 4
        input2.value = 3
        assert(
            input = input,
            output = output,
            outputObserver = observer,
            expectInput = 4,
            expectOutput = "3:4:3",
            expectLastObservedValue = "2:3:2",
            expectObserveCount = 3,
            messagePrefix = "change input after removing observer"
        )
    }
}
