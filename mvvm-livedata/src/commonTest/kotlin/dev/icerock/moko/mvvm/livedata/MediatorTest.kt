/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlin.test.Test
import kotlin.test.assertEquals

class MediatorTest {

    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @Test
    fun `live data mediator testing`() {
        val firstLd: MutableLiveData<Int> = MutableLiveData(initialValue = 1)
        val secondLd: MutableLiveData<Int> = MutableLiveData(initialValue = 2)
        val mediatorLd: LiveData<Int> = MediatorLiveData(initialValue = 3).apply {
            addSource(firstLd) {
                this.value = it * 2
            }
            addSource(secondLd) {
                this.value = it * 3
            }
        }

        var observedCounter = 0
        var lastValue: Int? = null
        val observer: (Int) -> Unit = {
            observedCounter++
            lastValue = it
        }
        mediatorLd.addObserver(observer)

        "we should got initial value".let { msg ->
            assertEquals(expected = 1, actual = firstLd.value, message = msg)
            assertEquals(expected = 2, actual = secondLd.value, message = msg)
            assertEquals(expected = 6, actual = mediatorLd.value, message = msg)
            assertEquals(expected = 6, actual = lastValue, message = msg)
            assertEquals(expected = 1, actual = observedCounter, message = msg)
        }

        firstLd.value = 2
        "we should got changes from first source".let { msg ->
            assertEquals(expected = 2, actual = firstLd.value, message = msg)
            assertEquals(expected = 2, actual = secondLd.value, message = msg)
            assertEquals(expected = 4, actual = mediatorLd.value, message = msg)
            assertEquals(expected = 4, actual = lastValue, message = msg)
            assertEquals(expected = 2, actual = observedCounter, message = msg)
        }

        secondLd.value = 3
        "we should got changes from second source".let { msg ->
            assertEquals(expected = 2, actual = firstLd.value, message = msg)
            assertEquals(expected = 3, actual = secondLd.value, message = msg)
            assertEquals(expected = 9, actual = mediatorLd.value, message = msg)
            assertEquals(expected = 9, actual = lastValue, message = msg)
            assertEquals(expected = 3, actual = observedCounter, message = msg)
        }

        mediatorLd.removeObserver(observer)
        firstLd.value = 5
        secondLd.value = 7
        "observer was removed - we should not got any changes".let { msg ->
            assertEquals(expected = 5, actual = firstLd.value, message = msg)
            assertEquals(expected = 7, actual = secondLd.value, message = msg)
            assertEquals(expected = 21, actual = mediatorLd.value, message = msg)
            assertEquals(expected = 9, actual = lastValue, message = msg)
            assertEquals(expected = 3, actual = observedCounter, message = msg)
        }
    }
}
