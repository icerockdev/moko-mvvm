/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.test.livedata

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.dataTransform
import dev.icerock.moko.mvvm.livedata.mediatorOf
import dev.icerock.moko.mvvm.state.ResourceState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

open class LiveDataTest {

    @Test
    open fun dataTransformTest() {
        val ld: MutableLiveData<ResourceState<Int, Throwable>> =
            MutableLiveData(ResourceState.Success(10))
        val ldBool: MutableLiveData<Boolean> = MutableLiveData(false)

        var dataTransformCounter = 0
        var mergeWithCounter = 0

        val mapLd: LiveData<ResourceState<Long, Throwable>> =
            ld.dataTransform {
                dataTransformCounter++
                mediatorOf(this, ldBool) { a1, a2 ->
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

        ld.value = ResourceState.Success(11)

        assertEquals(actual = dataTransformCounter, expected = 2)
        assertEquals(actual = mergeWithCounter, expected = 7)
        assertEquals(expected = 11, actual = mapLd.value.dataValue())

        ldBool.value = false

        assertEquals(actual = dataTransformCounter, expected = 2)
        assertEquals(
            actual = mergeWithCounter,
            expected = 9
        )
        assertEquals(expected = -11, actual = mapLd.value.dataValue())
    }

    @Test
    open fun dataTransformMergeWithTest() {
        val vmIsAuthorized = MutableLiveData(true)
        val state: MutableLiveData<ResourceState<Int, Throwable>> =
            MutableLiveData(ResourceState.Empty())
        val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

        var dataTransformCounter = 0
        var mergeWithDataTransformCounter = 0
        var mergeWithIsAuthorizedCounter = 0

        val dataTransform: LiveData<ResourceState<Long, Throwable>> =
            state.dataTransform {
                dataTransformCounter++
                mediatorOf(this, isLoading) { a1, a2 ->
                    mergeWithDataTransformCounter++
                    if (a2) a1.toLong() else -a1.toLong()
                }
            }

        val result: LiveData<ResourceState<Long, Throwable>> = mediatorOf(
            vmIsAuthorized,
            dataTransform
        ) { isAuthorized, dataState ->
            mergeWithIsAuthorizedCounter++
            if (isAuthorized) {
                dataState
            } else {
                ResourceState.Failed(Exception())
            }
        }

        assertEquals(actual = dataTransformCounter, expected = 0)
        assertEquals(actual = mergeWithDataTransformCounter, expected = 0)
        assertEquals(actual = mergeWithIsAuthorizedCounter, expected = 3)
        assertTrue { result.value.isEmpty() }

        state.value = ResourceState.Loading()

        assertEquals(actual = dataTransformCounter, expected = 0)
        assertEquals(actual = mergeWithDataTransformCounter, expected = 0)
        assertEquals(actual = mergeWithIsAuthorizedCounter, expected = 4)
        assertTrue { result.value.isLoading() }

        state.value = ResourceState.Success(10)

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
}
