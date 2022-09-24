/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.test.flow

import dev.icerock.moko.mvvm.flow.dataTransform
import dev.icerock.moko.mvvm.state.ResourceState
import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
open class FlowTest {

    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.Unconfined)

    @Test
    open fun dataTransformTest() = runTest {
        val flow: MutableStateFlow<ResourceState<Int, Throwable>> =
            MutableStateFlow(ResourceState.Success(10))
        val flowBool: MutableStateFlow<Boolean> = MutableStateFlow(false)

        var dataTransformCounter = 0
        var mergeWithCounter = 0

        val mapFlow: StateFlow<ResourceState<Long, Throwable>> = flow.dataTransform {
            dataTransformCounter++
            combine(this, flowBool) { a1, a2 ->
                mergeWithCounter++
                if (a2) a1.toLong() else -a1.toLong()
            }
        }.stateIn(coroutineScope, SharingStarted.Eagerly, ResourceState.Loading())

        assertEquals(actual = dataTransformCounter, expected = 1)
        assertEquals(actual = mergeWithCounter, expected = 1)
        assertEquals(expected = -10, actual = mapFlow.value.dataValue())

        flowBool.value = true

        assertEquals(actual = dataTransformCounter, expected = 1)
        assertEquals(actual = mergeWithCounter, expected = 2)
        assertEquals(expected = 10, actual = mapFlow.value.dataValue())

        flow.value = ResourceState.Success(11)

        assertEquals(actual = dataTransformCounter, expected = 2)
        assertEquals(actual = mergeWithCounter, expected = 3)
        assertEquals(expected = 11, actual = mapFlow.value.dataValue())

        flowBool.value = false

        assertEquals(actual = dataTransformCounter, expected = 2)
        assertEquals(
            actual = mergeWithCounter,
            expected = 4
        )
        assertEquals(expected = -11, actual = mapFlow.value.dataValue())
    }

    @Test
    open fun dataTransformMergeWithTest() = runTest {
        val coroutineScope = CoroutineScope(Dispatchers.Unconfined)

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
        assertEquals(actual = mergeWithIsAuthorizedCounter, expected = 1)
        assertTrue { result.value.isEmpty() }

        state.value = ResourceState.Loading()

        assertEquals(actual = dataTransformCounter, expected = 0)
        assertEquals(actual = mergeWithDataTransformCounter, expected = 0)
        assertEquals(actual = mergeWithIsAuthorizedCounter, expected = 2)
        assertTrue { result.value.isLoading() }

        state.value = ResourceState.Success(10)

        assertEquals(actual = dataTransformCounter, expected = 1)
        assertEquals(actual = mergeWithDataTransformCounter, expected = 1)
        assertEquals(actual = mergeWithIsAuthorizedCounter, expected = 3)
        assertTrue { result.value.isSuccess() }
        assertEquals(actual = result.value.dataValue(), expected = -10)

        isLoading.value = true

        assertEquals(actual = dataTransformCounter, expected = 1)
        assertEquals(actual = mergeWithDataTransformCounter, expected = 2)
        assertEquals(actual = mergeWithIsAuthorizedCounter, expected = 4)
        assertTrue { result.value.isSuccess() }
        assertEquals(actual = result.value.dataValue(), expected = 10)

        isLoading.value = false

        assertEquals(actual = dataTransformCounter, expected = 1)
        assertEquals(actual = mergeWithDataTransformCounter, expected = 3)
        assertEquals(actual = mergeWithIsAuthorizedCounter, expected = 5)
        assertTrue { result.value.isSuccess() }
        assertEquals(actual = result.value.dataValue(), expected = -10)
    }
}
