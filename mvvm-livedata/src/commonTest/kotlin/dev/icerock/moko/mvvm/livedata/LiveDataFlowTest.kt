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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.math.exp
import kotlin.test.Test
import kotlin.test.assertEquals

class LiveDataFlowTest {
    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @Test
    fun `flow to live data test`() {
        val scope = CoroutineScope(Dispatchers.Unconfined)

        val source = MutableStateFlow(0)
        val destination = source.asLiveData(scope)

        assertEquals(expected = source.value, actual = destination.value)

        source.value = 1
        assertEquals(expected = source.value, actual = destination.value)

        scope.cancel()

        source.value = 2
        assertEquals(expected = 1, actual = destination.value)
    }

    @Test
    fun `live data to flow test`() {
        val scope = CoroutineScope(Dispatchers.Unconfined)

        val source = MutableLiveData(0)
        val destination = source.asFlow()

        val items = mutableListOf<Pair<Int, Int>>()
        destination
            .onEach { value ->
                items.add(source.value to value)
            }
            .launchIn(scope)

        source.value = 1
        scope.cancel()
        source.value = 2

        val expectedItems = listOf(
            0 to 0,
            1 to 1
        )

        assertEquals(expected = expectedItems.size, actual = items.size)
        expectedItems.forEach { (sval, dval) ->
            assertEquals(expected = sval, actual = dval)
        }
    }
}
