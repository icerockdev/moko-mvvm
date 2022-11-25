/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.state.flow

import dev.icerock.moko.mvvm.state.ResourceState
import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FlowDataTest {

    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.Unconfined)

    @Test
    fun dataTransformTest() {
        val flow: MutableStateFlow<ResourceState<Int, Throwable>> =
            MutableStateFlow(ResourceState.Success(10))

        val flowBool: MutableStateFlow<Boolean> = MutableStateFlow(false)

        var flowDataTransformCounter = 0
        var flowMergeWithCounter = 0

        val mapLd: StateFlow<ResourceState<Long, Throwable>> = flow.dataTransform {
            flowDataTransformCounter++
            combine(this, flowBool) { a1, a2 ->
                flowMergeWithCounter++
                if (a2) a1.toLong() else -a1.toLong()
            }
        }.stateIn(coroutineScope, SharingStarted.Eagerly, ResourceState.Loading())

        assertEquals(actual = flowDataTransformCounter, expected = 1)
        assertEquals(actual = flowMergeWithCounter, expected = 3)
        assertEquals(expected = -10, actual = mapLd.value.dataValue())

        flowBool.value = true

        assertEquals(actual = flowDataTransformCounter, expected = 1)
        assertEquals(actual = flowMergeWithCounter, expected = 4)
        assertEquals(expected = 10, actual = mapLd.value.dataValue())

        flow.value = ResourceState.Success(11)

        assertEquals(actual = flowDataTransformCounter, expected = 2)
        assertEquals(actual = flowMergeWithCounter, expected = 7)
        assertEquals(expected = 11, actual = mapLd.value.dataValue())

        flowBool.value = false

        assertEquals(actual = flowDataTransformCounter, expected = 2)
        assertEquals(
            actual = flowMergeWithCounter,
            expected = 9
        ) // FIXME: there's an extra mergeWith lambda call
        assertEquals(expected = -11, actual = mapLd.value.dataValue())
    }

    @Test
    fun dataTransformMergeWithTest() {
        val vmIsAuthorized = MutableStateFlow(true)
        val state: MutableStateFlow<ResourceState<Int, Throwable>> =
            MutableStateFlow(ResourceState.Empty())
        val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

        var dataTransformCounter = 0
        var mergeWithDataTransformCounter = 0
        var mergeWithIsAuthorizedCounter = 0

        val dataTransform: StateFlow<ResourceState<Long, Throwable>> = state.dataTransform {
            dataTransformCounter++
            combine(this, isLoading) { a1, a2 ->
                mergeWithDataTransformCounter++
                if (a2) a1.toLong() else -a1.toLong()
            }
        }.stateIn(coroutineScope, SharingStarted.Eagerly, ResourceState.Loading())

        val result: StateFlow<ResourceState<Long, Throwable>> = combine(
            vmIsAuthorized,
            dataTransform
        ) { isAuthorized, dataState ->
            mergeWithIsAuthorizedCounter++
            if (isAuthorized) {
                dataState
            } else {
                ResourceState.Failed(Exception())
            }
        }.stateIn(coroutineScope, SharingStarted.Eagerly, ResourceState.Loading())

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
