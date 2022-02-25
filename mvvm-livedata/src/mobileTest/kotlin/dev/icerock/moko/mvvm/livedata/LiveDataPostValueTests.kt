/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlin.test.Test
import kotlin.test.assertEquals

class LiveDataPostValueTests {

    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @Test
    fun liveDataObserverTesting() {
        val ld: MutableLiveData<Int> = MutableLiveData(initialValue = 0)

        var observedCounter = 0
        var lastValue: Int? = null
        val observer: (Int) -> Unit = {
            observedCounter++
            lastValue = it
        }
        ld.addObserver(observer)

        "we should got initial value".let { msg ->
            assertEquals(expected = 0, actual = ld.value, message = msg)
            assertEquals(expected = 0, actual = lastValue, message = msg)
            assertEquals(expected = 1, actual = observedCounter, message = msg)
        }

        ld.value = 1
        "we should got changes".let { msg ->
            assertEquals(expected = 1, actual = ld.value, message = msg)
            assertEquals(expected = 1, actual = lastValue, message = msg)
            assertEquals(expected = 2, actual = observedCounter, message = msg)
        }

        ld.value = 1
        "value set but not changed, we should got notification".let { msg ->
            assertEquals(expected = 1, actual = ld.value, message = msg)
            assertEquals(expected = 1, actual = lastValue, message = msg)
            assertEquals(expected = 3, actual = observedCounter, message = msg)
        }

        ld.postValue(2)
        "postValue should set value on mainThread, so change should be applied".let { msg ->
            assertEquals(expected = 2, actual = ld.value, message = msg)
            assertEquals(expected = 2, actual = lastValue, message = msg)
            assertEquals(expected = 4, actual = observedCounter, message = msg)
        }

        ld.removeObserver(observer)
        ld.value = 3
        "observer was removed - we should not got any changes".let { msg ->
            assertEquals(expected = 3, actual = ld.value, message = msg)
            assertEquals(expected = 2, actual = lastValue, message = msg)
            assertEquals(expected = 4, actual = observedCounter, message = msg)
        }
    }

    @Test
    fun liveDataMapTesting() {
        val ld: MutableLiveData<Int> = MutableLiveData(initialValue = 1)
        val mapLd: LiveData<Int> = ld.map { it * -1 }

        var observedCounter = 0
        var lastValue: Int? = null
        val observer: (Int) -> Unit = {
            observedCounter++
            lastValue = it
        }
        mapLd.addObserver(observer)

        "we should got initial value".let { msg ->
            assertEquals(expected = 1, actual = ld.value, message = msg)
            assertEquals(expected = -1, actual = mapLd.value, message = msg)
            assertEquals(expected = -1, actual = lastValue, message = msg)
            assertEquals(expected = 1, actual = observedCounter, message = msg)
        }

        ld.value = 2
        "we should got changes".let { msg ->
            assertEquals(expected = 2, actual = ld.value, message = msg)
            assertEquals(expected = -2, actual = mapLd.value, message = msg)
            assertEquals(expected = -2, actual = lastValue, message = msg)
            assertEquals(expected = 2, actual = observedCounter, message = msg)
        }

        ld.value = 2
        "value set but not changed, we should got notification".let { msg ->
            assertEquals(expected = 2, actual = ld.value, message = msg)
            assertEquals(expected = -2, actual = mapLd.value, message = msg)
            assertEquals(expected = -2, actual = lastValue, message = msg)
            assertEquals(expected = 3, actual = observedCounter, message = msg)
        }

        ld.postValue(3)
        "postValue should set value on mainThread, so change should be applied".let { msg ->
            assertEquals(expected = 3, actual = ld.value, message = msg)
            assertEquals(expected = -3, actual = mapLd.value, message = msg)
            assertEquals(expected = -3, actual = lastValue, message = msg)
            assertEquals(expected = 4, actual = observedCounter, message = msg)
        }

        mapLd.removeObserver(observer)
        ld.value = 4
        "observer was removed - we should not got any changes".let { msg ->
            assertEquals(expected = 4, actual = ld.value, message = msg)
            assertEquals(expected = -4, actual = mapLd.value, message = msg)
            assertEquals(expected = -3, actual = lastValue, message = msg)
            assertEquals(expected = 4, actual = observedCounter, message = msg)
        }
    }

    @Test
    fun liveDataDistinctTesting() {
        val ld: MutableLiveData<Int> = MutableLiveData(initialValue = 1)
        val distinctLd: LiveData<Int> = ld.distinct()

        var observedCounter = 0
        var lastValue: Int? = null
        val observer: (Int) -> Unit = {
            observedCounter++
            lastValue = it
        }
        distinctLd.addObserver(observer)

        "we should got initial value".let { msg ->
            assertEquals(expected = 1, actual = ld.value, message = msg)
            assertEquals(expected = 1, actual = distinctLd.value, message = msg)
            assertEquals(expected = 1, actual = lastValue, message = msg)
            assertEquals(expected = 1, actual = observedCounter, message = msg)
        }

        ld.value = 2
        "we should got changes".let { msg ->
            assertEquals(expected = 2, actual = ld.value, message = msg)
            assertEquals(expected = 2, actual = distinctLd.value, message = msg)
            assertEquals(expected = 2, actual = lastValue, message = msg)
            assertEquals(expected = 2, actual = observedCounter, message = msg)
        }

        ld.value = 2
        "value set but not changed, we should not got any changes".let { msg ->
            assertEquals(expected = 2, actual = ld.value, message = msg)
            assertEquals(expected = 2, actual = distinctLd.value, message = msg)
            assertEquals(expected = 2, actual = lastValue, message = msg)
            assertEquals(expected = 2, actual = observedCounter, message = msg)
        }

        ld.postValue(2)
        "postValue set but not changed, we should not got any changes".let { msg ->
            assertEquals(expected = 2, actual = ld.value, message = msg)
            assertEquals(expected = 2, actual = distinctLd.value, message = msg)
            assertEquals(expected = 2, actual = lastValue, message = msg)
            assertEquals(expected = 2, actual = observedCounter, message = msg)
        }

        ld.postValue(4)
        "postValue set, we should got changes".let { msg ->
            assertEquals(expected = 4, actual = ld.value, message = msg)
            assertEquals(expected = 4, actual = distinctLd.value, message = msg)
            assertEquals(expected = 4, actual = lastValue, message = msg)
            assertEquals(expected = 3, actual = observedCounter, message = msg)
        }

        distinctLd.removeObserver(observer)
        ld.value = 5
        "observer was removed - we should not got any changes".let { msg ->
            assertEquals(expected = 5, actual = ld.value, message = msg)
            assertEquals(expected = 5, actual = distinctLd.value, message = msg)
            assertEquals(expected = 4, actual = lastValue, message = msg)
            assertEquals(expected = 3, actual = observedCounter, message = msg)
        }
    }
}
