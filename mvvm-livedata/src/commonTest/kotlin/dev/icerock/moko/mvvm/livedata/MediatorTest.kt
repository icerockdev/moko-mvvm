/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlin.test.Test

class MediatorTest {

    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @Test
    fun `Mediator validate`() {
        val input1: MutableLiveData<Int> = MutableLiveData(initialValue = 1)
        val input2: MutableLiveData<Int> = MutableLiveData(initialValue = 2)
        val output: LiveData<Int> = MediatorLiveData(initialValue = 3).apply {
            addSource(input1) {
                this.value = it * 2
            }
            addSource(input2) {
                this.value = it * 3
            }
        }
        val observer = AssertObserver<Int>()
        output.addObserver(observer)

        assert(
            input = input1,
            output = output,
            outputObserver = observer,
            expectInput = 1,
            expectOutput = 6,
            expectLastObservedValue = 6,
            expectObserveCount = 1,
            messagePrefix = "initialization step"
        )

        input1.value = 2
        assert(
            input = input1,
            output = output,
            outputObserver = observer,
            expectInput = 2,
            expectOutput = 4,
            expectLastObservedValue = 4,
            expectObserveCount = 2,
            messagePrefix = "first input changed"
        )

        input2.value = 3
        assert(
            input = input2,
            output = output,
            outputObserver = observer,
            expectInput = 3,
            expectOutput = 9,
            expectLastObservedValue = 9,
            expectObserveCount = 3,
            messagePrefix = "second input changed"
        )

        output.removeObserver(observer)
        input1.value = 5
        input2.value = 7
        assert(
            input = input2,
            output = output,
            outputObserver = observer,
            expectInput = 7,
            expectOutput = 21,
            expectLastObservedValue = 9,
            expectObserveCount = 3,
            messagePrefix = "observer removed"
        )
    }
}
