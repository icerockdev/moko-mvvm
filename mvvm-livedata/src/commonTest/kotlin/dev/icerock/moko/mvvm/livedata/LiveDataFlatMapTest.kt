/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.test.Test
import kotlin.test.assertEquals

class LiveDataFlatMapTest {
    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @Test
    fun `flat map valid`() {
        var counter: Int = 0
        val input = MutableLiveData<Int>(initialValue = 0)
        val output = input.flatMap { inputValue ->
            val newOutput = MutableLiveData(initialValue = inputValue)
            newOutput.value += counter++
            newOutput
        }
        var observerCounter: Int = 0
        output.addObserver { observerCounter++ }

        assertEquals(
            expected = 0,
            actual = output.value
        )
        assertEquals(
            expected = 1,
            actual = observerCounter
        )

        input.value = 1
        assertEquals(
            expected = 2,
            actual = output.value
        )
        assertEquals(
            expected = 2,
            actual = observerCounter
        )

        input.value = 2
        assertEquals(
            expected = 4,
            actual = output.value
        )
        assertEquals(
            expected = 3,
            actual = observerCounter
        )
    }

    @Test
    fun `flat map shared`() {
        val input = MutableLiveData<Int>(initialValue = 0)
        val internalLiveData = MutableLiveData(initialValue = 1)
        val output = input.flatMap { internalLiveData }
        var observerCounter: Int = 0
        output.addObserver { observerCounter++ }

        assertEquals(
            expected = 1,
            actual = output.value
        )
        assertEquals(
            expected = 1,
            actual = observerCounter
        )

        input.value = 1
        assertEquals(
            expected = 1,
            actual = output.value
        )
        assertEquals(
            expected = 2,
            actual = observerCounter
        )

        input.value = 2
        assertEquals(
            expected = 1,
            actual = output.value
        )
        assertEquals(
            expected = 3,
            actual = observerCounter
        )

        input.value = 3
        assertEquals(
            expected = 1,
            actual = output.value
        )
        assertEquals(
            expected = 4,
            actual = observerCounter
        )

        internalLiveData.value = 2
        assertEquals(
            expected = 2,
            actual = output.value
        )
        assertEquals(
            expected = 5,
            actual = observerCounter
        )
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
}