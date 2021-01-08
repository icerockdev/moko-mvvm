/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.State
import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LiveDataTest {

    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @Test
    fun `live data observer testing`() {
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
    fun `mergeWith test`() {
        val ld: MutableLiveData<Int> = MutableLiveData(10)
        val ldBool: MutableLiveData<Boolean> = MutableLiveData(false)

        var mergeWithCounter = 0

        val mapLd: LiveData<Long> = ld.mergeWith(ldBool) { a1, a2 ->
            mergeWithCounter++
            if (a2) a1.toLong() else -a1.toLong()
        }

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
    fun `dataTransform test`() {
        val ld: MutableLiveData<State<Int, Throwable>> = MutableLiveData(State.Data(10))
        val ldBool: MutableLiveData<Boolean> = MutableLiveData(false)

        var dataTransformCounter = 0
        var mergeWithCounter = 0

        val mapLd: LiveData<State<Long, Throwable>> = ld.dataTransform {
            dataTransformCounter++
            mergeWith(ldBool) { a1, a2 ->
                mergeWithCounter++
                if (a2) a1.toLong() else -a1.toLong()
            }
        }

        assertEquals(actual = dataTransformCounter, expected = 1)
        assertEquals(actual = mergeWithCounter, expected = 3)
        assertEquals(expected = -10, actual = mapLd.value.dataValue())

        ldBool.value = true

        assertEquals(actual = dataTransformCounter, expected = 1)
        assertEquals(actual = mergeWithCounter, expected = 4)
        assertEquals(expected = 10, actual = mapLd.value.dataValue())

        ld.value = State.Data(11)

        assertEquals(actual = dataTransformCounter, expected = 2)
        assertEquals(actual = mergeWithCounter, expected = 7)
        assertEquals(expected = 11, actual = mapLd.value.dataValue())

        ldBool.value = false

        assertEquals(actual = dataTransformCounter, expected = 2)
        assertEquals(
            actual = mergeWithCounter,
            expected = 9
        ) // FIXME: there's an extra mergeWith lambda call
        assertEquals(expected = -11, actual = mapLd.value.dataValue())
    }

    @Test
    fun `dataTransform + mergeWith test`() {
        val vmIsAuthorized = MutableLiveData(true)
        val state: MutableLiveData<State<Int, Throwable>> = MutableLiveData(State.Empty())
        val isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

        var dataTransformCounter = 0
        var mergeWithDataTransformCounter = 0
        var mergeWithIsAuthorizedCounter = 0

        val dataTransform: LiveData<State<Long, Throwable>> = state.dataTransform {
            dataTransformCounter++
            mergeWith(isLoading) { a1, a2 ->
                mergeWithDataTransformCounter++
                if (a2) a1.toLong() else -a1.toLong()
            }
        }

        val result: LiveData<State<Long, Throwable>> = vmIsAuthorized
            .mergeWith(dataTransform) { isAuthorized, dataState ->
                mergeWithIsAuthorizedCounter++
                if (isAuthorized) {
                    dataState
                } else {
                    State.Error(Exception())
                }
            }

        assertEquals(actual = dataTransformCounter, expected = 0)
        assertEquals(actual = mergeWithDataTransformCounter, expected = 0)
        assertEquals(actual = mergeWithIsAuthorizedCounter, expected = 3)
        assertTrue { result.value.isEmpty() }

        state.value = State.Loading()

        assertEquals(actual = dataTransformCounter, expected = 0)
        assertEquals(actual = mergeWithDataTransformCounter, expected = 0)
        assertEquals(actual = mergeWithIsAuthorizedCounter, expected = 4)
        assertTrue { result.value.isLoading() }

        state.value = State.Data(10)

        assertEquals(actual = dataTransformCounter, expected = 1)
        assertEquals(actual = mergeWithDataTransformCounter, expected = 3)
        assertEquals(actual = mergeWithIsAuthorizedCounter, expected = 5)
        assertTrue { result.value.isSuccess() }
        assertEquals(actual = result.value.dataValue(), expected = -10)

        isLoading.value = true

        assertEquals(actual = dataTransformCounter, expected = 1)
        assertEquals(actual = mergeWithDataTransformCounter, expected = 4)
        assertEquals(actual = mergeWithIsAuthorizedCounter, expected = 6)
        assertTrue { result.value.isSuccess() }
        assertEquals(actual = result.value.dataValue(), expected = 10)

        isLoading.value = false

        assertEquals(actual = dataTransformCounter, expected = 1)
        assertEquals(actual = mergeWithDataTransformCounter, expected = 5)
        assertEquals(actual = mergeWithIsAuthorizedCounter, expected = 7)
        assertTrue { result.value.isSuccess() }
        assertEquals(actual = result.value.dataValue(), expected = -10)
    }

    @Test
    fun `live data map testing`() {
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
            assertEquals(expected = 8, actual = mergedLd.value, message = msg)
            assertEquals(expected = 2, actual = lastValue, message = msg)
            assertEquals(expected = 3, actual = observedCounter, message = msg)
        }
    }

    @Test
    fun `live data distinct testing`() {
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
