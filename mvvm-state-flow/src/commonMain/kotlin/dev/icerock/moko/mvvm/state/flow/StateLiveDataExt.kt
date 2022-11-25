/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.state.flow

import dev.icerock.moko.mvvm.state.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map

fun <T, E> Flow<ResourceState<T, E>>.data(): Flow<T?> = map { it.dataValue() }

fun <T, E> StateFlow<ResourceState<T, E>>.dataValue(): T? = value.dataValue()

suspend fun <T, E> Flow<ResourceState<T, E>>.dataValue(): T? =
    lastOrNull()?.dataValue()

fun <T, E> Flow<ResourceState<T, E>>.error(): Flow<E?> = map { it.errorValue() }

fun <T, E> StateFlow<ResourceState<T, E>>.errorValue(): E? = value.errorValue()

suspend fun <T, E> Flow<ResourceState<T, E>>.errorValue(): E? =
    lastOrNull()?.errorValue()

inline fun <T, E, reified ST : ResourceState<T, E>, FL : Flow<ST>> List<FL>.error(): Flow<E?> =
    combine(this) { list ->
        @Suppress("UNCHECKED_CAST")
        val errorItem = list.firstOrNull {
            (it is ResourceState.Failed<*, *>)
        } as? ResourceState.Failed<T, E>
        errorItem?.error
    }
