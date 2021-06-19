/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import androidx.lifecycle.Observer
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.livedata.mergeWith
import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlin.test.Test
import kotlin.test.assertEquals

class AndroidLiveDataTest {

    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @Test
    fun livedata_valid() {
        val ld: MutableLiveData<Int> = MutableLiveData(initialValue = 0)

        var observedCounter = 0
        var lastValue: Int? = null
        val observer: Observer<Int> = Observer {
            observedCounter++
            lastValue = it
        }
        val androidLd = ld.ld()

        androidLd.observeForever(observer)

        assertEquals(expected = 1, actual = observedCounter)
        assertEquals(expected = 0, actual = lastValue)

        ld.value = 1

        assertEquals(expected = 2, actual = observedCounter)
        assertEquals(expected = 1, actual = lastValue)

        androidLd.removeObserver(observer)
        ld.value = 2

        assertEquals(expected = 2, actual = observedCounter)
        assertEquals(expected = 1, actual = lastValue)
    }

    @Test
    fun livedata_chain_valid() {
        val source1: MutableLiveData<Int> = MutableLiveData(initialValue = 0)
        val source2: MutableLiveData<Int> = MutableLiveData(initialValue = 0)

        val output: LiveData<Int> = source1.map { it * 2 }.mergeWith(source2) { a1, a2 ->
            a1 * a2
        }
        val androidLd = output.ld()

        var observedCounter = 0
        var lastValue: Int? = null
        val observer: Observer<Int> = Observer {
            observedCounter++
            lastValue = it
        }

        androidLd.observeForever(observer)

        assertEquals(expected = 1, actual = observedCounter)
        assertEquals(expected = 0, actual = lastValue)

        source1.value = 1
        source2.value = 2

        assertEquals(expected = 3, actual = observedCounter)
        assertEquals(expected = 4, actual = lastValue)

        androidLd.removeObserver(observer)
        source1.value = 2

        assertEquals(expected = 3, actual = observedCounter)
        assertEquals(expected = 4, actual = lastValue)
    }

    @Test
    fun mutable_livedata_valid() {
        val commonMutable: MutableLiveData<Int> = MutableLiveData(initialValue = 0)
        val androidLd = commonMutable.ld()

        assertEquals(expected = commonMutable.value, actual = androidLd.value)

        androidLd.value = 2

        assertEquals(expected = androidLd.value, actual = commonMutable.value)
    }
}
