/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlin.test.Test
import kotlin.test.assertEquals

class MergeTest {

    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @Test
    fun `mergeWith test`() {
        val ld: MutableLiveData<Int> = MutableLiveData(10)
        val ldBool: MutableLiveData<Boolean> = MutableLiveData(false)

        var mergeWithCounter = 0

        val mapLd: LiveData<Long> = ld.mergeWith(ldBool) { a1, a2 ->
            mergeWithCounter++
            if (a2) a1.toLong() else -a1.toLong()
        }

        // observer required with cold livedata
        mapLd.addObserver { }

        assertEquals(actual = mergeWithCounter, expected = 3)
        assertEquals(expected = -10, actual = mapLd.value)

        ldBool.value = true

        assertEquals(actual = mergeWithCounter, expected = 4)
        assertEquals(expected = 10, actual = mapLd.value)

        ld.value = 11

        assertEquals(actual = mergeWithCounter, expected = 5)
        assertEquals(expected = 11, actual = mapLd.value)
    }

    @Test
    fun `live data merge testing`() {
        val firstLd: MutableLiveData<Int> = MutableLiveData(initialValue = 0)
        val secondLd: MutableLiveData<Int> = MutableLiveData(initialValue = 0)
        val mergedLd: LiveData<Int> = firstLd.mergeWith(secondLd) { first, second ->
            first * second
        }

        var observedCounter = 0
        var lastValue: Int? = null
        val observer: (Int) -> Unit = {
            observedCounter++
            lastValue = it
        }
        mergedLd.addObserver(observer)

        "we should got initial value".let { msg ->
            assertEquals(expected = 0, actual = firstLd.value, message = msg)
            assertEquals(expected = 0, actual = secondLd.value, message = msg)
            assertEquals(expected = 0, actual = mergedLd.value, message = msg)
            assertEquals(expected = 0, actual = lastValue, message = msg)
            assertEquals(expected = 1, actual = observedCounter, message = msg)
        }

        firstLd.value = 1
        "result of merge not changed, but we should got notification".let { msg ->
            assertEquals(expected = 1, actual = firstLd.value, message = msg)
            assertEquals(expected = 0, actual = secondLd.value, message = msg)
            assertEquals(expected = 0, actual = mergedLd.value, message = msg)
            assertEquals(expected = 0, actual = lastValue, message = msg)
            assertEquals(expected = 2, actual = observedCounter, message = msg)
        }

        secondLd.value = 2
        "merged value changed, we should got changes".let { msg ->
            assertEquals(expected = 1, actual = firstLd.value, message = msg)
            assertEquals(expected = 2, actual = secondLd.value, message = msg)
            assertEquals(expected = 2, actual = mergedLd.value, message = msg)
            assertEquals(expected = 2, actual = lastValue, message = msg)
            assertEquals(expected = 3, actual = observedCounter, message = msg)
        }

        mergedLd.removeObserver(observer)
        firstLd.value = 4
        "observer was removed - we should not got any changes".let { msg ->
            assertEquals(expected = 4, actual = firstLd.value, message = msg)
            assertEquals(expected = 2, actual = secondLd.value, message = msg)
            assertEquals(expected = 2, actual = mergedLd.value, message = msg)
            assertEquals(expected = 2, actual = lastValue, message = msg)
            assertEquals(expected = 3, actual = observedCounter, message = msg)
        }
    }
}
